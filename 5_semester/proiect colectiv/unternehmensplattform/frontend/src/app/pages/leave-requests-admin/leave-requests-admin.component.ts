import {Component, OnInit, ViewChild} from '@angular/core';
import {VacationReqControllerService} from "../../services/services/vacation-req-controller.service";
import {VacationRequestDetailsDto} from "../../services/models/vacation-request-details-dto";
import {UserWithVacationRequestDetailsDto} from "../../services/models/user-with-vacation-request-details-dto";
import {Table} from "primeng/table";
import {VacationRequestStatus} from "../../services/enums/VacationRequestStatus";
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-leave-requests-admin',
  templateUrl: './leave-requests-admin.component.html',
  styleUrls: ['./leave-requests-admin.component.scss'],
  providers: [MessageService]
})
export class LeaveRequestsAdminComponent implements OnInit {
  requests: VacationRequestDetailsDto[] = [];
  employeesWithRequests: UserWithVacationRequestDetailsDto[] = [];
  expandedRows: { [key: number]: boolean } = {};
  searchValue: string = '';
  @ViewChild('dt2') dt2: Table | undefined;

  constructor(
    private vacationReqService: VacationReqControllerService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.showPendingRequests();
    this.loadEmployeesWithVacationRequests();
  }

  showPendingRequests(): void {
    this.vacationReqService.getAllPendingVacationRequests().subscribe({
      next: (data: VacationRequestDetailsDto[]) => {
        this.requests = data;
      },
      error: (error) => {
        console.error('Failed to fetch requests:', error);
      }
    });
  }

  onApprove(requestId: number, status: 'New' | 'Approved' | 'Rejected'): void {
    const params = { requestId, status };
    this.vacationReqService.updateRequestStatus(params).subscribe({
      next: () => {
        this.showPendingRequests();
        this.loadEmployeesWithVacationRequests();
      },
      error: (error) => {
        console.log('error', error);
        if (error.status === 400) {
          this.showPendingRequests();
          this.loadEmployeesWithVacationRequests();
          this.messageService.add({
            severity: 'error',
            summary: 'Request Rejected',
            detail: error.error.businessErrorDescription,
            key: 'decline-request-overlap-activity'
          });

        } else {
          console.error(`Failed to update request ${requestId}:`, error);
        }
      },
    });
  }

  onDecline(requestId: number, status: 'New' | 'Approved' | 'Rejected'): void {
    const params = { requestId, status };
    this.vacationReqService.updateRequestStatus(params).subscribe({
      next: () => {
        this.showPendingRequests();
        this.loadEmployeesWithVacationRequests();
      },
      error: (error) => {
        if (error.status === 400 && error.error?.message?.includes("Request rejected due to overlap with working days")) {
          this.messageService.add({
            severity: 'error',
            summary: 'Request Rejected',
            detail: 'The request was automatically rejected due to overlap with a working day.'
          });
        } else {
          console.error(`Failed to update request ${requestId}:`, error);
        }
      },
    });
  }

  loadEmployeesWithVacationRequests(): void {
    this.vacationReqService.getAllEmployeesWithVacationRequests().subscribe({
      next: (data) => {
        this.employeesWithRequests = data;
      },
      error: (err) => {
        console.error('Error fetching vacation requests:', err);
      }
    });
  }

  clear(table: any) {
    table.clear();
    this.searchValue = '';
  }

  protected readonly VacationRequestStatus = VacationRequestStatus;
}
