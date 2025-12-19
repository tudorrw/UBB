import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { CalendarOptions, EventInput } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import { WorkingDaysControllerService } from '../../../services/services/working-days-controller.service';
import { WorkingDaysDto } from '../../../services/models/working-days-dto';
import { HttpErrorResponse } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { DatePipe } from '@angular/common';
import { NgForm } from '@angular/forms';
import {VacationReqControllerService} from "../../../services/services/vacation-req-controller.service";
import {VacationRequestDetailsDto} from "../../../services/models/vacation-request-details-dto";
import {UserDetailsDto} from "../../../services/models/user-details-dto";
import {UserCrudControllerService} from "../../../services/services/user-crud-controller.service";

interface ExtendedEventProps {
    description?: string;
}

type CustomEventInput = EventInput & {
    extendedProps?: ExtendedEventProps;
};

@Component({
    selector: 'app-activity-reports-employee',
    templateUrl: './activity-reports-employee.component.html',
    styleUrls: ['./activity-reports-employee.component.scss'],
    encapsulation: ViewEncapsulation.Emulated,
    providers: [MessageService, DatePipe], // Asigură-te că DatePipe este în providers
})
export class ActivityReportsEmployeeComponent implements OnInit {
    activityDialog: boolean = false;
    detailDialog: boolean = false;
    selectedEventId: number | undefined = undefined;
    selectedDate: Date | null = null;
    startDate: Date | null = null;
    endDate: Date | null = null;
    description: string = '';
    events: CustomEventInput[] = [];
    userDetails: UserDetailsDto | null = null;
    vacationRequests: VacationRequestDetailsDto[] = [];
    isEditMode: boolean = false;
    messages: any[] = [];

    calendarOptions: CalendarOptions = {
        initialView: 'timeGridWeek',
        plugins: [interactionPlugin, dayGridPlugin, timeGridPlugin, listPlugin],
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek',
        },
        selectable: true,
        dateClick: this.onDateClick.bind(this),
        eventClick: this.onEventClick.bind(this),
        events: this.events,
        eventColor: '#378006',
        timeZone: 'UTC',
        weekends: false,
        scrollTime: '07:00',
        slotMinTime: '07:00:00',
        slotMaxTime: '24:00:00',
    };

    constructor(
        private workingDaysService: WorkingDaysControllerService,
        private vacationReqControllerService: VacationReqControllerService,
        private userCrudControllerService: UserCrudControllerService,
        private datePipe: DatePipe,
        private messageService: MessageService // Injectează MessageService pentru Toast
    ) {}

    ngOnInit() {
        this.fetchUserDetails();
        this.fetchVacationRequests();
        this.loadEvents();

    }

    fetchUserDetails(): void {
      this.userCrudControllerService.authenticatedUser().subscribe({
        next: (data: UserDetailsDto) => {
          this.userDetails = data;
          // Set the signingDate as the start date in the calendar valid range
          const signingDate = data.signingDate;
          if (signingDate) {
            const formattedDate = this.formatDate(signingDate);
            this.calendarOptions.validRange = {start: formattedDate};
          }
        },
        error: (err) => {
          console.error('Error fetching user details:', err);
        }
      });
    }


    formatDate(date: string): string {
      const parsedDate = new Date(date);
      const year = parsedDate.getFullYear();
      const month = String(parsedDate.getMonth() + 1).padStart(2, '0');
      const day = String(parsedDate.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    }

    loadEvents() {
        this.workingDaysService.getAllActivityReports().subscribe({
            next: (data: WorkingDaysDto[]) => {
                const activityReports = data.map((report) => {
                    return {
                        id: report.id?.toString(),
                        start: report.startDate,
                        end: report.endDate,
                        title: report.description || 'Activity',
                        backgroundColor: '#007bff',
                        extendedProps: {
                            description: report.description,
                        },
                    };
                });
                this.vacationReqControllerService.getApprovedVacationRequestsByEmployee().subscribe({
                next: (vacationRequests: VacationRequestDetailsDto[]) => {
                  const vacationEvents = vacationRequests
                    .map((request) => ({
                      id: `vacation-${request.id}`,
                      start: request.startDate,
                      // end: request.endDate,
                      end: new Date(new Date(request.endDate as string).setDate(new Date(request.endDate as string).getDate() + 1)).toISOString().split('T')[0],
                      title: request.description || 'Vacation',
                      backgroundColor: '#f39c12', // Vacation events in a distinct color
                      editable: false, // Prevent users from editing vacation events
                      extendedProps: {
                        type: 'vacation',
                        description: request.description,
                      },
                    }));

                  // Combine both activity and vacation events
                  this.events = [...activityReports, ...vacationEvents];
                  this.calendarOptions.events = this.events; // Update the calendar options
                },
                error: (error: HttpErrorResponse) => {
                  console.error('Error fetching vacation requests:', error);
                  this.messageService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Unable to fetch vacation requests.',
                    life: 5000,
                  });
                },
              });
              },
            error: (error: HttpErrorResponse) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Error loading events',
                    detail: error.error.message || 'An error occurred while loading events.',
                    life: 5000,
                });
            },
        });
    }

    submitActivity(form: NgForm): void {
        if (this.description && this.startDate && this.endDate && this.selectedDate) {
            const formattedStartDate = this.datePipe.transform(this.startDate, 'yyyy-MM-ddTHH:mm:ss') + 'Z';
            const formattedEndDate = this.datePipe.transform(this.endDate, 'yyyy-MM-ddTHH:mm:ss') + 'Z';
            const formattedSelectedDate = this.datePipe.transform(this.selectedDate, 'yyyy-MM-dd');

            if (!formattedStartDate || !formattedEndDate || !formattedSelectedDate) {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Invalid Dates',
                    detail: 'Please select valid start, end, and selected dates.',
                    life: 5000,
                });
                return;
            }

            const activity: WorkingDaysDto = {
                id: this.selectedEventId,
                description: this.description,
                startDate: formattedStartDate,
                endDate: formattedEndDate,
                date: formattedSelectedDate,
            };

            if (this.isEditMode && this.selectedEventId !== undefined) {
                this.workingDaysService.modifyWorkingDay({ body: activity }).subscribe({
                    next: () => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Activity Updated',
                            detail: 'Your activity has been successfully updated.',
                            key: 'save-modify-working-day',
                            life: 2500,
                        });
                        this.loadEvents();
                        this.activityDialog = false;
                        form.resetForm();
                        this.isEditMode = false;
                    },
                    error: (error) => {
                        console.error('Error updating activity:', error);
                        if (error.status === 409) {
                            this.messages = [{
                                severity: 'error',
                                summary: 'Conflict',
                                detail: error.error.businessErrorDescription,
                            }];
                        } else if(error.status === 400){
                            this.messages = [{
                                severity: 'error',
                                summary: 'Error',
                                detail: error.error.businessErrorDescription,
                            }];
                        }
                        if (error.error.validationErrors) {
                          this.messages = [{
                            severity: 'error',
                            summary: 'Error',
                            detail: error.error.validationErrors[0],
                          }];
                        }
                    },
                });
            } else {
                this.workingDaysService.createWorkingDay({ body: activity }).subscribe({
                    next: () => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Activity Submitted',
                            detail: 'Your activity has been successfully submitted.',
                            key: 'save-create-working-day',
                            life: 2500,
                        });
                        this.loadEvents();
                        this.activityDialog = false;
                        form.resetForm();
                    },
                    error: (error ) => {
                        console.error('Error submitting activity:', error);
                        if (error.status === 409) {
                            this.messages = [{
                              severity: 'error',
                              summary: 'Conflict',
                              detail: error.error.businessErrorDescription,
                            }];
                        } else if(error.status === 400){
                          this.messages = [{
                            severity: 'error',
                            summary: 'Error',
                            detail: error.error.businessErrorDescription,
                          }];
                        }
                      if (error.error.validationErrors) {
                        this.messages = [{
                          severity: 'error',
                          summary: 'Error',
                          detail: error.error.validationErrors[0],
                        }];
                      }
                    },
                });
            }
        } else {
          this.messages =  [{
            severity: 'error',
            summary: 'validation Error',
            detail: 'Complete the required fields!'
          }];
        }
    }

  onDateClick(info: any): void {
    const clickedDate = new Date(info.dateStr);
    const isVacationDay = this.vacationRequests.some((request) => {
      const startDate = new Date(request.startDate as string);
      const endDate = new Date(request.endDate as string);
      return (
        clickedDate >= startDate &&
        clickedDate <= endDate
      );
    });
    console.log(isVacationDay);
    if (isVacationDay) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Vacation Day',
        detail: 'You cannot add activity reports on vacation days.',
        key: 'warn-vacation-event',
        life: 3000,
      });
      return; // Prevent opening the activity dialog
    }
    this.selectedEventId = undefined;  // Reset selectedEventId
    // this.selectedDate = info.dateStr ;
    this.selectedDate = clickedDate;

    this.startDate = this.parseUTCDate(info.dateStr);
    this.endDate = new Date(info.dateStr);
    this.description = '';
    this.activityDialog = true;
    this.isEditMode = false;
  }

  onEventClick(info: any): void {
    const event = this.events.find((e) => e.id === info.event.id);
    if (event && event.extendedProps) {
      console.log('Event clicked:', event.extendedProps);
      if (event.extendedProps['type'] === 'vacation') {
        this.messageService.add({
          severity: 'info',
          summary: 'Vacation Event',
          detail: 'You cannot edit or interact with vacation events.',
          key: 'info-vacation-event',
          life: 3000,
        });
        return; // Prevent further action
      }
      this.selectedEventId = event.id ? parseInt(event.id as string, 10) : undefined;

      this.startDate = new Date(Date.UTC(
        new Date(event.start as string).getUTCFullYear(),
        new Date(event.start as string).getUTCMonth(),
        new Date(event.start as string).getUTCDate(),
        new Date(event.start as string).getUTCHours() - 2,
        new Date(event.start as string).getUTCMinutes()
      ));

      this.endDate = new Date(Date.UTC(
        new Date(event.end as string).getUTCFullYear(),
        new Date(event.end as string).getUTCMonth(),
        new Date(event.end as string).getUTCDate(),
        new Date(event.end as string).getUTCHours() - 2,
        new Date(event.end as string).getUTCMinutes()
      ));

      this.description = event.extendedProps.description || '';
      this.selectedDate = new Date(this.startDate.toISOString());
      this.detailDialog = true;
      this.isEditMode = true;
    }
  }

    deleteActivity(eventId: string) {
        const params = { requestId: parseInt(eventId, 10) };
        this.workingDaysService.deleteWorkingDay(params).subscribe({
            next: () => {
                this.loadEvents();
                this.detailDialog = false;
                this.messageService.add({
                    severity: 'success',
                    summary: 'Activity Deleted',
                    detail: 'The activity has been successfully deleted.',
                  key: 'save-delete-working-day',
                  life: 5000,
                });
            },
            error: (error: HttpErrorResponse) => {
                console.error('Error deleting activity:', error);
                this.messageService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Failed to delete the activity.',
                    key: 'cancel-delete-working-day',
                    life: 5000,
                });
            },
        });
    }

    openEditDialog() {
        this.activityDialog = true;
        this.isEditMode = true;
        this.detailDialog = false;
    }
  onCancelSubmitWorkingDayForm(form: NgForm) {
    form.resetForm();
    this.activityDialog = false;
  }

  parseUTCDate(dateString: string): Date {
    // Use the Date constructor explicitly with `Date.UTC`
    const date = new Date(dateString);
    return new Date(Date.UTC(
      date.getUTCFullYear(),
      date.getUTCMonth(),
      date.getUTCDate(),
      date.getUTCHours() - 2,
      date.getUTCMinutes(),
    ));
  }

  fetchVacationRequests(): void {
    this.vacationReqControllerService.getApprovedVacationRequestsByEmployee().subscribe({
      next: (data: any) => {
        this.vacationRequests = data as VacationRequestDetailsDto[];
      },
      error: (error) => {
        console.error('Failed to fetch vacation requests:', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Unable to fetch vacation requests.',
          life: 3000,
        });
      },
    });
  }


}

