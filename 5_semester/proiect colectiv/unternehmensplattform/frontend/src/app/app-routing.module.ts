import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
// import { LeaveRequestsComponent } from './pages/leave-requests/leave-requests.component';
// import { EmployeeActivityReportComponent } from './pages/activity-reports/activity-reports.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { authGuard } from './services/guard/auth.guard';
import { loginGuard } from './services/guard/login.guard';
import {LeaveRequestsAdminComponent} from "./pages/leave-requests-admin/leave-requests-admin.component";
import {ActivityReportsComponent} from "./pages/activity-reports/activity-reports.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [loginGuard],
  },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'leave-requests', component: LeaveRequestsAdminComponent },
      { path: 'activity-reports', component: ActivityReportsComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
