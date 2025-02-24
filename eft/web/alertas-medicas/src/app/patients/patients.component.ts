import { Component, OnInit, inject } from '@angular/core';
import { ApiService } from '../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {
  patients: any[] = [];

  private apiService = inject(ApiService);

  ngOnInit() {
    this.apiService.getPatients().subscribe(response => {
      this.patients = response.content;
    });
  }
}
