import { createClient } from "@deepgram/sdk";
import fs from 'fs';
import { pipeline } from 'stream/promises';
import { Readable } from 'stream';
import cuid from 'cuid';  // Make sure you have the 'cuid' library installed

const VOICE_ID = "9BWtsMINqrJLrRacOk9x";
const YOUR_XI_API_KEY = process.env.ELEVENLABS_API_KEY;

const chatStreamUrl = "http://localhost:8081/api/gpt";
const url = `https://api.elevenlabs.io/v1/text-to-speech/${VOICE_ID}/with-timestamps`;

const headers = {
    "Content-Type": "application/json",
    "xi-api-key": YOUR_XI_API_KEY
};

const generateAudio = async (data: Record<string, any>) => {
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error(`Error encountered, status: ${response.status}, content: ${errorText}`);
            return null;
        }

        const responseData = await response.json();

        if (!responseData.audio_base64) {
            console.error("No audio_base64 field in response");
            return null;
        }

        // Decode the base64 audio
        const audioBuffer = Buffer.from(responseData.audio_base64, "base64");

        // Generate unique ID and define file path
        const uniqueId = cuid();
        const filePath = `assets/audio/${uniqueId}.mp3`;

        // Save audio to file
        fs.writeFileSync(filePath, audioBuffer);
        console.log(`Audio saved successfully to ${filePath}`);

        // Return file path and alignment data
        return { filePath, alignment: responseData.alignment };
    } catch (error) {
        console.error("Error during request:", error);
        return null;
    }
};

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

    if (!chatResponse.ok || !chatResponse.body) {
        console.error("Failed to fetch from chatStreamUrl");
        return new Response("Server Error: Failed to fetch response", { status: 500 });
    }

    const gptText = await chatResponse.text();

    const data = {
        text: gptText,
        model_id: "eleven_multilingual_v2",
        voice_settings: {
            stability: 0.5,
            similarity_boost: 0.75
        }
    };

    const audioData = await generateAudio(data);

    if (!audioData) {
        return new Response("Server Error: Failed to generate audio", { status: 500 });
    }

    return new Response(JSON.stringify({
        message: "Audio generated and saved",
        gptText: gptText,
        audioPath: audioData.filePath,
        alignment: audioData.alignment
    }), {
        headers: { "Content-Type": "application/json" }
    });
}
