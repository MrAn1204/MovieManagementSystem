import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { ErrorRespondModel } from '../../shared/model/error-respond.model';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(catchError((error: HttpErrorResponse) => {
    const res = error.error as ErrorRespondModel;

    switch (res.status) {
      case 400:
        break;
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

    return throwError(() => res);
  }));
};
