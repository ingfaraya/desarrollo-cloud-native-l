import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MsalService } from '@azure/msal-angular';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {
  private router = inject(Router); // Inyectamos el Router

  constructor(private authService: MsalService) {}

  goToPatients() {
    this.router.navigate(['/patients']);
  }

  goToAlerts() {
    this.router.navigate(['/alerts']);
  }

  goToSettings() {
    this.router.navigate(['/settings']);
  }

  logout() {
    console.log('🔴 Cerrando sesión desde Dashboard...');
    this.authService.logoutRedirect();
  }
}
