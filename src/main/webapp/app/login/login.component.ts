import { AfterViewInit, Component, ElementRef, OnInit, inject, signal, viewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import SharedModule from 'app/shared/shared.module';
import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import {CommonModule} from "@angular/common";
import { StateStorageService } from 'app/core/auth/state-storage.service';

declare const google: any;
declare const FB: any;

@Component({
  selector: 'jhi-login',
  standalone: true,
  imports: [SharedModule, CommonModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export default class LoginComponent implements OnInit, AfterViewInit {
  username = viewChild.required<ElementRef>('username');

  authenticationError = signal(false);

  loginForm = new FormGroup({
    username: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    rememberMe: new FormControl(false, { nonNullable: true, validators: [Validators.required] }),
  });

  private readonly accountService = inject(AccountService);
  private readonly loginService = inject(LoginService);
  private readonly router = inject(Router);
  private readonly http = inject(HttpClient);
  private readonly stateStorageService = inject(StateStorageService);
  // constructor(private authService: SocialAuthService, private http: HttpClient) {}

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.username().nativeElement.focus();

    const googleDiv = document.getElementById('g_id_signin');
    if (google && google.accounts && google.accounts.id && googleDiv) {
      google.accounts.id.initialize({
        client_id: '108387489501-071tvlasb3uk19v7gs3m61qjga3l70v6.apps.googleusercontent.com',
        callback: (response: any) => this.handleCredentialResponse(response),
      });

      google.accounts.id.renderButton(googleDiv, {
        theme: 'outline',
        size: 'large',
      });
    }
  }

  handleCredentialResponse(response: any): void {
    const token = response.credential;
    this.http.post('http://localhost:8080/api/authenticate-google', { token }).subscribe({
      next: (res: any) => {
        this.stateStorageService.storeAuthenticationToken(res.id_token, false);
        this.accountService.identity(true).subscribe(() => {
          this.router.navigate(['']);
        });
      },
      error: err => {
        console.error('Google login error:', err);
      },
    });
  }

  loginWithFacebook(): void {
    FB.login((response: any) => {
      if (response.authResponse) {
        const accessToken = response.authResponse.accessToken;

        this.http.post('http://localhost:8080/api/authenticate-facebook', {
          access_token: accessToken
        }).subscribe((res: any) => {
          this.stateStorageService.storeAuthenticationToken(res.id_token, false);
          this.accountService.identity(true).subscribe(() => {
            this.router.navigate(['']);
          });
        });
      } else {
        console.error('Người dùng từ chối đăng nhập');
      }
    }, { scope: 'email,public_profile' });
  }

  // ngAfterViewInit(): void {
  //   this.username().nativeElement.focus();
  // }

  login(): void {
    this.loginService.login(this.loginForm.getRawValue()).subscribe({
      next: () => {
        this.authenticationError.set(false);
        if (!this.router.getCurrentNavigation()) {
          // There were no routing during login (eg from navigationToStoredUrl)
          this.router.navigate(['']);
        }
      },
      error: () => this.authenticationError.set(true),
    });
  }
}
