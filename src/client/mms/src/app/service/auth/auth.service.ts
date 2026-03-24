import { computed, Injectable, signal } from '@angular/core';
import { jwtDecode } from "jwt-decode";
import { LoginRequest } from '../../model/auth/login-request';
import { HttpClient } from '@angular/common/http';
import { LocalStorageService } from '../storage/local-storage.service';
import { LoginResponse } from '../../model/auth/login-response';
import { UserInfo } from '../../model/auth/user-info';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { RegisterRequest } from '../../model/auth/register-request';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080/auth';

  private readonly currentUser = signal<UserInfo | null>(null);
  isAuthenticated = computed<boolean>(() => this.currentUser() !== null);

  constructor(private readonly http: HttpClient, private readonly lsService: LocalStorageService, private readonly router: Router) {
    this.checkToken();
  }

  checkToken() {
    const token = this.lsService.getItem("token");

    if (!token) {
      return;
    }

    const userInfo = this.parseJwt(token);

    if (userInfo?.exp && Date.now() > userInfo.exp * 1000) {
      this.lsService.removeItem("token");
    } else {
      this.currentUser.set(userInfo);
    }
  }

  parseJwt(token: string): UserInfo | null {
    if (!token) {
      return null;
    }

    return jwtDecode(token);
  }

  getFullname(): string {
    return this.currentUser()?.fullname || '';
  }

  getEmail(): string {
    return this.currentUser()?.email || '';
  }

  includeRoles(roles: string[]): boolean {
    if (roles.length == 0) {
      return false;
    }

    const userRoles = this.currentUser()?.roles || [];
    return roles.some(role => userRoles.includes(role));
  }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, request).pipe(tap((res) => {
      this.lsService.setItem("token", res.token);
      this.currentUser.set(this.parseJwt(res.token));
      this.router.navigate(['/']);
    }));
  }

  logout(): void {
    this.lsService.removeItem("token");
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  register(request: RegisterRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/register`, request).pipe(tap(() => {
      this.router.navigate(['/login']);
    }));
  }
}
