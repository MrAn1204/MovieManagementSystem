import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { ErrorRespondModel } from '../../shared/model/error-respond.model';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(catchError((error: HttpErrorResponse) => {
    const res = error.error as ErrorRespondModel;
    const message = Object.values(res.messages).join(' ') || error.message || 'Unknown error.';

    switch (res.status) {
      case 401:
        router.navigate(['/login']);
        break;
      case 403:
      case 404:
        router.navigate(['/not-found']);
        break;
      default:
        router.navigate(['/error']);
        break;
    }

    return throwError(() => message);
  }));
};
