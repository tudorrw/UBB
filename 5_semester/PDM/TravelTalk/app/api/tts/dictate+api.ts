import { TimelineSegment } from "@/app/utils/audio/declarations";
import { segmentTextByLength } from "@/app/utils/audio/manipulation";
import { processSegment } from "@/app/utils/audio/processing";

export async function POST(request: Request): Promise<Response> {
    const { text } = await request.json();
    const timeline: TimelineSegment[] = [];
    let currentTime = 0;
  
    if (!text) return new Response("Text is required", { status: 400 });

    // Use segmentTextByLength for direct text input
    const segments = segmentTextByLength(text, 150);
    for (const segment of segments) {
        const segmentData = await processSegment(segment, currentTime);
        timeline.push(segmentData);
        currentTime = segmentData.to;
    }
  
    // Return the timeline as JSON
    return new Response(JSON.stringify(timeline), {
      headers: { "Content-Type": "application/json" },
    });
  }