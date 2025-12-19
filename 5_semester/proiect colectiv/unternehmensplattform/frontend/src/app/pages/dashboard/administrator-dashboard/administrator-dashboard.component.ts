import {Component, OnInit, ViewChild} from '@angular/core';
import { MessageService} from "primeng/api";
import { UserDetailsDto } from "../../../services/models/user-details-dto";
import { UserCrudControllerService } from "../../../services/services/user-crud-controller.service";
import {Table} from "primeng/table";
import {RegistrationRequest} from "../../../services/models/registration-request";
import {DatePipe} from "@angular/common";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-administrator-dashboard',
  templateUrl: './administrator-dashboard.component.html',
  styleUrls: ['./administrator-dashboard.component.scss'],
  providers: [MessageService, DatePipe]
})
export class AdministratorDashboardComponent implements OnInit {
  @ViewChild('employeeForm') employeeForm!: NgForm; // Access the form
  @ViewChild('dt1') dt1: Table | undefined;

  employees: UserDetailsDto[] = [];
  clonedEmployees: { [s: string]: UserDetailsDto } = {};
  searchValue: string = '';
  loading: boolean = true;
  showDialog: boolean = false;
  minDate!: Date;
  maxDate!: Date;
  newEmployee: RegistrationRequest = {
    email: '',
    firstName: '',
    lastName: '',
    telefonNumber: '',
    passwordHash: '',
    signingDate: '',
  };


  constructor(
    private userCrudService: UserCrudControllerService,
    private messageService: MessageService,
    private datePipe: DatePipe
  ) {
  }

  ngOnInit(): void {
    this.showAllEmployees();
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear, 0, 1);
    this.maxDate = new Date(currentYear, 11, 31);
    console.log(this.minDate, this.maxDate);

  }

  clear(table: Table) {
    table.clear();
    this.searchValue = ''
  }


  showAllEmployees(): void {
    this.userCrudService.getAllEmployees().subscribe({
      next: (data: UserDetailsDto[]) => {
        this.employees = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Failed to fetch employees:', error);
        this.loading = false;
      }
    });
  }

  onRowEditInit(employee: UserDetailsDto) {
    this.clonedEmployees[employee.id!] = {...employee};
  }

  onRowEditSave(employee: UserDetailsDto) {
    if (!employee.firstName || !employee.lastName || !employee.email) {
      this.messageService.add({
        severity: 'error',
        summary: 'Validation Error',
        detail: 'First Name, Last Name, and Email cannot be empty.',
      });
      return; // Prevent saving
    }
    if (!this.validateEmail(employee.email) || !this.validatePhoneNumber(employee.telefonNumber)) {
      return; // Prevent saving if validation fails
    }
    this.updateEmployee(employee);

  }

  validateEmail(email: string): boolean {
    if (!email || email.trim() === '') {
      return true; // No validation error for empty email
    }
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailRegex.test(email);
  }

  validatePhoneNumber(phone: string | undefined): boolean {
    if (!phone || phone.trim() === '') {
      return true; // No validation error for empty phone
    }
    const phoneRegex = /^(07|03)[0-9]{8}$/;
    return phoneRegex.test(phone);
  }


  formatPhoneNumber(phone: string): string {
    if (!phone) {
      return '';
    }
    return phone.replace(/^(\d{4})(\d{3})(\d{3})$/, '$1 $2 $3');
  }


  onRowEditCancel(employee: UserDetailsDto, index: number) {
    this.employees[index] = this.clonedEmployees[employee.id!];
    delete this.clonedEmployees[employee.id!];
  }

  updateEmployee(userDetails: UserDetailsDto): void {
    console.log("Updating employee:", userDetails);
    this.userCrudService.modifyUser({body: userDetails}).subscribe({
      next: () => {
        this.showAllEmployees();
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Employee edited successfully',
          key: 'save-edit-employee',
          life: 2500
        });

      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: error.error.businessErrorDescription,
          detail: 'Failed to update employee!',
          key: 'cancel-edit-employee',
          life: 2500
        });
        this.showAllEmployees();
      }
    });
  }

  addEmployee(form: NgForm): void {
    if (this.newEmployee.signingDate) {
      const formattedDate = this.datePipe.transform(this.newEmployee.signingDate, 'yyyy-MM-dd');
      this.newEmployee.signingDate = formattedDate ? formattedDate : undefined; // Ensure signingDate is either string or undefined
    }
    console.log(this.newEmployee);

    if (this.newEmployee.firstName && this.newEmployee.lastName && this.newEmployee.email && this.newEmployee.passwordHash && this.newEmployee.signingDate) {
      this.userCrudService.register({body: this.newEmployee}).subscribe({
        next: () => {
          this.showAllEmployees();
          this.showDialog = false;
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Employee added successfully!', key: 'save-add-employee', life: 2500});
          form.resetForm();
        },

        error: (error) => {
          if (error.error.validationErrors) {
            error.error.validationErrors.forEach((msg: string) => {
              this.messageService.add({
                severity: 'error',
                summary: 'Validation Error',
                detail: msg,
                key: 'cancel-add-employee',
                life: 2500
              });
            });
          } else {
            // Display a single business error
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: error.error.businessErrorDescription,
              key: 'cancel-add-employee',
              life: 2500
            });
          }
        }
      });
    } else {
      this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'Complete the required fields!', key: 'uncompleted-add-employee', life: 2500});
    }
  }

  onCancel(form: NgForm): void {
    form.resetForm(); // Reset the form and clear all validation states
    this.newEmployee = {
      firstName: '',
      lastName: '',
      email: '',
      telefonNumber: '',
      passwordHash: '',
      signingDate: ''
    };
    this.showDialog = false; // Close the dialog
  }



}
