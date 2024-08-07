import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { JwtTokenDTO, LoginRequestDTO, RefreshTokenDTO } from '../model/auth-dtos';
import { catchError, firstValueFrom, map, of } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public static AUTH_TOKEN_STORAGE_NAME: string = 'AccessToken';
  public static REFRESH_TOKEN_STORAGE_NAME: string = 'RefreshToken';

  refreshTokenTimeout: ReturnType<typeof setTimeout> | undefined;

  constructor(
    private jwtHelper: JwtHelperService,
    private httpClient: HttpClient,
    private router: Router
  ) {
  }

  public isAuthenticated(): boolean {
    const token = sessionStorage.getItem(AuthService.AUTH_TOKEN_STORAGE_NAME);
    return !this.jwtHelper.isTokenExpired(token);
  }

  public login(username?: string, password?: string): Promise<boolean | number> {
    return firstValueFrom(
      this.httpClient.post<JwtTokenDTO>('api/v1/auth/login', { username, password } as LoginRequestDTO)
        .pipe(
          map((jwtToken: JwtTokenDTO) => {
            const loginResult = this.handleReceivedToken(jwtToken)
            this.router.navigate(['']);
            return loginResult;
          }),
          catchError((err: HttpErrorResponse) => {
            console.log('An error occurred for the request', err);
            return of(err.status);
          })
        )
    );
  }

  private handleReceivedToken(jwtToken: JwtTokenDTO): boolean {
    if (this.jwtHelper.isTokenExpired(jwtToken.accessToken)) {
      return false;
    }

    sessionStorage.setItem(AuthService.AUTH_TOKEN_STORAGE_NAME, jwtToken.accessToken);
    sessionStorage.setItem(AuthService.REFRESH_TOKEN_STORAGE_NAME, jwtToken.refreshToken);
    this.setNewRefreshTimeout(jwtToken.expiresIn);

    return true;
  }

  /**
   * Set a callback to renew the access token before it expires.
   */
  private setNewRefreshTimeout(expiresIn: number) {
    this.clearTimeoutIfAny();

    const timeout = expiresIn - 1000;
    this.refreshTokenTimeout = setTimeout(() => this.renewAccessToken(), timeout);
  }

  private clearTimeoutIfAny() {
    if (this.refreshTokenTimeout) {
      clearTimeout(this.refreshTokenTimeout);
      this.refreshTokenTimeout = undefined;
    }
  }

  /**
   * Tires to renew the access token using the refresh token.
   * In case of any errors, the user is logged out.
   */
  private renewAccessToken() {
    const refreshToken = sessionStorage.getItem(AuthService.REFRESH_TOKEN_STORAGE_NAME);
    if (refreshToken === undefined) {
      console.log('No refresh token was found - forcing user to logout');
      this.logout();
    }

    this.httpClient.post<JwtTokenDTO>('api/v1/auth/refresh-token', { refreshToken } as RefreshTokenDTO)
      .pipe(
        map((jwtToken: JwtTokenDTO) => this.handleReceivedToken(jwtToken)),
        catchError((err: HttpErrorResponse) => {
          console.log('An error occurred for the refresh token request', err);
          this.logout();
          return of(false);
        })
      ).subscribe();
  }

  /**
   * Logs out the user by clearing the session storage and routing to the login page.
   */
  public logout() {
    console.log("Logging out user...")
    this.clearTimeoutIfAny();
    sessionStorage.clear();
    this.router.navigate(['login']);
  }
}
