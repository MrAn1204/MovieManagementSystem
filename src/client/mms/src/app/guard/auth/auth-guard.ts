import { inject, PLATFORM_ID } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';
import { isPlatformBrowser } from '@angular/common';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const authService = inject(AuthService);
  const platformId = inject(PLATFORM_ID);

  if (isPlatformBrowser(platformId) && !authService.isAuthenticated()) {
    return router.parseUrl('/login');
  }

  return true;
};
