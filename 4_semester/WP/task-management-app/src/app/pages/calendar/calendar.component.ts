import { Component, OnInit, Inject, PLATFORM_ID, signal } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { CalendarOptions, DateSelectArg, EventClickArg, EventApi, Calendar } from '@fullcalendar/core';
import { ChangeDetectorRef } from '@angular/core';
import { EventInput } from '@fullcalendar/core';

import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import interactionPlugin from '@fullcalendar/interaction';
import { AuthService } from '../../services/auth.service';
import { DataTaskService } from '../../services/shared/data-task.service';
import { INITIAL_EVENTS, createEventId } from './event-utils';
import { TaskInMemory } from '../../services/model/task-in-memory';
import { Observer, flatMap, from, forkJoin } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { TaskPopupComponent } from '../../task-popup/task-popup.component';


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private changeDetector: ChangeDetectorRef,
    private authService: AuthService,
    private router: Router,
    private dataTaskService: DataTaskService,
    private dialog: MatDialog // Inject MatDialog

  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  isBrowser: boolean;
  taskList: TaskInMemory[] = [];
  calendarVisible = signal(true);
  calendarOptions: CalendarOptions = {}; // Initialize calendarOptions property

  currentTasks = signal<EventApi[]>([]);


  ngOnInit(): void {
    console.log(this.isBrowser);
    if (this.isBrowser) {
      if (!this.authService.isLoggedIn()) {
        this.router.navigate(['/login']);
        return;
      }
    }

    this.getAllTasks().subscribe(async taskList => {

      const events = this.transformTasksToEvents(taskList);
      this.calendarOptions.events = events;
    });
    // Initialize calendarOptions with INITIAL_EVENTS
    this.calendarOptions = {
      plugins: [
        interactionPlugin,
        dayGridPlugin,
        timeGridPlugin,
        listPlugin,
      ],
      headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek'
      },
      initialView: 'dayGridMonth',
      // initialEvents: INITIAL_EVENTS,
      // initialEvents: this.transformTasksToEvents(this.getAllTasks(this.dataTaskService)),
      weekends: true,
      editable: true,
      selectable: true,
      selectMirror: true,
      dayMaxEvents: true,
      select: this.handleDateSelect.bind(this),
      eventClick: this.handleEventClick.bind(this),
      eventsSet: this.handleEvents.bind(this)
    };

    // Fetch and transform tasks

  }


  handleCalendarToggle() {
    this.calendarVisible.update((bool) => !bool);
  }

  handleWeekendsToggle() {
    this.calendarOptions.weekends = !this.calendarOptions.weekends;
  }

  handleDateSelect(selectInfo: DateSelectArg) {
    // const title = prompt('Please enter a new title for your event');
    // const calendarApi = selectInfo.view.calendar;

    // calendarApi.unselect(); // clear date selection

    // if (title) {
    //   calendarApi.addEvent({
    //     id: '2',
    //     title,
    //     start: selectInfo.startStr,
    //     end: selectInfo.endStr,
    //     allDay: selectInfo.allDay
    //   });
    // }
  }

  handleEventClick(clickInfo: EventClickArg) {
    this.getAllTasks().subscribe(taskList => {
      const taskDetails = taskList.find(task => task.id === clickInfo.event.id); // Assuming taskList contains your TaskInMemory objects
      if (taskDetails) {
        this.dialog.open(TaskPopupComponent, {
          data: {
            event: clickInfo.event,
            task: taskDetails
          }
        });
      }

    });
  }

  handleEvents(events: EventApi[]) {
    this.currentTasks.set(events);
    this.changeDetector.detectChanges(); // workaround for pressionChangedAfterItHasBeenCheckedError
  }

  getAllTasks() {
    return this.dataTaskService.getAllTasks().pipe(
      flatMap((res: any[]) => {
        return from(Promise.all(res.map(async (e: any) => {
          const data = e.payload.doc.data();
          data.id = e.payload.doc.id;
          //console.log("categoru",data.category)
          const categorySnapshot = await data.category.get();
          const categoryData = categorySnapshot.data();
          //console.log("categoryData",categoryData)
          //console.log("categoryData",categoryData.name)

          data.category = categoryData.name;

          const prioritySnapshot = await data.priority.get();
          const priorityData = prioritySnapshot.data();
          data.priority = priorityData.name;
          //console.log("priorityData",priorityData.name)
          const stageSnapshot = await data.stage.get();
          const stageData = stageSnapshot.data();
          data.stage = stageData.name;

          data.dueDate = data.dueDate.toDate();
          //console.log("final",data);
          return data;
        })));
      })
    );
  }

  transformTasksToEvents(tasks: TaskInMemory[]): EventInput[] {
    return tasks.map(task => {
      const startTime = new Date(task.dueDate);
      const year = startTime.getFullYear();
      const month = String(startTime.getMonth() + 1).padStart(2, '0'); // Month is zero-indexed, so add 1
      const day = String(startTime.getDate()).padStart(2, '0');
      const startTimeStr = `${year}-${month}-${day}`;
      // Get the end date by adding one day to the start date
      const endDate = new Date(startTime);
      endDate.setDate(endDate.getDate() + 1);
      const endYear = endDate.getFullYear();
      const endMonth = String(endDate.getMonth() + 1).padStart(2, '0'); // Month is zero-indexed, so add 1
      const endDay = String(endDate.getDate()).padStart(2, '0');
      const endTimeStr = `${endYear}-${endMonth}-${endDay}`;
      console.log("end", endTimeStr);
      return {
        id: task.id,
        title: task.title,
        start: startTimeStr + 'T00:00:00',
        end: endTimeStr + 'T00:00:00',
      };
    });
  }



}

