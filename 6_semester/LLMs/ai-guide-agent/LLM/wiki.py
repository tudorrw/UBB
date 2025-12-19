import wikipedia
from ingest import ingest_document
from urllib.parse import urlparse, unquote

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