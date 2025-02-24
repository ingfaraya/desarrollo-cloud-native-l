import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../services/api.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-alerts',
  templateUrl: './alerts.component.html',
  imports: [CommonModule, HttpClientModule] // 🔹 Asegúrate de incluir CommonModule
})
export class AlertsComponent {
  private apiService = inject(ApiService);
  
  alerts: any[] = [];
  page: number = 0;
  size: number = 10;
  totalPages: number = 1;

  constructor() {
    this.loadAlerts();
  }

  loadAlerts() {
    this.apiService.getAlerts(this.page, this.size).subscribe((response: any) => {
      this.alerts = response.content;
      this.totalPages = response.totalPages;
    }, error => {
      console.error("Error al cargar alertas:", error);
    });
  }

  prevPage() {
    if (this.page > 0) {
      this.page--;
      this.loadAlerts();
    }
  }

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadAlerts();
    }
  }
}
