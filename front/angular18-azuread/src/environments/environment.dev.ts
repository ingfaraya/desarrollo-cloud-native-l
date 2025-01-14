export const environment = {
  production: false,
  msalConfig: {
    auth: {
      clientId: '0b5db7ec-8452-4f45-b6eb-4191c3a02bbb',
      authority: 'https://login.microsoftonline.com/common',
    },
  },
  apiConfig: {
    scopes: ['User.Read'],
    uri: 'https://graph.microsoft.com/v1.0/me',
  },
};

