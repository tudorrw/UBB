import { createClient } from "@deepgram/sdk";
import fs from 'fs';

const deepgram = createClient(process.env.DEEPGRAM_API_KEY);

export async function POST(request: Request) {
    try {
        // Parse the id from the request body
        const { id } = await request.json();

        if (!id) {
            return new Response("ID is required", { status: 400 });
        }

        // Construct the path to the audio file using the id
        const filePath = `assets/audio/${id}.mp3`;

        // Check if the file exists
        if (!fs.existsSync(filePath)) {
            return new Response("Audio file not found", { status: 404 });
        }

        // Read the audio file into a buffer
        const audioBuffer = fs.readFileSync(filePath);

        // Send the audio buffer to Deepgram for transcription
        const response = await deepgram.listen.prerecorded.transcribeFile(
            audioBuffer ,  // Specify the buffer and MIME type
            {
                model: "nova",       // Choose the model for transcription
                smart_format: true,  // Enable smart formatting
                custom_topic: "Tell me the story of Sighisoara City"
            }
        );

        // Extract and return the transcription text
        const transcription = response.result?.results;
        return new Response(JSON.stringify({ transcription }), {
            headers: { "Content-Type": "application/json" },
        });
    } catch (error) {
        console.error("Transcription error:", error);
        return new Response("Error transcribing audio", { status: 500 });
    }
}
