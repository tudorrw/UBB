import streamlit as st
from rag import run_with_wikipedia_fallback
from ingest import ingest_document
from wiki import extract_wiki_title
import wikipedia
import pandas as pd
import PyPDF2
from config import collection

st.title("TravelTalk Document Ingestion System")

# Upload or ingest documents
uploaded = st.file_uploader("Upload text or PDF files", type=["txt", "md", "pdf"], accept_multiple_files=True)
wiki_url = st.text_input("Or enter a Wikipedia page URL")

if st.button("Run Ingestion"):
    for f in uploaded:
        if f.type == "application/pdf":
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
        answer = run_with_wikipedia_fallback(user_query)
        st.markdown(f"**Answer:** {answer}")

# View ChromaDB contents
if st.button("Show ChromaDB Contents"):
    total = collection.count()
    st.write(f"Total chunks in collection: **{total}**")
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