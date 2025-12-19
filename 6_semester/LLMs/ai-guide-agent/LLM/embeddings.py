import requests
from typing import List
from langchain.embeddings.base import Embeddings

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