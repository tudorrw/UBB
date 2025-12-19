from langchain.schema import Document
from config import text_splitter, embedder, collection

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