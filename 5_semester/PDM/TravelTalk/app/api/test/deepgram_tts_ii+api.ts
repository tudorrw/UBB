import { createClient, LiveTTSEvents } from '@deepgram/sdk';
import fs from 'fs';

const text = "Hello, this is a test."; // Text for synthesis
const deepgram = createClient("72d39af1331f5e224b26f6dab2acbf78e69a3046");

export async function GET(request: Request) {
    let audioBuffer = Buffer.alloc(0); // Initialize an empty buffer

    const dgConnection = deepgram.speak.live({
        model: "aura-asteria-en"
    });

    dgConnection.on(LiveTTSEvents.Open, () => {
        console.log("Connection opened");

        // Send text data and flush to start TTS synthesis
        dgConnection.sendText(text);
        dgConnection.flush(); // Signal that all text has been sent

        // Set up event listeners within the Open event
        dgConnection.on(LiveTTSEvents.Audio, (data) => {
            console.log("Audio data received, chunk size:", data);
            if (data.length > 0) {
                // const buffer = Buffer.from(data);
                audioBuffer = Buffer.concat([audioBuffer, data]); // Accumulate audio chunks
                fs.writeFile("output.mp3", audioBuffer, (err) => {
                    if (err) {
                        console.error("Error writing audio file:", err);
                    } else {
                        console.log("Audio file saved as output.mp3");
                    }
                });
            } else {
                console.log("Received empty audio chunk.");
            }
        });

        dgConnection.on(LiveTTSEvents.Flushed, () => {
            console.log("Flushed event received. Waiting for connection to close...");
        });

        dgConnection.on(LiveTTSEvents.Close, () => {
            console.log("Connection closed, writing final audio to file...");
            fs.writeFile("output.mp3", audioBuffer, (err) => {
                if (err) {
                    console.error("Error writing audio file:", err);
                } else {
                    console.log("Audio file saved as output.mp3");
                }
            });
            // Resetting the buffer is not necessary here since the connection is complete
        });

        dgConnection.on(LiveTTSEvents.Error, (err) => {
            console.error("Error:", err);
        });
    });

    return new Response(JSON.stringify({ message: "Audio streaming started." }), {
        headers: { "Content-Type": "application/json", "Transfer-Encoding": "chunked" }
    });
}
