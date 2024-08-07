import { HttpInterceptorFn } from '@angular/common/http';
import { AppConfigService } from './app-config.service';
import { inject } from '@angular/core';

export const endpointInterceptor: HttpInterceptorFn = (req, next) => {
  const appConfigService = inject(AppConfigService);
  if (req.url.length === 0) {
    return next(req);
  }
  
  // If first character in URL is not a slash, we add a slash
  const addSlash = req.url.at(0) !== '/' ? '/' : '';

  // Construct full endpoint
  const urlWithApiBaseUrl = appConfigService.apiBaseUrl + addSlash +  req.url;

  // Add authentication header to request
  const fullEndpointReq = req.clone({
    url: urlWithApiBaseUrl
  });

  return next(fullEndpointReq);
};
