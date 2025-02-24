// msal.config.ts
import { PublicClientApplication, LogLevel, InteractionType } from '@azure/msal-browser'; 
import { MsalGuardConfiguration, MsalInterceptorConfiguration } from '@azure/msal-angular';
import { environment } from './environments/environment'; // Asegúrate de que define azureB2C

export function MSALInstanceFactory(): PublicClientApplication {
  console.log("🔄 Inicializando MSALInstanceFactory...");
  return new PublicClientApplication({
    auth: {
      clientId: environment.azureB2C.clientId,
      authority: environment.azureB2C.authority,
      redirectUri: environment.azureB2C.redirectUri,
      postLogoutRedirectUri: environment.azureB2C.postLogoutRedirectUri,
      knownAuthorities: environment.azureB2C.knownAuthorities
    },
    cache: {
      cacheLocation: 'localStorage',
      storeAuthStateInCookie: true
    },
    system: {
      loggerOptions: {
        loggerCallback: (level: LogLevel, message: string) => {
          console.log(message);
        },
        logLevel: LogLevel.Info,
        piiLoggingEnabled: false
      }
      // Si tu versión de msal-browser lo soporta, puedes hacer:
      // navigateToLoginRequestUrl: false
    }
  });
}

export function MSALGuardConfigFactory(): MsalGuardConfiguration {
  return {
    interactionType: InteractionType.Redirect,
    authRequest: {
      scopes: ['openid', 'profile', 'email']
    }
  };
}

export function MSALInterceptorConfigFactory(): MsalInterceptorConfiguration {
  return {
    interactionType: InteractionType.Redirect,
    protectedResourceMap: new Map([
      ['https://mi-api.com/api', ['openid', 'profile', 'email']]
    ])
  };
}
