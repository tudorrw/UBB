import {Component, OnInit, ViewChild} from '@angular/core';
import {MessageService} from "primeng/api";
import {DatePipe} from "@angular/common";
import {UserCrudControllerService} from "../../../services/services/user-crud-controller.service";
import {CompanyControllerService} from "../../../services/services/company-controller.service";
import {UserDetailsDto} from "../../../services/models/user-details-dto";
import {CompanyDto} from "../../../services/models/company-dto";
import {TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {CompanyDetailsDto} from "../../../services/models/company-details-dto";
import {RegistrationRequest} from "../../../services/models/registration-request";
import {NgForm} from "@angular/forms";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {reportUnhandledError} from "rxjs/internal/util/reportUnhandledError";

@Component({
  selector: 'app-superadmin-dashboard',
  templateUrl: './superadmin-dashboard.component.html',
  styleUrl: './superadmin-dashboard.component.scss',
  providers: [MessageService, DatePipe]

})
export class SuperadminDashboardComponent implements OnInit {
  @ViewChild('companyForm') companyForm!: NgForm; // Access the form
  @ViewChild('adminForm') adminForm!: NgForm; // Access the form

  companies: CompanyDetailsDto[] = [];
  expandedRows: { [key: number]: boolean } = {}; // Declare expandedRows with numeric keys
  showCompanyDialog = false;
  newCompany: CompanyDto = { name: '', address: '', telefonNumber: '' };
  newAdmins: RegistrationRequest[] = [];
  newAdmin: RegistrationRequest = { firstName: '', lastName: '', email: '', passwordHash: '', telefonNumber: '' };
  showAdminDialog = false;
  showAdminDialogWithoutComp = false;
  selectedCompanyId: number | null = null;


  constructor(
    private companyService: CompanyControllerService,
    private userCrudControllerService :UserCrudControllerService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.fetchCompanyDetails();
  }

  fetchCompanyDetails(): void {
    this.companyService.getAllCompanies().subscribe({
      next: (data: CompanyDetailsDto[]) => {
        this.companies = data;
      },
      error: (error) => {
        console.error('Failed to fetch company details:', error);
      }
    });
  }

  expandAll(): void {
    this.expandedRows = this.companies.reduce((acc: { [key: number]: boolean }, p) => {
      if (p.companyDTO && p.companyDTO.companyId !== undefined) {
        acc[p.companyDTO.companyId] = true; // Safely assign if companyId is defined
      }
      return acc;
    }, {} as { [key: number]: boolean }); // Initialize as an object with numeric keys
  }


  collapseAll(): void {
    this.expandedRows = {}; // Collapse all rows
  }

  onRowExpand(event: TableRowExpandEvent) {
    this.messageService.add({ severity: 'info', summary: 'Product Expanded', detail: event.data.companyDTO.name, life: 3000 });
  }

  onRowCollapse(event: TableRowCollapseEvent) {
    this.messageService.add({ severity: 'success', summary: 'Product Collapsed', detail: event.data.companyDTO.name, life: 3000 });
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

  addCompany(form: NgForm): void {
    const companyData = {
      body: { // Wrap the object in a `body` key
        companyDTO: this.newCompany,
        adminRegistrations: this.newAdmins
      }
    };
    if(this.newCompany.name && this.newCompany.address && this.newCompany.telefonNumber) {

      this.companyService.createCompany(companyData).subscribe({
        next: () => {
          this.fetchCompanyDetails();
          this.showCompanyDialog = false;
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Company added successfully!', key: 'save-add-company', life: 2500});
          form.resetForm();
          this.newAdmins = [];
        },
        error: (error) => {
          console.error('Failed to add company:', error);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error.businessErrorDescription,
            key: 'cancel-add-company',
            life: 2500
          });
        }
      });
    } else {
      this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'Complete the required fields!', key: 'uncompleted', life: 2500});
    }
  }

  addAdmin(form: NgForm): void {
    if (this.newAdmin.firstName && this.newAdmin.lastName && this.newAdmin.email && this.newAdmin.passwordHash) {
      let emailExists = false;
      let phoneExists = false;

      const checkEmail = new Promise<void>((resolve, reject) => {
        this.userCrudControllerService.checkEmailExists({ email: this.newAdmin.email }).subscribe({
          next: () => resolve(),
          error: (error) => {
            emailExists = true;
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: error.error.businessErrorDescription,
              key: 'cancel-add-admin',
              life: 2500
            });
            reject(); // Reject if email exists
          }
        });
      });

      const checkPhone = new Promise<void>((resolve, reject) => {
        if (this.newAdmin.telefonNumber) {
          this.userCrudControllerService.checkPhoneNumberExists({ phoneNumber: this.newAdmin.telefonNumber }).subscribe({
            next: () => resolve(),
            error: (error) => {
              phoneExists = true;
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: error.error.businessErrorDescription,
                key: 'cancel-add-admin',
                life: 2500
              });
              reject(); // Reject if phone number exists
            }
          });
        } else {
          resolve(); // No phone number to check, resolve immediately
        }
      });

      // Ensure both checks complete before proceeding
      Promise.all([checkEmail, checkPhone]).then(() => {
        // Proceed to add the admin if no errors
        if (!emailExists && !phoneExists) {
          this.newAdmins.push(this.newAdmin);
          this.newAdmin = { firstName: '', lastName: '', email: '', passwordHash: '', telefonNumber: '' };
          this.showAdminDialog = false;
        }
      }).catch(() => {
        // If either check fails, do nothing and error messages will have been shown
      });

    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Validation Error',
        detail: 'Complete the required fields!',
        key: 'uncompleted',
        life: 2500
      });
    }
  }


  openAdminDialogForCompany(companyId: number): void {
    this.selectedCompanyId = companyId;
    this.showAdminDialogWithoutComp = true;
  }


  addAdminWithoutComp(form: NgForm): void {
    console.log(this.selectedCompanyId);
    if (this.selectedCompanyId === null) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'No company selected!',
        key: 'uncompleted',
        life: 2500
      });
      return;
    }

    if (this.newAdmin.firstName && this.newAdmin.lastName && this.newAdmin.email && this.newAdmin.passwordHash) {
      this.userCrudControllerService.register1({
        companyId: this.selectedCompanyId,
        body: this.newAdmin,
      }).subscribe({
        next: () => {
          this.fetchCompanyDetails();
          this.showAdminDialogWithoutComp = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Admin added successfully!',
            key: 'save-add-admin',
            life: 2500
          });
          form.resetForm();
        },
        error: (error) => {
          if (error.error.validationErrors) {
            error.error.validationErrors.forEach((msg: string) => {
              this.messageService.add({
                severity: 'error',
                summary: 'Validation Error',
                detail: msg,
                key: 'cancel-add-admin',
                life: 2500
              });
            });
          } else {
            // Display a single business error
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: error.error.businessErrorDescription,
              key: 'cancel-add-admin',
              life: 2500
            });
          }
        }
      });
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Validation Error',
        detail: 'Complete the required fields!',
        key: 'uncompleted',
        life: 2500
      });
    }
  }


  deleteAdmin(index: number): void {
    this.newAdmins.splice(index, 1); // Remove the admin at the specified index
    this.messageService.add({
      severity: 'success',
      summary: 'Admin Removed',
      detail: 'The admin has been removed successfully.',
      key: 'remove-admin'
    });
  }


  cancelCompany(form: NgForm): void {
    this.showCompanyDialog = false;
    form.resetForm();
  }

  cancelAdmin(form: NgForm): void {
    this.showAdminDialog = false;
    form.resetForm();
  }

  cancelAdminWithoutCompany(form: NgForm): void {
    this.showAdminDialogWithoutComp = false;
    form.resetForm();
  }



}

