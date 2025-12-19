import fs from 'fs';

import { synthesizeAudio, getAudioDuration } from './manipulation+api';
import { outputFilename, TimelineSegment } from './declarations';

 export async function processSegment(segment: string, currentTime: number): Promise<TimelineSegment> {
    const audioData = await synthesizeAudio(segment);
    const duration = await getAudioDuration(audioData);
    fs.appendFileSync(outputFilename, audioData);
  
    return {
      sequence: segment,
      from: currentTime,
      to: currentTime + duration,
    };
  }