import {Component, OnInit} from '@angular/core';
import {UserRole} from "../../services/enums/UserRole";
import {UserDetailsDto} from "../../services/models/user-details-dto";
import {UserCrudControllerService} from "../../services/services/user-crud-controller.service";

@Component({
  selector: 'app-activity-reports',
  templateUrl: './activity-reports.component.html',
  styleUrl: './activity-reports.component.scss'
})
export class ActivityReportsComponent implements OnInit{
  userDetails: UserDetailsDto | null = null;

  constructor(
    private userCrudControllerService: UserCrudControllerService,
  ) {}

  ngOnInit(): void {
    this.userCrudControllerService.authenticatedUser().subscribe({
      next: (data: UserDetailsDto) => {
        this.userDetails = data;
      },
      error: (err) => {
        console.error('Error fetching user details:', err);
      }
    });
  }
  protected readonly UserRole = UserRole;
}
