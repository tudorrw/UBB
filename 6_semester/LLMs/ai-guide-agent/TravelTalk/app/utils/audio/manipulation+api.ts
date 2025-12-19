import https, { RequestOptions } from 'https';
import ffmpeg from 'fluent-ffmpeg';
import { DEEPGRAM_API_KEY, DEEPGRAM_URL } from './declarations';
import fs from 'fs';

export function segmentTextByLength(text: string, maxLength: number): string[] {
    const segments: string[] = [];
    let currentSegment = "";
  
    text.split(" ").forEach((word) => {
      if ((currentSegment + word).length > maxLength) {
        segments.push(currentSegment.trim());
        currentSegment = word + " ";
      } else {
        currentSegment += word + " ";
      }
    });
  
    if (currentSegment) {
      segments.push(currentSegment.trim());
    }
  
    return segments;
  }
  
export function synthesizeAudio(text: string): Promise<Buffer> {
    return new Promise((resolve, reject) => {
      const payload = JSON.stringify({ text });
      const options: RequestOptions = {
        method: "POST",
        headers: {
          Authorization: `Token ${DEEPGRAM_API_KEY}`,
          "Content-Type": "application/json",
        },
      };
  
      const req = https.request(DEEPGRAM_URL, options, (res) => {
        const data: Buffer[] = [];
  
        res.on("data", (chunk: Buffer) => {
          data.push(chunk);
        });
  
        res.on("end", () => {
          resolve(Buffer.concat(data));
        });
      });
  
      req.on("error", (error: Error) => {
        reject(error);
      });
  
      req.write(payload);
      req.end();
    });
  }
  
export function getAudioDuration(audioBuffer: Buffer): Promise<number> {
    return new Promise((resolve, reject) => {
      const tempFile = 'temp_segment.mp3';
      fs.writeFileSync(tempFile, audioBuffer);
  
      ffmpeg.ffprobe(tempFile, (err, metadata) => {
        if (err) {
          reject(err);
        } else {
          const duration = metadata?.format?.duration ?? 0;
          fs.unlinkSync(tempFile);
          resolve(duration);
        }
      });
    });
  }