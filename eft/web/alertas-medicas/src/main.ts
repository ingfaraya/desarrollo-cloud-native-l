// src/main.ts
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes'; // ✅ Importar tus rutas
import { MsalService, MsalRedirectComponent, MsalGuard, MsalBroadcastService } from '@azure/msal-angular';
import { MSAL_INSTANCE, MSAL_GUARD_CONFIG, MSAL_INTERCEPTOR_CONFIG } from '@azure/msal-angular';
import { MSALInstanceFactory, MSALGuardConfigFactory, MSALInterceptorConfigFactory } from './msal.config';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),             // ✅ Usa `routes` en lugar de `[]`
    provideHttpClient(),
    { provide: MSAL_INSTANCE, useFactory: MSALInstanceFactory },
    { provide: MSAL_GUARD_CONFIG, useFactory: MSALGuardConfigFactory },
    { provide: MSAL_INTERCEPTOR_CONFIG, useFactory: MSALInterceptorConfigFactory },
    MsalService,
    MsalRedirectComponent,
    MsalGuard,
    MsalBroadcastService
  ]
}).catch(err => console.error(err));
