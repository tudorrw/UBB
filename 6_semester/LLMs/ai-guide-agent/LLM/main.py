import streamlit as st
import wikipedia
import requests
from typing import List
import pandas as pd

import chromadb
from chromadb import PersistentClient
from urllib.parse import urlparse, unquote

from langchain.schema import Document
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.embeddings.base import Embeddings

from langchain_openai import ChatOpenAI
from langchain_community.vectorstores import Chroma
from langchain.chains import ConversationalRetrievalChain
from langchain.memory import ConversationBufferMemory

# --- 1) Your Embedding & Chroma Setup ---

class LocalServerEmbeddings(Embeddings):
    def __init__(self, base_url: str):
        self.base_url = base_url
        self.model = "text-embedding-nomic-embed-text-v1.5"

    def embed_documents(self, texts: List[str]) -> List[List[float]]:
        resp = requests.post(f"{self.base_url}/embeddings", json={"input": texts})
        resp.raise_for_status()
        return [item["embedding"] for item in resp.json()["data"]]

    def embed_query(self, text: str) -> List[float]:
        resp = requests.post(f"{self.base_url}/embeddings", json={"input": [text]})
        resp.raise_for_status()
        return resp.json()["data"][0]["embedding"]

embedder = LocalServerEmbeddings(base_url="http://localhost:1234/v1")
text_splitter = RecursiveCharacterTextSplitter(chunk_size=3000, chunk_overlap=300)

client     = PersistentClient(path="chroma_db")
collection = client.get_or_create_collection("traveltalk")

# --- 2) LangChain Setup for RAG ---

llm = ChatOpenAI(
    base_url="http://localhost:1234/v1",
    api_key="lm-studio",
    # model="deepseek-r1-distill-qwen-7b",
    model="llama-3.2-3b-instruct",

    temperature=0.9,
)

vectorstore = Chroma(client=client, collection_name="traveltalk", embedding_function=embedder, collection_metadata={"hnsw:space": "cosine"})
retriever = vectorstore.as_retriever()

memory = ConversationBufferMemory(memory_key="chat_history", return_messages=True)
qa_chain = ConversationalRetrievalChain.from_llm(
    llm=llm,
    retriever=retriever,
    memory=memory,
)

def run_with_wikipedia_fallback(place: str, retriever, chain, collection) -> str:
    query = f"Tell me 2 sentences about {place}"

    # Step 1: Check if the retriever finds relevant chunks
    docs_with_scores = vectorstore.similarity_search_with_score(query, k=7)
    threshold = 1.2
    for doc, score in docs_with_scores:
        print(f"Score: {score:.3f} | Content Preview: {doc.page_content[:200]}")
    filtered_docs = [doc for doc, score in docs_with_scores if score <= threshold]
    print("Filtered docs:", filtered_docs)

    if len(filtered_docs) > 10:
        return chain.run(query)

    # Step 2: Fallback to Wikipedia
    try:
        print("Searching Wikipedia for:", place)
        search_results = wikipedia.search(place)
        if not search_results:
            return f"No relevant documents found in ChromaDB or Wikipedia for {place}."

        page = wikipedia.page(search_results[0], auto_suggest=False)
        print("Wikipedia page found:", page.title)
        ingest_document(page.title, page.content)
        print("Wikipedia content ingested for: {page.title})
        return chain.run(query)
    except Exception as e:
        return f"Failed to retrieve or ingest from Wikipedia for {place}: {e}"


# --- 3) Ingestion Functions ---

def ingest_document(doc_id: str, text: str):
    docs = [Document(page_content=text, metadata={"source": doc_id})]
    splits = text_splitter.split_documents(docs)
    chunks = [d.page_content for d in splits]
    embeddings = embedder.embed_documents(chunks)
    ids = [f"{doc_id}_{i}" for i in range(len(chunks))]
    metadatas = [{"source": doc_id, "chunk_index": i} for i in range(len(chunks))]
    collection.upsert(
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


# --- 4) Streamlit UI ---

st.title("📚 RAG-Powered ChromaDB System")

# Upload or ingest documents
uploaded = st.file_uploader("Upload text or PDF files", type=["txt", "md", "pdf"], accept_multiple_files=True)
wiki_url = st.text_input("Or enter a Wikipedia page URL")

if st.button("Run Ingestion"):
    for f in uploaded:
        if f.type == "application/pdf":
            import PyPDF2
            reader = PyPDF2.PdfReader(f)
            raw = "\n\n".join(p.extract_text() or "" for p in reader.pages)
        else:
            raw = f.read().decode("utf-8")
        ingest_document(f.name, raw)
        st.success(f"Ingested file: {f.name}")

    if wiki_url:
        try:
            title = extract_wiki_title(wiki_url)
            page = wikipedia.page(title, auto_suggest=False)
            ingest_document(page.title, page.content)
            st.success(f"Ingested Wikipedia page: {page.title}")
        except Exception as e:
            st.error(f"Error fetching Wikipedia: {e}")

# Query with hybrid RAG (with memory + source fallback)
user_query = st.text_input("Ask something about a place...")
if st.button("Ask"):
    if user_query:
        answer = run_with_wikipedia_fallback(user_query, retriever, qa_chain, collection)
        st.markdown(f"**Answer:** {answer}")

# View ChromaDB contents
if st.button("Show ChromaDB Contents"):
    total = collection.count()
    st.write(f"🔍 Total chunks in collection: **{total}**")
    sample = collection.get(offset=0, limit=5)
    rows = []
    for doc, meta in zip(sample["documents"], sample["metadatas"]):
        rows.append({
            "source": meta["source"],
            "chunk_index": meta["chunk_index"],
            "preview": doc[:100].replace("\n", " ")
        })
    df = pd.DataFrame(rows)
    st.table(df)