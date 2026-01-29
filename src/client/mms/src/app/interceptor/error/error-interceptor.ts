import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(catchError((error: HttpErrorResponse) => {
    switch (error.status) {
      case 403:
        console.error("Authentication credentials are invalid or expired. Redirecting to login.");
        router.navigate(['/login']);
        break;
      case 404:
        console.error("Resource not found - The requested resource could not be found.");
        break;
      default:
        console.error("An unexpected error occurred:", error.message);
        break;
    }

    return throwError(() => error);
  }));
};
