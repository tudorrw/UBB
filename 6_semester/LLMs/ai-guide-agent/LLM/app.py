from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from rag import run_with_wikipedia_fallback, rag_chat
from langchain_openai import ChatOpenAI
import uvicorn

app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # For dev only!
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# instantiate a local‐LM LLM to generate RLHF instructions
instruction_llm = ChatOpenAI(
    base_url="http://localhost:1234/v1",
    api_key="lm-studio",
    model="gemma-3-4b-it-qat",
    temperature=0.7,
)

def generate_rlhf_instructions(chat_history: list[dict]) -> str:
    """
    Given a list of {"role":..., "content":...}, ask the local LLM to produce
    a short system‐style instruction to prepend to the next user prompt.
    """
    # flatten history into a string
    history_text = "\n".join(f"{m['role']}: {m['content']}" for m in chat_history)
    system_prompt = "You are an expert prompt engineer. From the conversation below, generate a concise list of 2–3 system-style instructions related to only how the answer is given for example if the answer should be longer or shorter, not to what information it contains, to guide the assistant's next response."
    user_prompt = f"""Conversation:
{history_text}

Instructions:"""

    resp = instruction_llm.invoke([
        {"role": "system", "content": system_prompt},
        {"role": "user",   "content": user_prompt}
    ])
    return resp.content.strip()

@app.post("/api/llm_summary")
async def llm_summary(request: Request):
    data = await request.json()
    place_name = data.get("place_name")
    place_vicinity = data.get("place_vicinity")
    language = data.get("language")
    summary = run_with_wikipedia_fallback(place_name, place_vicinity, language)
    print(f"LLM summary for {place_name}: {summary}")
    return {"summary": summary}

# General idea for the function:
@app.post("/api/llm_chat")
async def llm_rlhf(request: Request):
    payload = await request.json()
    chat_history      = payload.get("chat_history", [])
    message           = payload.get("message", "")
    language          = payload.get("language", "en")

    # 2) generate dynamic RLHF instructions
    print(f"Chat history: {chat_history}")
    # Combine chat history + current user message for RLHF
    full_conversation = [
        {"role": m["role"], "content": m["text"]} for m in chat_history
    ] + [{"role": "user", "content": message}]

    # Generate dynamic RLHF instructions
    instructions = generate_rlhf_instructions(full_conversation)

    answer = rag_chat(
        message=message,
        instructions=instructions,
        language=language,
        chat_history=chat_history,
    )

    print(f"Answer: {answer}")

    return {"answer": answer}

if __name__ == "__main__":
    uvicorn.run("app:app", host="0.0.0.0", port=8000, reload=True)