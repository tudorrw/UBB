export const DEEPGRAM_URL = "https://api.deepgram.com/v1/speak?model=aura-helios-en";
export const DEEPGRAM_API_KEY = process.env.DEEPGRAM_API_KEY;
export const outputFilename = process.env.OUTPUT_FILENAME;
export const OPENAI_API_KEY = process.env.OPENAI_API_KEY;
export const chatStreamUrl = "api/gpt_stream";

export interface TimelineSegment {
    sequence: string;
    from: number;
    to: number;
}