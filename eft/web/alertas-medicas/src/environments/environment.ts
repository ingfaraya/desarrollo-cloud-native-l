export const environment = {
  production: false,
  azureB2C: {
    clientId: 'dfe75459-5397-4740-9a80-d784534fac34', 
    authority: 'https://duocpruebaazures1.b2clogin.com/duocpruebaazures1.onmicrosoft.com/B2C_1_sum_2',
    redirectUri: 'http://localhost:4200/',
    postLogoutRedirectUri: 'http://localhost:4200/',
    knownAuthorities: ['duocpruebaazures1.b2clogin.com']
  }
};
