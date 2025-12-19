import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule, HttpClient } from '@angular/common/http'
import { ButtonModule } from 'primeng/button';
import { PanelModule } from 'primeng/panel';
import { InputTextModule } from 'primeng/inputtext';
import { LoginComponent } from './pages/login/login.component';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {HttpTokenInterceptor} from "./services/interceptor/http-token/http-token.interceptor";
import { EmployeeDashboardComponent } from './pages/dashboard/employee-dashboard/employee-dashboard.component';
import { AdministratorDashboardComponent } from './pages/dashboard/administrator-dashboard/administrator-dashboard.component';
import {MatCheckbox} from "@angular/material/checkbox";
import {MatInput} from "@angular/material/input";
import {MatDialogContent} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";

import {OverlayPanelModule} from "primeng/overlaypanel";
import {AvatarModule} from "primeng/avatar";
import {TableModule} from "primeng/table";
import {Ripple} from "primeng/ripple";
import {InputSwitchModule} from "primeng/inputswitch";
import {DialogModule} from "primeng/dialog";
import {CalendarModule} from "primeng/calendar";
import {ToastModule} from "primeng/toast";
import {SuperadminDashboardComponent} from "./pages/dashboard/superadmin-dashboard/superadmin-dashboard.component";
import { LeaveRequestsAdminComponent } from './pages/leave-requests-admin/leave-requests-admin.component';
import { TopBarComponent } from './shared/top-bar/top-bar.component';
import {TabMenuModule} from "primeng/tabmenu";
import { LayoutComponent } from './shared/layout/layout.component';
import { ActivityReportsAdminComponent } from "./pages/activity-reports/activity-reports-admin/activity-reports-admin.component";
import { ActivityReportsEmployeeComponent } from './pages/activity-reports/activity-reports-employee/activity-reports-employee.component';
import { ActivityReportsComponent } from './pages/activity-reports/activity-reports.component';
import {TagModule} from "primeng/tag";
import {IconFieldModule} from "primeng/iconfield";
import {InputIconModule} from "primeng/inputicon";
import {MultiSelectModule} from "primeng/multiselect";
import {DropdownModule} from "primeng/dropdown";
import { FloatLabelModule } from 'primeng/floatlabel';

import {FullCalendarModule} from "@fullcalendar/angular";
import {InputTextareaModule} from "primeng/inputtextarea";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    SuperadminDashboardComponent,
    // SuperadminDashboardComponent2,
    EmployeeDashboardComponent,
    AdministratorDashboardComponent,
    // AddCompanyComponent,
    // AddAdminComponent,

    LeaveRequestsAdminComponent,
    TopBarComponent,
    LayoutComponent,
    ActivityReportsAdminComponent,
    ActivityReportsEmployeeComponent,
    ActivityReportsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ButtonModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule, ReactiveFormsModule,
    PanelModule,
    InputTextModule,
    MessagesModule,
    MessageModule,
    MatCheckbox,
    MatInput,
    MatDialogContent,
    MatButton,
    OverlayPanelModule,
    AvatarModule,
    DialogModule,
    TableModule, ToastModule, CalendarModule, InputSwitchModule, Ripple, MultiSelectModule, DropdownModule,
    TabMenuModule, TagModule, IconFieldModule, InputIconModule, FullCalendarModule, FloatLabelModule, InputTextareaModule
  ],
  providers: [
    HttpClient,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
