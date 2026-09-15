import { computed, inject, Injectable, PLATFORM_ID, signal } from '@angular/core';
import { LoginRequest } from '../../model/auth/login-request';
import { HttpClient } from '@angular/common/http';
import { UserInfo } from '../../model/auth/user-info';
import { Router } from '@angular/router';
import { firstValueFrom, Observable, tap } from 'rxjs';
import { RegisterRequest } from '../../model/auth/register-request';
import { RoleName } from '../../shared/model/role-config.model';
import { PasswordResetFormModel } from '../../model/form/password-reset-form.model';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080/auth';

  private readonly currentUser = signal<UserInfo | null>(null);
  isAuthenticated = computed<boolean>(() => this.currentUser() !== null);

  private readonly platformId = inject(PLATFORM_ID);

  constructor(private readonly http: HttpClient, private readonly router: Router) {
  }

  getCurrentUser() {
    if (!isPlatformBrowser(this.platformId)) {
      return Promise.resolve(null);
    }

    return firstValueFrom(this.http.get<UserInfo>(`${this.baseUrl}/current-user`)
      .pipe(
        tap({
          next: (res) => this.currentUser.set(res),
          error: () => this.currentUser.set(null)
        }),
      ));
  }

  getId(): string {
    return this.currentUser()?.id || '';
  }

  getFullname(): string {
    return this.currentUser()?.fullname || '';
  }

  getEmail(): string {
    return this.currentUser()?.email || '';
  }

  includeRoles(roles: RoleName[]): boolean {
    if (roles.length == 0) {
      return false;
    }

    const userRoles = this.currentUser()?.roles || [];
    return roles.some(role => userRoles.includes(role));
  }

  login(request: LoginRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(`${this.baseUrl}/login`, request).pipe(tap((res) => {
      this.currentUser.set(res);
      this.router.navigate(['/']);
    }));
  }

  logout(): void {
    this.http.post(`${this.baseUrl}/logout`, {}).subscribe(() => {
      this.currentUser.set(null);
      this.router.navigate(['/login']);
    });
  }

  register(request: RegisterRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/register`, request).pipe(tap(() => {
      this.router.navigate(['/login']);
    }));
  }

  forgotPassword(email: string): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/forgot-password`, { email: email });
  }

  validateResetToken(token: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/reset-password`, {
      params: {
        token: token
      }
    });
  }

  resetPassword(request: PasswordResetFormModel): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/reset-password`, request)
      .pipe(tap(() => this.logout()));
  }
}
