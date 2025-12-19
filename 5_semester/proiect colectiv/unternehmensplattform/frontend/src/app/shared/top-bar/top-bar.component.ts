import { Component, Input, OnInit } from '@angular/core';
import { UserDetailsDto } from "../../services/models/user-details-dto";
import { UserCrudControllerService } from "../../services/services/user-crud-controller.service";
import { Router, NavigationEnd } from "@angular/router";
import { MenuItem } from "primeng/api";
import { UserRole } from "../../services/enums/UserRole";

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit {
  userDetails: UserDetailsDto | null = null;
  tabMenuItems: MenuItem[] = [];
  activeTab: MenuItem | undefined = undefined;

  constructor(
    private userCrudControllerService: UserCrudControllerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userCrudControllerService.authenticatedUser().subscribe({
      next: (data: UserDetailsDto) => {
        this.userDetails = data;
        this.initializeMenuItems();
        this.setActiveTabBasedOnCurrentRoute();
      },
      error: (err) => {
        console.error('Error fetching user details:', err);
      }
    });

    // Listen to route changes and update activeTab
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.setActiveTabBasedOnCurrentRoute();
      }
    });
  }

  initializeMenuItems(): void {
    if (!this.userDetails) return;

    const role = this.userDetails.role;

    if (role === UserRole.Superadmin) {
      this.tabMenuItems = [];
    } else if (role === UserRole.Administrator) {
      this.tabMenuItems = [
        { label: 'Dashboard', icon: 'pi pi-home', routerLink: '/dashboard' },
        { label: 'Leave Requests', icon: 'pi pi-calendar', routerLink: '/leave-requests' },
        { label: 'Activity Reports', icon: 'pi pi-file', routerLink: '/activity-reports' },
      ];
    } else if (role === UserRole.Employee) {
      this.tabMenuItems = [
        { label: 'Dashboard', icon: 'pi pi-home', routerLink: '/dashboard' },
        { label: 'Activity Reports', icon: 'pi pi-file', routerLink: '/activity-reports' },
      ];
    }
  }

  setActiveTabBasedOnCurrentRoute(): void {
    const currentUrl = this.router.url; // Get the current URL
    this.activeTab = this.tabMenuItems.find((tab) => currentUrl.startsWith(tab.routerLink || ''));
  }

  onTabChange(selectedTab: MenuItem): void {
    this.activeTab = selectedTab;
  }

  goToProfile(): void {
    // Navigate to profile page
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
