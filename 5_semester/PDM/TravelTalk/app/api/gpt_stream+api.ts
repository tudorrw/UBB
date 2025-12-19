import OpenAI from 'openai';
import { OPENAI_API_KEY } from '../utils/audio/declarations';
import {GoogleGenerativeAI} from "@google/generative-ai";
import {VertexAI} from "@google-cloud/vertexai";

export async function getChatGPTStream(prompt: string): Promise<ReadableStream> {
    const apiKey = OPENAI_API_KEY;
    const openai = new OpenAI({ apiKey });

    return new ReadableStream({
        async start(controller) {
            try {
                const response = await openai.chat.completions.create({
                    model: "gpt-4o",
                    messages: [
                        { role: "system", content: "You are a travel assistant, helping users find information about locations." },
                        { role: "user", content: prompt },  // Use the prompt from the request body
                    ],
                    stream: true,
                });


                // Handle each chunk from the async iterator
                for await (const chunk of response) {
                    const textChunk = chunk.choices[0].delta?.content || '';
                    controller.enqueue(new TextEncoder().encode(textChunk));
                }

                // Close the stream once done
                controller.close();
            } catch (error) {
                console.error("Error initiating stream:", error);
                controller.error(error);
            }
        }
    });
}

// export async function POST(request: Request) {
//     const apiKey = OPENAI_API_KEY;
//     const openai = new OpenAI({ apiKey });
//
//     // Parse the prompt from the request body
//     const { prompt } = await request.json();
//
//     console.log(prompt);
//
//
//     const responseStream = new ReadableStream({
//         async start(controller) {
//             try {
//                 const response = await openai.chat.completions.create({
//                     model: "gpt-4",
//                     messages: [
//                         { role: "system", content: "You are a helpful assistant." },
//                         { role: "user", content: prompt },  // Use the prompt from the request body
//                     ],
//                     stream: true,
//                 });
//
//                 // Use for-await-of to handle each chunk from the async iterator
//                 for await (const chunk of response) {
//                     const textChunk = chunk.choices[0].delta?.content || '';
//                     controller.enqueue(new TextEncoder().encode(textChunk));
//                 }
//
//                 // Close the stream once done
//                 controller.close();
//             } catch (error) {
//                 console.error("Error initiating stream:", error);
//                 controller.error(error);
//             }
//         }
//     });
//
//     return new Response(responseStream, {
//         headers: { "Content-Type": "text/event-stream" },
//     });
// }
