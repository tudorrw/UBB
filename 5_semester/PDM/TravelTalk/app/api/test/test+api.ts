import { createClient } from "@deepgram/sdk";
import fs from 'fs'
import { pipeline } from 'stream/promises'
import { Readable } from 'stream';

const chatStreamUrl = "http://localhost:8081/api/gpt_stream";

const VOICE_ID = "9BWtsMINqrJLrRacOk9x"; // Rachel
const YOUR_XI_API_KEY = process.env.ELEVENLABS_API_KEY;

export async function POST(request: Request) {
    
    const { prompt } = await request.json();
    if (!prompt) {
        return new Response("Prompt is required", { status: 400 });
    }
    
    // Fetch the text stream from ChatGPT endpoint
    const chatResponse = await fetch(chatStreamUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ prompt })
    });
    const chatStreamReader = chatResponse.body?.getReader();

    if (!chatStreamReader) {
        return new Response("Failed to read ChatGPT stream", { status: 500 });
    }

    const url = `https://api.elevenlabs.io/v1/text-to-speech/${VOICE_ID}/stream/with-timestamps`;
    const headers = {
        "Content-Type": "application/json",
        "xi-api-key": YOUR_XI_API_KEY
    };

    let isStreamClosed = false;
    const audioStream = new ReadableStream({
        async start(controller) {
            
            async function processTextToAudio(textChunk: string) {
                console.log(textChunk);

                
                
            }

            // Continuously read chunks from ChatGPT and process them
            while (true) {
                const { done, value } = await chatStreamReader.read();
                if (done) break;
                const textChunk = new TextDecoder().decode(value);
                await processTextToAudio(textChunk);
            }

            // Close the stream after processing all chunks
            if (!isStreamClosed) {
                isStreamClosed = true;
                controller.close();
            }
        },
        cancel() {
            isStreamClosed = true;
        }
    });

    return new Response(audioStream, {
        headers: { "Content-Type": "audio/mpeg", "Transfer-Encoding": "chunked" },
    });
}
