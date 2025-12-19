import { chatStreamUrl, TimelineSegment } from "@/app/utils/audio/declarations";
import { processSegment } from "@/app/utils/audio/processing";

export async function POST(request: Request): Promise<Response> {
    const { prompt } = await request.json();
    const timeline: TimelineSegment[] = [];
    let currentTime = 0;

    if (!prompt) return new Response("Prompt is required", { status: 400 });
  
    // Fetch the text stream from ChatGPT endpoint
    const chatResponse = await fetch(chatStreamUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ prompt })
    });
    const chatStreamReader = chatResponse.body?.getReader();

    if (!chatStreamReader) return new Response("Failed to read ChatGPT stream", { status: 500 });

    let textBuffer = '';
    const targetWordCount = 50;

    while (true) {
        const { done, value } = await chatStreamReader.read();
        if (done) break;
        
        const textChunk = new TextDecoder().decode(value);
        textBuffer += textChunk;

        const wordCount = textBuffer.split(/\s+/).length;
        if (wordCount >= targetWordCount) {
            const segment = textBuffer.trim();
            const segmentData = await processSegment(segment, currentTime);
            timeline.push(segmentData);
            currentTime = segmentData.to;

            // Reset text buffer after processing
            textBuffer = '';
        }
    }

    // Process remaining words in the buffer
    if (textBuffer) {
        const segmentData = await processSegment(textBuffer.trim(), currentTime);
        timeline.push(segmentData);
    }
  
    // Return the timeline as JSON
    return new Response(JSON.stringify(timeline), {
      headers: { "Content-Type": "application/json" },
    });
  }
  