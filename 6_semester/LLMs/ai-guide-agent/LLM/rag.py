from config import vectorstore, llm
from langchain.chains import ConversationalRetrievalChain
from langchain.memory import ConversationBufferMemory
from langchain.schema import AIMessage, HumanMessage, SystemMessage
from langchain.prompts import PromptTemplate
from langchain.chains import LLMChain

import wikipedia
from ingest import ingest_document

retriever = vectorstore.as_retriever()
memory = ConversationBufferMemory(memory_key="chat_history", return_messages=True)
qa_chain = ConversationalRetrievalChain.from_llm(
    llm=llm,
    retriever=retriever,
    memory=memory,
)

def run_with_wikipedia_fallback(place: str, place_vicinity: str, language: str) -> str:
    query = f"""Provide a brief summary (maximum 2-3 sentences) about {place} located in {place_vicinity}. 
    Describe if notable the history, general atmosphere, local culture, and any other features that make the place unique. 
    Keep the response concise but informative. Provide EXACTLY THE ANSWER 'Information about {place} located in {place_vicinity} not found.'
    ONLY IF ABSOLUTELY no information is not found AT ALL but if something at all is found then return the brief (maximum 2-3 sentences) summary 
    based on that. Provide the information in the language: {language}"""
    docs_with_scores = vectorstore.similarity_search_with_score(query, k=7)
    threshold = 0.65
    for doc, score in docs_with_scores:
        print(f"Score: {score:.3f} | Content Preview: {doc.page_content[:200]}")
    filtered_docs = [doc for doc, score in docs_with_scores if score <= threshold]
    print("Filtered docs:", filtered_docs)
    
    if len(filtered_docs) > 1:
        return qa_chain.run(query)
    try:
        
        print("Searching Wikipedia for:", place)
        search_results = wikipedia.search(place)
        if not search_results:
            return f"No relevant documents found in ChromaDB or Wikipedia for {place}."

        page = wikipedia.page(search_results[0], auto_suggest=False)
        print("Wikipedia page found:", page.title)
        ingest_document(page.title, page.content)
        print(f"(Wikipedia content ingested for: {page.title})")
        return qa_chain.run(query)
        
    except Exception as e:
        return f"Failed to retrieve or ingest from Wikipedia for {place}: {e}"

def convert_chat_to_memory(chat_history: list[dict]) -> list:
    """
    Convert a list of {'role': ..., 'text': ...} messages into LangChain memory messages.
    """
    role_to_message = {
        "user": HumanMessage,
        "system": SystemMessage,
        "assistant": AIMessage,
    }
    messages = []
    for m in chat_history:
        role = m["role"]
        content = m["text"]
        if role in role_to_message:
            messages.append(role_to_message[role](content=content))
    return messages

from langchain.prompts import PromptTemplate
from langchain.chains import LLMChain, StuffDocumentsChain, ConversationalRetrievalChain
from langchain.memory import ConversationBufferMemory

def rag_chat(message: str, instructions: str, language: str, chat_history: list[dict]) -> str:
    """
    Run Conversational RAG using ONLY local vectorstore.
    Uses past messages as memory.
    """

    # Limit chat history to last 4 messages
    last_messages = chat_history[-4:]

    # Prepare memory from last 4 messages
    memory = ConversationBufferMemory(memory_key="chat_history", return_messages=True)
    memory.chat_memory.messages = convert_chat_to_memory(last_messages)

    # Build combined prompt string with all your info
    chat_hist_str = "\n".join([f"{m['role']}: {m['text']}" for m in last_messages])

    # Similarity search with scores (adjust k and threshold as needed)
    docs_with_scores = vectorstore.similarity_search_with_score(chat_hist_str, k=3)
    threshold = 0.65

    # Filter documents by score (lower score = more similar)
    filtered_docs = [doc for doc, score in docs_with_scores if score <= threshold]

    # If no docs pass threshold, fallback to all retrieved docs
    if not filtered_docs:
        filtered_docs = [doc for doc, _ in docs_with_scores]

    # Combine filtered docs content as context
    context = "\n\n".join([doc.page_content for doc in filtered_docs])

    full_prompt = f"""
You are a helpful assistant answering questions based only on the local knowledge database.

Instructions:
{instructions.strip()}

Relevant documents:
{context}

Chat history:
{chat_hist_str}

This is your question, please respond to it: "{message.strip()}"

Please answer based on the previous conversation and the available information.
If you cannot find enough relevant information in the database, respond exactly with "I don't have information about this".

Respond in this language: {language.strip()}
"""
    print(full_prompt)

    # Create the QA chain with memory and retriever
    qa_chain = ConversationalRetrievalChain.from_llm(
        llm=llm,
        retriever=vectorstore.as_retriever(),
        memory=memory,
    )

    # Pass the whole combined prompt as "question"
    try:
        return qa_chain.run(full_prompt)
    except Exception as e:
        return f"Error retrieving answer from local data: {e}"
