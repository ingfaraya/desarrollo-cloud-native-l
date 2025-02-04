import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MsalBroadcastService, MsalService } from '@azure/msal-angular';
import {
  AuthenticationResult,
  EventMessage,
  EventType,
  InteractionStatus,
} from '@azure/msal-browser';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: [],
  standalone: true,
  imports: [CommonModule],
})
export class HomeComponent implements OnInit {
  // This flag indicates whether a user is logged in.
  loginDisplay = false;

  constructor(
    private msalService: MsalService,
    private msalBroadcastService: MsalBroadcastService
  ) {}

  ngOnInit(): void {
    // Subscribe to login success events.
    this.msalBroadcastService.msalSubject$
      .pipe(
        filter(
          (event: EventMessage) => event.eventType === EventType.LOGIN_SUCCESS
        )
      )
      .subscribe((event: EventMessage) => {
        console.log('Login successful event:', event);
        const authResult = event.payload as AuthenticationResult;
        // Set the active account using the authentication result.
        this.msalService.instance.setActiveAccount(authResult.account);
      });

    // Monitor the interaction status and update the login display when no interaction is in progress.
    this.msalBroadcastService.inProgress$
      .pipe(
        filter(
          (status: InteractionStatus) => status === InteractionStatus.None
        )
      )
      .subscribe(() => {
        this.updateLoginDisplay();
      });
  }

  /**
   * Updates the login display flag based on whether any accounts are present.
   */
  updateLoginDisplay(): void {
    this.loginDisplay =
      this.msalService.instance.getAllAccounts().length > 0;
  }
}
