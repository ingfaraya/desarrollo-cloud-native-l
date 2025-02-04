export const environment = {
  production: false,
  msalConfig: {
    auth: {
      clientId: 'dfe75459-5397-4740-9a80-d784534fac34',
      authority: 'https://login.microsoftonline.com/119295d5-0da1-4441-9b8b-119441b2d71c',
    },
  },
  apiConfig: {
    scopes: ['User.Read'],
    uri: 'https://graph.microsoft.com/v1.0/me',
  },
};
