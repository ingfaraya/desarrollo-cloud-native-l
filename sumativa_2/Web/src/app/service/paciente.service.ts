import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Paciente } from '../model/paciente';

@Injectable({
  providedIn: 'root'
})
export class PacienteService {

  private apiUrl = '';
    private httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

  constructor(private http: HttpClient) { }

  obtenerTodosLosPacientes():Observable<Paciente[]>{
    return this.http.get<Paciente[]>(this.apiUrl + "pacientes")
  }

  crearPaciente(paciente: Paciente): Observable<Paciente>{
    return this.http.post<Paciente>(this.apiUrl + "pacientes", paciente, this.httpOptions);
  }
}
