import streamlit as st
import wikipedia, requests, pandas as pd
import signal
import subprocess
import os
from urllib.parse import urlparse, unquote

# 1) Register your LM-Studio model with DeepEval
subprocess.run([
    "deepeval", "set-local-model",
    "--model-name=gemma-3-4b-it-qat",
    "--base-url=http://localhost:1234/v1/",
    "--api-key=lm-studio",
], check=True)

# Disable any new signal handlers (no-ops instead of real handlers)
signal.signal = lambda *args, **kwargs: None
from deepeval import assert_test
from deepeval.metrics import GEval, AnswerRelevancyMetric
from deepeval.test_case import LLMTestCase, LLMTestCaseParams

from chromadb import PersistentClient
from langchain.schema import Document
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.embeddings.base import Embeddings
from langchain_community.vectorstores import Chroma
from langchain_openai import ChatOpenAI
from langchain.chains import ConversationalRetrievalChain
from langchain.memory import ConversationBufferMemory


# --- Your existing embedder & splitter ---
class LocalServerEmbeddings(Embeddings):
    def __init__(self, base_url: str):
        self.base_url = base_url
        self.model = "text-embedding-nomic-embed-text-v1.5"
    def embed_documents(self, texts):
        resp = requests.post(f"{self.base_url}/embeddings", json={"input": texts})
        resp.raise_for_status()
        return [item["embedding"] for item in resp.json()["data"]]
    def embed_query(self, text):
        resp = requests.post(f"{self.base_url}/embeddings", json={"input": [text]})
        resp.raise_for_status()
        return resp.json()["data"][0]["embedding"]

embedder = LocalServerEmbeddings("http://localhost:1234/v1")
text_splitter = RecursiveCharacterTextSplitter(chunk_size=3000, chunk_overlap=300)

# --- 1) Set up your evaluation-specific Chroma collection ---
client = PersistentClient(path="chroma_db")

evaluation_collection = client.get_or_create_collection("traveltalk_eval")
evaluation_vectorstore = Chroma(
    client=client,
    collection_name="traveltalk_eval",
    embedding_function=embedder,
    collection_metadata={"hnsw:space": "cosine"},
)

llm = ChatOpenAI(
    base_url="http://localhost:1234/v1",
    api_key="lm-studio",
    model="gemma-3-4b-it-qat",
    temperature=0.9,
)

retriever = evaluation_vectorstore.as_retriever()

memory = ConversationBufferMemory(memory_key="chat_history", return_messages=True)
qa_chain = ConversationalRetrievalChain.from_llm(
    llm=llm,
    retriever=retriever,
    memory=memory,
)


def ingest_document(doc_id: str, text: str):
    docs = [Document(page_content=text, metadata={"source": doc_id})]
    splits = text_splitter.split_documents(docs)
    chunks = [d.page_content for d in splits]
    embeddings = embedder.embed_documents(chunks)
    ids = [f"{doc_id}_{i}" for i in range(len(chunks))]
    metadatas = [{"source": doc_id, "chunk_index": i} for i in range(len(chunks))]
    evaluation_collection.upsert(
        ids=ids,
        embeddings=embeddings,
        metadatas=metadatas,
        documents=chunks
    )


def extract_wiki_title(url: str) -> str:
    path = urlparse(url).path
    slug = path.split("/")[-1]
    return unquote(slug.replace("_", " "))


def fetch_and_ingest_wikipedia(title: str):
    search_results = wikipedia.search(title)
    if not search_results:
        return None
    page = wikipedia.page(search_results[0], auto_suggest=False)
    ingest_document(page.title, page.content)
    return page.title


# Ingest initial data for Paris and London
gold_texts = {
    "Paris": "Paris is the capital city of France, celebrated for its historic landmarks like the Eiffel Tower and the Louvre Museum. It’s renowned for its vibrant art scene, world-class cuisine, and romantic atmosphere.",
    "London": "London is the capital city of the United Kingdom, known for its rich history and iconic landmarks such as the Tower of London and Buckingham Palace. It boasts a vibrant cultural scene and is a global financial hub.",
    "Atlantis": "Information about Atlantis not found."  # Expected fallback for mythical place
}

fetch_and_ingest_wikipedia("Paris")
fetch_and_ingest_wikipedia("London")
# Do not ingest anything for Atlantis to simulate no data


# --- 2) DeepEval metric & prompt variants ---
correctness = GEval(
    name="Correctness",
    criteria="Determine if the 'actual output' correctly captures the facts in the reference context.",
    evaluation_steps=[
        "Check if each fact in the actual output matches the reference text.",
        "Confirm no key detail is omitted.",
        "Assess overall factual completeness."
    ],
    evaluation_params=[
        LLMTestCaseParams.INPUT,
        LLMTestCaseParams.ACTUAL_OUTPUT,
        LLMTestCaseParams.EXPECTED_OUTPUT
    ],
    threshold=0.5,
)
correctness.evaluation_cost = 0.0

prompt_variants = [
    "Provide exactly two concise and factual sentences describing key aspects of {place}.",
    "In two clear sentences, give a brief yet informative overview of {place}, highlighting its most notable features.",
    "List the two most significant and interesting facts about {place}, phrased as concise sentences.",
    "Provide a brief summary (maximum 2-3 sentences) about {place}. Describe if notable the history, general atmosphere, local culture, and any other features that make the place unique. Keep the response concise but informative. Provide EXACTLY THE ANSWER 'Information about {place} not found.' ONLY IF ABSOLUTELY no information is not found AT ALL, but if something at all is found then return the brief (maximum 2-3 sentences) summary based on that."
]

answer_relevancy = AnswerRelevancyMetric(
    threshold=0.5,
    model="gemma-3-4b-it-qat",
    include_reason=True
)
answer_relevancy.evaluation_cost = 0.0

def evaluate_prompts_on_place(place: str):
    results = []
    gold_text = gold_texts.get(place, f"Information about {place} not found in the database.")
    for template in prompt_variants:
        prompt = template.format(place=place)

        # A) retrieve your evaluation context
        docs = evaluation_vectorstore.similarity_search(prompt, k=3)
        retrieval_context = [d.page_content for d in docs]

        # B) get the chain’s answer
        actual = qa_chain.run(prompt)

        # C) build a test case
        tc = LLMTestCase(
            input=prompt,
            actual_output=actual,
            expected_output=gold_text,
            retrieval_context=retrieval_context,
        )

        # D) evaluate via GEval.evaluate()
        correctness_score, correctness_reasoning = correctness.evaluate(tc)
        correctness_passed = correctness_score >= 5  # example passing threshold

        # E) evaluate via answer relevancy
        relevancy_score = answer_relevancy.measure(tc)
        relevancy_passed = relevancy_score >= 0.5

        results.append({
            "prompt": prompt,
            "actual_output": actual,
            "score": correctness_score,
            "passed": correctness_passed,
            "reasoning": correctness_reasoning,
            "relevancy_score": relevancy_score,
            "relevancy_passed": relevancy_passed,
        })

    return pd.DataFrame(results)


def run_multiple_tests():
    places = ["Paris", "London", "Atlantis"]  # Atlantis expected to have no data
    all_results = []

    for place in places:
        st.write(f"### Evaluation results for: {place}")
        df = evaluate_prompts_on_place(place)

        st.dataframe(df[[
            "prompt",
            "score",
            "passed",
            "reasoning",
            "actual_output",
            "relevancy_score",
            "relevancy_passed"
        ]])



        all_results.append(df.assign(place=place))



    combined_df = pd.concat(all_results, ignore_index=True)
    return combined_df

# --- 3) Streamlit UI to trigger evaluation ---
st.title("🔍 Evaluate Prompt Variants with LLM Judge")

if st.button("Run Evaluation"):
    combined_df = run_multiple_tests()
