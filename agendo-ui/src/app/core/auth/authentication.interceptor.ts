import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from './auth.service';

export const authenticationInterceptor: HttpInterceptorFn = (req, next) => {
  
  const authToken = sessionStorage.getItem(AuthService.AUTH_TOKEN_STORAGE_NAME);

  // If no authToken exists
  if (!authToken) {
    return next(req);
  }

  // Add authentication header to request
  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${authToken}`
    }
  });
  return next(authReq);
};
