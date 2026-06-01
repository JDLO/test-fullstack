import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../models/LoginRequest';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  login(credentials: LoginRequest): Observable<any> {
    return this.http.post(this.apiUrl + '/login', credentials);
  }

  initiateSSO(): void {
    window.location.href = this.apiUrl + '/sso';
  }

  validateSsoCode(code: string): Observable<any> {
    const params = new HttpParams().set('code', code);
    return this.http.get(this.apiUrl + '/sso/callback', {params});
  }

  saveToken(token: string): void {
    localStorage.setItem('auth_token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  logout(): void {
    localStorage.removeItem('auth_token');
  }
}
