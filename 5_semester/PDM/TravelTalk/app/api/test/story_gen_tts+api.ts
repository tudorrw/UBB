import fetch from "node-fetch";
import fs from "fs";
import cuid from "cuid";

const chatStreamUrl = "http://localhost:8081/api/gpt_stream";
const VOICE_ID = "9BWtsMINqrJLrRacOk9x";
const RATE_LIMIT_DELAY = 1000;

export async function POST(request: Request) {
    const { prompt } = await request.json();
    if (!prompt) {
        return new Response("Prompt is required", { status: 400 });
    }

    const YOUR_XI_API_KEY = "sk_4e36a085cf2ba51f76ac01bb8f39be7e14eb9d0755835415";

    const chatResponse = await fetch(chatStreamUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ prompt })
    });

    if (!chatResponse.body) {
        return new Response("Failed to read ChatGPT stream", { status: 500 });
    }

    const uniqueId = cuid();
    const filePath = `assets/audio/${uniqueId}.mp3`;
    const fileStream = fs.createWriteStream(filePath);
    let isStreamClosed = false;
    let processingAudio = false;
    let pendingAudioChunks = [];

    const audioStream = new ReadableStream({
        async start(controller) {
            let textBuffer = "";
            const chunkBuffer = [];
            const batchSize = 3;

            async function processTextToAudio(textChunk) {
                if (processingAudio) {
                    pendingAudioChunks.push(textChunk);
                    return;
                }

                processingAudio = true;
                const url = `https://api.elevenlabs.io/v1/text-to-speech/${VOICE_ID}/stream/with-timestamps`;
                const headers = {
                    "Content-Type": "application/json",
                    "xi-api-key": YOUR_XI_API_KEY
                };

                const data = {
                    text: textChunk,
                    model_id: "eleven_multilingual_v2",
                    voice_settings: {
                        stability: 0.5,
                        similarity_boost: 0.75
                    }
                };

                const response = await fetch(url, {
                    method: "POST",
                    headers: headers,
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    console.error(`Error in ElevenLabs request: ${response.statusText}`);
                    processingAudio = false;
                    return;
                }

                let buffer = "";
                response.body?.on("data", (chunk) => {
                    buffer += chunk.toString("utf-8");

                    let boundary = buffer.lastIndexOf("\n");
                    if (boundary !== -1) {
                        const completeLines = buffer.slice(0, boundary).split("\n");
                        buffer = buffer.slice(boundary + 1);

                        for (const line of completeLines) {
                            try {
                                const responseDict = JSON.parse(line);
                                if (responseDict["audio_base64"] && responseDict["alignment"]) {
                                    const audioChunk = Buffer.from(responseDict["audio_base64"], "base64");

                                    if (!isStreamClosed) {
                                        controller.enqueue(new TextEncoder().encode(JSON.stringify({
                                            audio: audioChunk.toString("base64"),
                                            timestamps: responseDict["alignment"]
                                        }) + "\n"));
                                    }
                                    fileStream.write(audioChunk);
                                }
                            } catch (error) {
                                console.error("Error parsing JSON:", error);
                            }
                        }
                    }
                });

                return new Promise((resolve, reject) => {
                    response.body?.on("end", async () => {
                        processingAudio = false;
                        if (pendingAudioChunks.length > 0) {
                            const nextChunk = pendingAudioChunks.shift();
                            await processTextToAudio(nextChunk || "");
                        }
                        resolve();
                    });
                    response.body?.on("error", (error) => {
                        processingAudio = false;
                        console.error("Error reading ElevenLabs stream:", error);
                        reject(error);
                    });
                });
            }

            async function sendChunkBuffer() {
                if (chunkBuffer.length > 0) {
                    const batchedText = chunkBuffer.join(" ");
                    chunkBuffer.length = 0;

                    await new Promise(resolve => setTimeout(resolve, RATE_LIMIT_DELAY));
                    await processTextToAudio(batchedText);
                }
            }

            chatResponse.body.on("data", (chunk) => {
                textBuffer += chunk.toString("utf-8");

                const sentences = textBuffer.split(/([.?!])\s+/);
                textBuffer = sentences.pop() || "";

                for (const sentence of sentences) {
                    if (sentence.trim()) {
                        chunkBuffer.push(sentence.trim());
                    }
                }

                if (chunkBuffer.length >= batchSize) {
                    sendChunkBuffer();
                }
            });

            chatResponse.body.on("end", async () => {
                if (textBuffer) {
                    chunkBuffer.push(textBuffer.trim());
                }
                await sendChunkBuffer();
                if (!isStreamClosed) {
                    isStreamClosed = true;
                    controller.close();
                    fileStream.end();
                }
            });

            chatResponse.body.on("error", (error) => {
                console.error("Error reading ChatGPT stream:", error);
                controller.error(error);
                fileStream.end();
            });
        },
        cancel() {
            isStreamClosed = true;
            fileStream.end();
        }
    });

    return new Response(audioStream, {
        headers: { "Content-Type": "application/json", "Transfer-Encoding": "chunked" }
    });
}
