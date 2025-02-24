import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PatientsComponent } from './patients/patients.component';
import { AlertsComponent } from './alerts/alerts.component';
import { MsalGuard } from '@azure/msal-angular';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [MsalGuard] },
  { path: 'patients', component: PatientsComponent, canActivate: [MsalGuard] },
  { path: 'alerts', component: AlertsComponent, canActivate: [MsalGuard] },
  { path: '**', redirectTo: '' }
];
