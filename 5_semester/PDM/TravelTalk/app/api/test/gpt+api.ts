import OpenAI from 'openai';

export async function POST(request: Request) {
    const apiKey = process.env.OPENAI_API_KEY;
    const openai = new OpenAI({ apiKey });

    // Parse the prompt from the request body
    const { prompt } = await request.json();

    console.log(prompt);

    try {
        // Make a non-streaming API call
        const response = await openai.chat.completions.create({
            model: "gpt-4",
            messages: [
                { role: "system", content: "You are a helpful assistant." },
                { role: "user", content: prompt },  // Use the prompt from the request body
            ],
            stream: false, // Disable streaming
        });

        // Retrieve and send the entire response content
        const textResponse = response.choices[0].message?.content || '';

        return new Response(textResponse, {
            headers: { "Content-Type": "application/json" },
        });
    } catch (error) {
        console.error("Error processing request:", error);
        return new Response(JSON.stringify({ error: "Failed to fetch response" }), {
            status: 500,
            headers: { "Content-Type": "application/json" },
        });
    }
}
