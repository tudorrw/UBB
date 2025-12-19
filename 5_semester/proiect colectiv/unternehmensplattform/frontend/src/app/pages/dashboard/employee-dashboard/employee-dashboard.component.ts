import {Component, OnInit, ViewChild} from '@angular/core';
import { MessageService } from 'primeng/api';
import { UserDetailsDto } from '../../../services/models/user-details-dto';
import { UserCrudControllerService } from '../../../services/services/user-crud-controller.service';
import { VacationRequestDto } from '../../../services/models/vacation-request-dto';
import { VacationReqControllerService } from '../../../services/services/vacation-req-controller.service';

import {HttpErrorResponse} from "@angular/common/http";
import {VacationRequestStatus} from "../../../services/enums/VacationRequestStatus";
import {VacationRequestDetailsDto} from "../../../services/models/vacation-request-details-dto";
import {NgForm} from "@angular/forms";
import {DatePipe} from "@angular/common";


@Component({
    selector: 'app-employee-dashboard',
    templateUrl: './employee-dashboard.component.html',
    styleUrls: ['./employee-dashboard.component.scss'],
    providers: [MessageService, DatePipe],
})
export class EmployeeDashboardComponent implements OnInit {
  @ViewChild('vacationForm') vacationForm!: NgForm; // Access the form

  userDetails: UserDetailsDto | null = null;
  showVacationDetailsDialog = false;
  requestVacationForm = false;
  dialogWidth: string = '25rem';
  dialogHeight: string = '20rem';
  messages: any[] = [];  // Mesaje pentru formularul de cerere
  messages1: any[] = []; // Mesaje pentru tabelul de cereri
  availableAdministrators: UserDetailsDto[] = [];
  selectedAdministrator: UserDetailsDto | undefined;
  vacationRequests: VacationRequestDetailsDto[] = [];
  minDate: Date | undefined;
  maxDate: Date | undefined;
  invalidDates: Date[] = [];
  date5: Date[] = [];
  description: string = '';

  constructor(
      private userCrudControllerService: UserCrudControllerService,
      private messageService: MessageService,
      private vacationReqControllerService: VacationReqControllerService,
      private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
      this.fetchUserDetails();
      this.fetchAvailableAdministrators();
      this.fetchVacationRequests();
  }


  //functii pt butonul de see more details
  viewVacationDetails(): void {
      this.showVacationDetailsDialog = true;
  }
  fetchUserDetails(): void {
      this.userCrudControllerService.authenticatedUser().subscribe({
          next: (data: UserDetailsDto) => {
              this.userDetails = data;
              this.setVacationMessage();

            this.updateDateRangesAndInvalidDates();
          },
          error: (error) => {
              console.error('Failed to fetch user details:', error);
              this.messageService.add({
                  severity: 'error',
                  summary: 'Error',
                  detail: 'Unable to fetch user details.',
                  life: 3000,
              });
          },
      });
  }

  updateDateRangesAndInvalidDates(): void {
    // Initialize minDate to the current date
    this.minDate = new Date();

    // Set maxDate to be one year ahead from today (current year + 1, May 30)
    this.maxDate = new Date(this.minDate.getFullYear() + 1, 5, 30);

    // Ensure that minDate is at least the signing date, if it's provided
    const signingDate = this.userDetails?.signingDate ? new Date(this.userDetails.signingDate) : null;
    if (signingDate && signingDate > this.minDate) {
      this.minDate = signingDate; // Set minDate to signingDate if it's later than today's date
    }

    // Fetch weekends (invalid dates) between minDate and maxDate
    this.invalidDates = this.getWeekendsBetweenDates(this.minDate, this.maxDate);
  }

  //functii pt butonul de request vacation
  requestVacation(): void {
      this.requestVacationForm = true;
      this.dialogWidth = '25rem';
      this.dialogHeight = '20rem';
      this.setVacationMessage();
  }

  selectAdministrator(admin: UserDetailsDto): void {
    this.selectedAdministrator = admin;
  }
  fetchAvailableAdministrators(): void {
      this.vacationReqControllerService.getAvailableAdministrators().subscribe({
          next: (data: UserDetailsDto[]) => {
              this.availableAdministrators = data;
          },
          error: (error) => {
              console.error('Failed to fetch administrators:', error);
          }
      });
  }

  getWeekendsBetweenDates(startDate: Date, endDate: Date): Date[] {
      const weekends: Date[] = [];
      let date = new Date(startDate);

      while (date <= endDate) {
          if (date.getDay() === 6 || date.getDay() === 0) {
              weekends.push(new Date(date));
          }
          date.setDate(date.getDate() + 1);
      }

      return weekends;
  }

  calculateDaysDifference(startDate: any, endDate: any): number {
      const start = new Date(startDate);
      const end = new Date(endDate);

      if (isNaN(start.getTime()) || isNaN(end.getTime())) {
          console.error('Invalid date format');
          return 0;
      }

    let count = 0;
    let currentDate = new Date(start);

    // Loop through each day in the range
    while (currentDate <= end) {
      const dayOfWeek = currentDate.getDay();
      // Count the day if it's not a weekend (0 = Sunday, 6 = Saturday)
      if (dayOfWeek !== 0 && dayOfWeek !== 6) {
        count++;
      }
      // Move to the next day
      currentDate.setDate(currentDate.getDate() + 1);
    }
    return count;
  }

  submitVacationRequest(form: NgForm): void {
    if(this.description && this.selectedAdministrator && this.date5.length) {
      var formattedStartDate = this.datePipe.transform(this.date5[0], 'yyyy-MM-dd');
      if(this.date5[1] == null) {
        this.date5[1] = this.date5[0];
      }
      var formattedEndDate = this.datePipe.transform(this.date5[1], 'yyyy-MM-dd');
      const daysSelected = this.calculateDaysDifference(formattedStartDate, formattedEndDate);
      console.log(daysSelected, this.userDetails?.actualYearVacationDays)
      // Verifică dacă zilele selectate sunt mai multe decât cele disponibile
      if (daysSelected > (this.userDetails?.actualYearVacationDays || 0)) {
        this.messages = [
          {
            severity: 'error',
            summary: 'Not enough vacation days',
            detail: `You have only ${this.userDetails?.actualYearVacationDays} vacation days left. Please select a shorter period.`,
          },
        ];
        return;
      }

      const vacationRequest: VacationRequestDto = {
        description: this.description,
        startDate: formattedStartDate ? formattedStartDate : undefined,
        endDate: formattedEndDate ? formattedEndDate : undefined,
        assignedAdministratorId: this.selectedAdministrator?.id,
      };
      console.log(vacationRequest);

      // Trimite cererea de vacanță
      this.vacationReqControllerService.createVacationRequest({
        body: vacationRequest,
      }).subscribe({
        next: (response) => {
          this.messages = [
            {
              severity: 'success',
              summary: 'Vacation Request Submitted',
              detail: 'Your vacation request has been successfully submitted.',
            },
          ];
          this.fetchUserDetails()
          this.fetchVacationRequests();
          this.requestVacationForm = false;
          form.resetForm();

        },
        error: (error: HttpErrorResponse) => {
          console.error('Error submitting vacation request:', error);
          if (error.status === 409) {
            this.messages = [{severity: 'error', summary: 'Conflict', detail: error.error.error}];
          } else {
            this.messages = [{
              severity: 'error',
              summary: 'Error',
              detail: error.error.error
            }];
          }
        },
      });
    }else {
      this.messages = [{
        severity: 'error',
        summary: 'validation Error',
        detail: 'Complete the required fields!'
      }];
    }
  }

//mesaje pentru dialogul de req vac
  setVacationMessage(): void {
      if (this.userDetails?.actualYearVacationDays !== undefined) {
          this.messages = [
              {
                  severity: 'info',
                  summary: 'Attention',
                  detail: `You have ${this.userDetails.actualYearVacationDays} vacation days left this year.`,
              },
          ];
      } else {
          this.messages = [
              {
                  severity: 'error',
                  summary: 'Error',
                  detail: 'No vacation days left or data unavailable.',
              },
          ];
      }
  }
  onSelect(event: any): void {
      if (!event || event.length === 0) {
          this.messages = [
              {
                  severity: 'warn',
                  summary: 'No dates selected',
                  detail: 'Please select a vacation period before submitting.',
              },
          ];
      } else {
          this.messages = [];
      }
  }

  //functii pt tabel
  setVacationTableMessage(): void {
      // Mesaj pentru zilele de vacanță rămase
      if (this.userDetails?.actualYearVacationDays !== undefined) {
          this.messages1 = [
              {
                  severity: 'info',
                  summary: 'Vacation Days Remaining',
                  detail: `You have ${this.userDetails.actualYearVacationDays} vacation days left this year.`,
              },
          ];
      }

      // Mesaj pentru cererile de vacanță
      if (this.vacationRequests.length === 0) {
          this.messages1.push({
              severity: 'info',
              summary: 'No vacation requests',
              detail: 'Currently, there are no vacation requests in the system.',
          });
      }
  }
  fetchVacationRequests(): void {
      this.vacationReqControllerService.getVacationRequestsByEmployee().subscribe({
          next: (data: any) => {
              this.vacationRequests = data as VacationRequestDetailsDto[];
              console.log('Fetched vacation requests:', this.vacationRequests);
              this.setVacationTableMessage();
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


  deleteVacationRequest(requestId: number): void {
    this.vacationReqControllerService.deleteVacationRequest({ requestId }).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Vacation Request deleted successfully',
          key: 'save-delete-vac-req',
          life: 2500
        });

        this.fetchUserDetails();
        this.fetchVacationRequests();
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to delete vacation request.',
          key: 'cancel-delete-vac-req',
          life: 2500
        });
      },
    });
  }
  onCancelAddVacReqForm(form: NgForm): void {
      form.resetForm();
      this.requestVacationForm = false;
  }

  downloadPdf(requestId: number): void {
    this.vacationReqControllerService.downloadVacationRequestPdf({ requestId }).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = url;
        a.download = `VacationRequest_${requestId}.pdf`;

        a.click();

        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        console.error('Error downloading PDF:', err);
      },
    });
  }


  protected readonly VacationRequestStatus = VacationRequestStatus;
}
