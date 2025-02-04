import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink, RouterOutlet } from '@angular/router';
import {
  MsalService,
  MsalModule,
  MsalBroadcastService,
  MSAL_GUARD_CONFIG,
  MsalGuardConfiguration,
} from '@azure/msal-angular';
import {
  AuthenticationResult,
  InteractionStatus,
  PopupRequest,
  RedirectRequest,
  EventMessage,
  EventType,
} from '@azure/msal-browser';
import { Subject } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MsalModule,
    RouterOutlet,
    RouterLink,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
  ],
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'MQ Hospital';
  isIframe = false;
  loginDisplay = false;
  private readonly destroy$ = new Subject<void>();

  constructor(
    @Inject(MSAL_GUARD_CONFIG) private msalGuardConfig: MsalGuardConfiguration,
    private authService: MsalService,
    private msalBroadcastService: MsalBroadcastService
  ) {}

  ngOnInit(): void {
    // Handle redirect responses if any
    this.authService.handleRedirectObservable().subscribe();

    // Check if the app is running in an iframe (useful for token acquisition scenarios)
    this.isIframe = window !== window.parent && !window.opener;

    // Set the initial login display status
    this.updateLoginDisplay();

    // Enable account storage events to capture account changes across tabs/windows
    this.authService.instance.enableAccountStorageEvents();

    // Listen for account addition or removal events
    this.msalBroadcastService.msalSubject$
      .pipe(
        filter(
          (msg: EventMessage) =>
            msg.eventType === EventType.ACCOUNT_ADDED ||
            msg.eventType === EventType.ACCOUNT_REMOVED
        )
      )
      .subscribe(() => {
        if (this.authService.instance.getAllAccounts().length === 0) {
          // No accounts found, redirect to the home page
          window.location.pathname = '/';
        } else {
          this.updateLoginDisplay();
        }
      });

    // Monitor the interaction status and update the active account when no interaction is in progress
    this.msalBroadcastService.inProgress$
      .pipe(
        filter((status: InteractionStatus) => status === InteractionStatus.None),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.updateLoginDisplay();
        this.setActiveAccountIfNeeded();
      });
  }

  /**
   * Updates the login display flag based on the number of signed-in accounts.
   */
  updateLoginDisplay(): void {
    this.loginDisplay = this.authService.instance.getAllAccounts().length > 0;
  }

  /**
   * Sets the first available account as active if none is currently active.
   * Note: This is a basic implementation. More advanced logic may be required for complex scenarios.
   */
  setActiveAccountIfNeeded(): void {
    const activeAccount = this.authService.instance.getActiveAccount();
    if (!activeAccount && this.authService.instance.getAllAccounts().length > 0) {
      const accounts = this.authService.instance.getAllAccounts();
      this.authService.instance.setActiveAccount(accounts[0]);
    }
  }

  /**
   * Initiates a login redirect using the provided MSAL guard configuration if available.
   */
  loginRedirect(): void {
    if (this.msalGuardConfig.authRequest) {
      this.authService.loginRedirect({
        ...this.msalGuardConfig.authRequest,
      } as RedirectRequest);
    } else {
      this.authService.loginRedirect();
    }
  }

  /**
   * Initiates a login popup. On success, sets the active account and silently acquires an access token.
   */
  loginPopup(): void {
    if (this.msalGuardConfig.authRequest) {
      this.authService
        .loginPopup({ ...this.msalGuardConfig.authRequest } as PopupRequest)
        .subscribe((response: AuthenticationResult) => {
          this.authService.instance.setActiveAccount(response.account);
  
          // Acquire and store the access token
          this.authService.acquireTokenSilent({ scopes: ['User.Read'] }).subscribe({
            next: (tokenResponse) => {
              localStorage.setItem('jwt', tokenResponse.idToken);
              console.log('ID token stored in localStorage:', tokenResponse.idToken);
            },
            error: (error) => {
              console.error('Error acquiring access token:', error);
            },
          });
        });
    } else {
      this.authService
        .loginPopup()
        .subscribe((response: AuthenticationResult) => {
          this.authService.instance.setActiveAccount(response.account);
  
          // Acquire and store the access token
          this.authService.acquireTokenSilent({ scopes: ['User.Read'] }).subscribe({
            next: (tokenResponse) => {
              localStorage.setItem('jwt', tokenResponse.accessToken);
              console.log('Access token stored in localStorage:', tokenResponse.accessToken);
            },
            error: (error) => {
              console.error('Error acquiring access token:', error);
            },
          });
        });
    }
  }

  /**
   * Logs out the current user. If the popup parameter is true, uses a popup for logout.
   * Otherwise, it uses a redirect.
   * @param popup - Determines whether to use a popup or a redirect for logout.
   */
  logout(popup?: boolean): void {
    if (popup) {
      this.authService.logoutPopup({ mainWindowRedirectUri: '/' });
    } else {
      this.authService.logoutRedirect();
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
