import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CommonModule } from '@angular/common';
import { MsalService } from '@azure/msal-angular';
import { PacienteService } from '../service/paciente.service';
import { Paciente } from '../model/paciente';

type ProfileType = {
  name?: string;
  preferred_username?: string;
};

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: [],
  standalone: true,
  imports: [CommonModule],
})
export class ProfileComponent implements OnInit {
  profile?: ProfileType;
  pacientes: Paciente[] = [];

  constructor(
    private authService: MsalService,
    private http: HttpClient,
    private pacienteService: PacienteService
  ) {}

  ngOnInit(): void {
    // Initialize profile data when the component loads.
    this.loadProfile(environment.apiConfig.uri);
  }

  /**
   * Retrieves and decodes the JWT from localStorage to extract the user profile.
   * @param url - API URL (currently not used in the method but kept for consistency).
   */
  loadProfile(url: string): void {
    // Retrieve the token from localStorage.
    const token = localStorage.getItem('jwt');

    if (token) {
      try {
        // Decode the token using our custom decode function.
        const decodedToken = this.decodeToken(token);

        // Extract desired profile properties.
        this.profile = {
          name: decodedToken.name,
          preferred_username: decodedToken.preferred_username,
        };

        console.log('Decoded profile:', this.profile);
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    } else {
      console.error('No token found in localStorage.');
    }
  }

  /**
   * Decodes a JWT token (base64 URL encoded) and returns its payload as a JSON object.
   * @param token - The JWT token to decode.
   * @returns The decoded payload object or null if decoding fails.
   */
  private decodeToken(token: string): any {
    try {
      // The JWT structure is header.payload.signature; we need the payload.
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map(c => '%' + c.charCodeAt(0).toString(16).padStart(2, '0'))
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }

  /**
   * Calls the backend service to fetch all patients and stores them in the component state.
   */
  callBackend(): void {
    this.pacienteService.obtenerTodosLosPacientes().subscribe(
      (response: Paciente[]) => {
        this.pacientes = response;
      },
      (error) => {
        console.error('Error fetching patients:', error);
      }
    );
  }

  // Uncomment and adjust the method below if you need to display the backend response as a JSON string.
  // showBackendResponse(): string {
  //   return JSON.stringify(this.responseBackend);
  // }
}
