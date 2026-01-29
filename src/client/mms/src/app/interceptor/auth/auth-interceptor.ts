import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LocalStorageService } from '../../service/storage/local-storage.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const lsService = inject(LocalStorageService);

  const token = lsService.getItem("token");

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  return next(req);
};
