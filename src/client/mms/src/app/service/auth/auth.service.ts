import { computed, Injectable, signal } from '@angular/core';
import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  currentUser = signal<any>(null);
  isAuthenticated = computed<boolean>(() => this.currentUser() !== null);

  constructor() {
    const token = localStorage.getItem("token");

    if (token) {
      this.currentUser.set(this.parseJwt(token));
      console.log(this.currentUser());
    }
  }

  parseJwt(token: string) {
    if (!token) {
      return;
    }

    return jwtDecode(token);
  }
}
