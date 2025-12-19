import { processSegment } from '../utils/audio/processing+api';
import { outputFilename, TimelineSegment } from '../utils/audio/declarations';
import { segmentTextByLength } from '../utils/audio/manipulation+api';
import { getChatGPTStream } from './gpt_stream+api';
import fs from 'fs';

export async function POST(request: Request): Promise<Response> {
  const { text, generate, prompt } = await request.json();
  const timeline: TimelineSegment[] = [];
  let currentTime = 0;

  // Clear the previous output if the path exists
  if (outputFilename) {
    try {
      console.log(`Checking for existing audio file at: ${outputFilename}`);
      console.log(fs.existsSync(outputFilename));
      if (fs.existsSync(outputFilename)) {
        fs.writeFileSync(outputFilename, '')
        // fs.unlinkSync(outputFilename)
        console.log(`Cleared existing audio file at: ${outputFilename}`);
      }
    } catch (error) {
      console.error(`Error clearing output file: ${error}`);
      return new Response(
          JSON.stringify({ error: 'Failed to clear previous audio output' }),
          { status: 500 }
      );
    }
  }

  if (text) {
    // Use segmentTextByLength for direct text input
    const segments = segmentTextByLength(text, 150);

    console.log(`Segments: ${segments.length}`);
    console.log(segments);
    
    for (const segment of segments) {
      const segmentData = await processSegment(segment, currentTime);
      timeline.push(segmentData);
      currentTime = segmentData.to;
    }
  } 
  else if (generate && prompt) {
    // Fetch the text stream from ChatGPT endpoint
    console.log(`Generating text from prompt: ${prompt}`);
    // const chatResponse = await fetch('app/api/gpt_stream', {
    //   method: 'POST',
    //   headers: { 'Content-Type': 'application/json' },
    //   body: JSON.stringify({ prompt })
    // });
    const chatResponse = await getChatGPTStream(prompt);

    const chatStreamReader = chatResponse?.getReader();

    if (!chatStreamReader) {
      return new Response("Failed to read ChatGPT stream", { status: 500 });
    }

    let textBuffer = '';
    const targetWordCount = 50;

    while (true) {
      const { done, value } = await chatStreamReader.read();
      if (done) break;
      
      const textChunk = new TextDecoder().decode(value);
      textBuffer += textChunk;
      console.log(textChunk);
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
  } else {
    return new Response(JSON.stringify({ error: "Invalid request parameters" }), { status: 400 });
  }

  // Return the timeline as JSON
  return new Response(JSON.stringify(timeline), {
    headers: { "Content-Type": "application/json" },
  });
}
