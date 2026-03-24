import { ApplicationConfig, inject, provideAppInitializer, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './interceptor/auth/auth-interceptor';
import { errorInterceptor } from './interceptor/error/error-interceptor';
import { OVERLAY_DEFAULT_CONFIG } from '@angular/cdk/overlay';
import { MessageService } from './service/message.service';
import { ConstraintService } from './service/constraint.service';
import { AuthService } from './service/auth/auth.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), provideClientHydration(withEventReplay()),
    provideHttpClient(withFetch(), withInterceptors([authInterceptor, errorInterceptor])),
    { provide: OVERLAY_DEFAULT_CONFIG, useValue: { usePopover: false } },
    provideAppInitializer(async () => await initializeApp())
  ]
};

async function initializeApp() {
  const authService = inject(AuthService);
  const messageService = inject(MessageService);
  const constraintService = inject(ConstraintService);

  await new Promise<void>((resolve) => {
    authService.checkToken();
    resolve();
  })

  await Promise.all([
    messageService.loadAll(),
    constraintService.loadAll(),
  ]);
}

