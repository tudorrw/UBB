import { EventInput } from '@fullcalendar/core';
import { TaskInMemory } from '../../services/model/task-in-memory';
import { Observer, flatMap, from, forkJoin } from 'rxjs';
import { Observable } from 'rxjs';
import { DataTaskService } from '../../services/shared/data-task.service';


let eventGuid = 0;
const TODAY_STR = new Date().toISOString().replace(/T.*$/, ''); // YYYY-MM-DD of today

// export const INITIAL_TASKS: Observable<TaskInMemory[]> = getAllTasks(DataTaskService);

export var INITIAL_TASKS2: EventInput[] = [];


export const INITIAL_EVENTS: EventInput[] = [
  {
    id: createEventId(),
    title: 'All-day event',
    start: TODAY_STR
  },
  {
    id: createEventId(),
    title: 'Timed event',
    start: TODAY_STR + 'T00:00:00',
    end: TODAY_STR + 'T03:00:00'
  },
  {
    id: createEventId(),
    title: 'Timed event',
    start: TODAY_STR + 'T12:00:00',
    end: TODAY_STR + 'T15:00:00',
    setAllDay: true
  }
];

export function createEventId() {
  return String(eventGuid++);
}




