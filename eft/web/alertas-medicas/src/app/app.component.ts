// app.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { MsalService, MsalBroadcastService } from '@azure/msal-angular';
import { AuthenticationResult, EventType } from '@azure/msal-browser';
import { Subject } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-root',
  imports: [CommonModule, RouterModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  loggedIn = false;
  msalReady$ = new Subject<void>();  // 🔴 Emite cuando MSAL esté listo

  constructor(
    private authService: MsalService,
    private msalBroadcastService: MsalBroadcastService,
    private router: Router
  ) {}

  async ngOnInit() {
    console.log('🔄 Inicializando MSALInstanceFactory...');
    await this.authService.instance.initialize();

    // Emite que MSAL está listo
    this.msalReady$.next();

    // Manejo callback de redirección
    this.authService.instance.handleRedirectPromise()
      .then((result) => {
        if (result?.account) {
          this.authService.instance.setActiveAccount(result.account);
          this.updateLoginStatus();
        }
      })
      .catch((error) => console.error('❌ Error en autenticación:', error));

    // Escuchar eventos
    this.msalBroadcastService.msalSubject$.subscribe((event) => {
      if (event?.eventType === EventType.LOGIN_SUCCESS) {
        const payload = event.payload as AuthenticationResult;
        console.log('✅ Login Exitoso:', payload.account);
        this.updateLoginStatus();
      }
    });

    this.updateLoginStatus();
  }

  private updateLoginStatus(): void {
    this.loggedIn = this.authService.instance.getAllAccounts().length > 0;
    console.log('Estado de sesión:', this.loggedIn);
    if(this.loggedIn){this.router.navigate(['/dashboard']);}
  }
}
