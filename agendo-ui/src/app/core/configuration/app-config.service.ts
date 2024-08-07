/* eslint-disable @typescript-eslint/no-explicit-any */
import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {

  private httpClient;
  
  private appConfig: any;

  constructor(private handler: HttpBackend) {
    // We create a new http client without interceptors to avoid a circular dependency
    // HttpClient has a dependency for http interceptor and this service may be used in interceptors
    this.httpClient = new HttpClient(handler);
  }

  async loadAppConfig() {
    await firstValueFrom(this.httpClient.get<any>('/assets/config.json')
      .pipe(
        map((data: any) => this.appConfig = data)
      )
    )
  }

  get apiBaseUrl(): string {
    if (this.appConfig === undefined) {
      throw new Error('App config has not been loaded');
    }

    if (this.appConfig.apiBaseUrl === undefined) {
      throw new Error('Api base URL not defined in app config json');
    }

    return this.appConfig.apiBaseUrl;
  }
}
