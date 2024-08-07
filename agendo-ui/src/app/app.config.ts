import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { JWT_OPTIONS, JwtHelperService } from '@auth0/angular-jwt';
import { authenticationInterceptor } from './core/auth/authentication.interceptor';
import { appRoutes } from './app.routes';
import { endpointInterceptor } from './core/configuration/endpoint.interceptor';
import { AppConfigService } from './core/configuration/app-config.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(appRoutes),
    provideAnimations(),
    {
      provide: JWT_OPTIONS,
      useValue: JWT_OPTIONS
    },
    AppConfigService,
    {
      provide: APP_INITIALIZER,
      useFactory: (appConfigService: AppConfigService) => () => appConfigService.loadAppConfig(),
      deps: [AppConfigService],
      multi: true
    },
    JwtHelperService,
    provideHttpClient(
      // registering interceptors
      withInterceptors(
        [
          authenticationInterceptor,
          endpointInterceptor
        ]
      )
    )
  ],
};