import { environment } from 'src/environments/environment';
import { IPublicClientApplication, PublicClientApplication } from '@azure/msal-browser';

export function MSALInstanceFactory(): IPublicClientApplication {
  return new PublicClientApplication({
    auth: {
      clientId: environment.msalConfig.auth.clientId, 
      authority: environment.msalConfig.auth.authority, 
      redirectUri: 'http://localhost:4200', 
    },
  });
}
