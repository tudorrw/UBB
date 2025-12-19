from langchain_openai import ChatOpenAI
from langchain_chroma import Chroma
from langchain.text_splitter import RecursiveCharacterTextSplitter
from chromadb import PersistentClient
from embeddings import LocalServerEmbeddings

embedder = LocalServerEmbeddings(base_url="http://localhost:1234/v1")
text_splitter = RecursiveCharacterTextSplitter(chunk_size=500, chunk_overlap=50)
client = PersistentClient(path="chroma_db")
collection = client.get_or_create_collection("traveltalk")
vectorstore = Chroma(client=client, collection_name="traveltalk", embedding_function=embedder, collection_metadata={"hnsw:space": "cosine"}) 
llm = ChatOpenAI(
    base_url="http://localhost:1234/v1",
    api_key="lm-studio",
    model="gemma-3-4b-it-qat",
    # model="deepseek-r1-distill-qwen-7b",
    temperature=0.9,
)
evaluation_collection = client.get_or_create_collection("traveltalk_eval")
evaluation_vectorstore = Chroma(
    client=client,
    collection_name="traveltalk_eval",
    embedding_function=embedder,
    collection_metadata={"hnsw:space": "cosine"}
)