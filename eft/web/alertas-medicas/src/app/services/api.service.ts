import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PaginatedResponse } from '../models/alert.model';

@Injectable({
  providedIn: 'root' // Esto asegura que el servicio esté disponible en toda la aplicación
})
export class ApiService {
  private baseUrl = 'http://localhost:8082/api'; // Ajusta según tu backend

  private http = inject(HttpClient);

  // 🔹 Obtener lista de pacientes con paginación
  getPatients(page: number = 0, size: number = 10, sort: string = 'id'): Observable<any> {
    return this.http.get(`${this.baseUrl}/patients?page=${page}&size=${size}&sort=${sort}`);
  }

  // 🔹 Obtener alertas con paginación y ordenamiento
  getAlerts(page: number = 0, size: number = 10, sort: string = 'id'): Observable<PaginatedResponse> {
    return this.http.get<PaginatedResponse>(`${this.baseUrl}/alerts?page=${page}&size=${size}&sort=${sort}`);
  }

  // 🔹 Obtener alertas por paciente
  getAlertsByPatient(patientId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/alerts/patientId/${patientId}`);
  }

  // 🔹 Crear un nuevo paciente
  createPatient(patient: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/patients`, patient);
  }

  // 🔹 Crear una nueva alerta
  createAlert(alert: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/alerts`, alert);
  }
}
