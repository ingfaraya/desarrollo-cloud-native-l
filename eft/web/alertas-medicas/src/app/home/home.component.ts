import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MsalService } from '@azure/msal-angular';
import { AppComponent } from '../app.component'; // O la ruta adecuada

@Component({
  standalone: true,
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  constructor(
    private msalService: MsalService,
    private router: Router,
    private appComponent: AppComponent  // Inyectamos AppComponent
  ) {}

  ngOnInit() {
    // Suscribirse a la señal de "MSAL listo"
    this.appComponent.msalReady$.subscribe(() => {
      this.doAutoLogin();
    });
  }

  private doAutoLogin(): void {
    const accounts = this.msalService.instance.getAllAccounts();
    if (accounts.length > 0) {
      console.log('🔹 Sesión activa => redirigir /dashboard');
      this.router.navigate(['/dashboard']);
    } else {
      console.log('No hay sesión => loginRedirect()');
      this.msalService.loginRedirect();
    }
  }

  login() {
    this.doAutoLogin();
  }
}
