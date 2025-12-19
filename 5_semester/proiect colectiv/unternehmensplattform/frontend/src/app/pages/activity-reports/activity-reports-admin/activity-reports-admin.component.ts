import { Component, OnInit } from '@angular/core';
import { CalendarOptions } from '@fullcalendar/core';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimeGridPlugin from '@fullcalendar/resource-timegrid';
import { WorkingDaysControllerService } from '../../../services/services/working-days-controller.service';

@Component({
  selector: 'app-activity-reports-admin',
  templateUrl: './activity-reports-admin.component.html',
  styleUrls: ['./activity-reports-admin.component.scss'],
})
export class ActivityReportsAdminComponent implements OnInit {
  private colorRotation: string[] = ['#c5b514', '#f39c12', '#8e44ad', '#27ae60', '#c0392b'];
  calendarOptions: CalendarOptions = {
    initialView: 'resourceTimeGridDay',
    plugins: [resourceTimeGridPlugin, interactionPlugin],
    datesSet: (info) => this.onDateChange(new Date(info.startStr)),
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'resourceTimeGridDay',
    },
    weekends: false,
    resources: [],
    events: [],
    selectable: true,
    dateClick: (info) => {
      alert(`Clicked ${info.dateStr} on resource ${info.resource?.id}`);
    },
    select: (info) => {
      alert(`Selected from ${info.startStr} to ${info.endStr} on resource ${info.resource?.id}`);
    },
    scrollTime: '07:00',
    slotMinTime: '07:00:00',
    slotMaxTime: '24:00:00',
    slotMinWidth: 100,
    timeZone: 'UTC',
  };

  resources: any[] = []; // Separate variable for resources
  employeeDailyTotals: { [key: string]: { hours: number; minutes: number } } = {};
  currentDate: string = '';
  constructor(private workingDaysService: WorkingDaysControllerService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.workingDaysService.getEmployeesWithWorkingDays().subscribe((data) => {

      // Map resources (employees)
      this.resources = data.map((employee) => ({
        id: String(employee.userDetailsDTO.id),
        title: `${employee.userDetailsDTO.firstName} ${employee.userDetailsDTO.lastName}`,
      }));

      // Clear totals for the current day
      const dailyTotals: { [key: string]: { hours: number; minutes: number } } = {};

      const events = data.flatMap((employee) =>
        employee.workingDays?.map((activityReport) => {
          const effectiveTime = activityReport.effectiveTime ?? '';
          const timeMatch = effectiveTime.match(/(\d+)\s*hours?\s*(\d+)\s*minutes?/);

          if (timeMatch) {
            const hours = parseInt(timeMatch[1], 10);
            const minutes = parseInt(timeMatch[2], 10);

            const employeeId = String(employee.userDetailsDTO.id);
            const activityDate = activityReport.date;
            const key = `${employeeId}_${activityDate}`;

            if (!dailyTotals[key]) {
              dailyTotals[key] = { hours: 0, minutes: 0 };
            }

            dailyTotals[key].hours += hours;
            dailyTotals[key].minutes += minutes;
          }

          return {
            id: String(activityReport.id),
            resourceId: String(employee.userDetailsDTO.id),
            start: activityReport.startDate,
            end: activityReport.endDate,
            title: activityReport.description + ' - ' + effectiveTime,
            describe: activityReport.effectiveTime,
            color: this.getUserColor(employee.userDetailsDTO.id),
          };
        }) || []
      );

      Object.keys(dailyTotals).forEach((key) => {
        const totals = dailyTotals[key];
        totals.hours += Math.floor(totals.minutes / 60);
        totals.minutes = totals.minutes % 60;
      });

      this.employeeDailyTotals = dailyTotals;

      this.calendarOptions = {
        ...this.calendarOptions,
        resources: this.resources,
        events,
      };

      console.log('Daily totals for the current day:', this.employeeDailyTotals);
    });
  }

  onDateChange(newDate: Date): void {
    this.currentDate = newDate.toISOString().split('T')[0];
    this.fetchData();
  }
  private getUserColor(userId: number | undefined): string {
    if (!userId) return '#ccc'; // Default gray color if user ID is undefined
    const index = userId % this.colorRotation.length; // Rotate colors based on user ID
    return this.colorRotation[index];
  }
}
