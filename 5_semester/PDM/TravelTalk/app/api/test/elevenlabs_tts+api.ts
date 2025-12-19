import fetch from "node-fetch";
import * as fs from "fs";
import * as base64 from "base64-js";

const VOICE_ID = "9BWtsMINqrJLrRacOk9x"; // Rachel
const YOUR_XI_API_KEY = process.env.ELEVENLABS_API_KEY;

export async function GET(request: Request) {
    if (!YOUR_XI_API_KEY) {
        console.error("API key is missing.");
        return new Response("Server Error: Missing API Key", { status: 500 });
    }

    const url = `https://api.elevenlabs.io/v1/text-to-speech/${VOICE_ID}/stream/with-timestamps`;
    const headers = {
        "Content-Type": "application/json",
        "xi-api-key": YOUR_XI_API_KEY
    };

    const data = {
        text: "Born and raised in the charming south, I can add a touch of sweet southern hospitality to your audiobooks and podcasts",
        model_id: "eleven_multilingual_v2",
        voice_settings: {
            stability: 0.5,
            similarity_boost: 0.75
        }
    };

    try {
        const response = await fetch(url, {
            method: "POST",
            headers: headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error(`Error encountered, status: ${response.status}, content: ${errorText}`);
            return new Response("Server Error: Failed to generate audio", { status: 500 });
        }

        const audioChunks: Uint8Array[] = [];
        const characters: string[] = [];
        const characterStartTimesSeconds: number[] = [];
        const characterEndTimesSeconds: number[] = [];

        let buffer = "";

        return new Promise<Response>((resolve, reject) => {
            response.body?.on("data", (chunk) => {
                buffer += chunk.toString("utf-8");

                // Split the buffer by newline to process complete JSON objects
                const lines = buffer.split("\n");

                // Keep the last incomplete line in the buffer
                buffer = lines.pop() || "";

                for (const line of lines) {
                    try {
                        const responseDict = JSON.parse(line);

                        // Decode audio and collect it
                        if (responseDict["audio_base64"]) {
                            const audioChunk = base64.toByteArray(responseDict["audio_base64"]);
                            audioChunks.push(audioChunk);
                        }

                        // Collect timestamps
                        if (responseDict["alignment"]) {
                            characters.push(...responseDict["alignment"]["characters"]);
                            characterStartTimesSeconds.push(...responseDict["alignment"]["character_start_times_seconds"]);
                            characterEndTimesSeconds.push(...responseDict["alignment"]["character_end_times_seconds"]);
                        }
                    } catch (error) {
                        console.error("Error parsing JSON:", error);
                    }
                }
            });

            response.body?.on("end", () => {
                // Combine all audio chunks into a single audio buffer
                const audioBuffer = Buffer.concat(audioChunks);
                fs.writeFileSync("output.mp3", audioBuffer);

                resolve(
                    new Response(
                        JSON.stringify({
                            characters,
                            character_start_times_seconds: characterStartTimesSeconds,
                            character_end_times_seconds: characterEndTimesSeconds
                        }),
                        { headers: { "Content-Type": "application/json" } }
                    )
                );
            });

            response.body?.on("error", (error) => {
                console.error("Error during audio generation:", error);
                reject(new Response("Server Error: Failed to generate audio", { status: 500 }));
            });
        });
    } catch (error) {
        console.error("Error during audio generation:", error);
        return new Response("Server Error: Failed to generate audio", { status: 500 });
    }
}
