import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';

export const passwordResetGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  const token = route.queryParamMap.get('token');

  if (token) {
    authService.validateResetToken(token).subscribe((res) => {
      if (!res) {
        router.navigateByUrl('/forgot-password');
      }
    });
  } else {
    router.navigateByUrl('/forgot-password');
  }

  return true;
};
