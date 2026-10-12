import { Injectable, signal, computed, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { AuthResponse, LoginRequest } from '../models/auth.models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly apiUrl = 'http://localhost:8080/api/v1/auth';

  // Reactividad con Signal para el estado de autenticación
  readonly currentUser = signal<AuthResponse | null>(this.obtenerSesionInicial());
  readonly isAuthenticated = computed(() => !!this.currentUser()?.token);
  readonly userRole = computed(() => this.currentUser()?.rol ?? null);
  readonly username = computed(() => this.currentUser()?.username ?? null);

  private obtenerSesionInicial(): AuthResponse | null {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const rol = localStorage.getItem('rol');

    if (token && username && rol) {
      return { token, username, rol };
    }
    return null;
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('username', response.username);
        localStorage.setItem('rol', response.rol);
        this.currentUser.set(response);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('rol');
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.currentUser()?.token || localStorage.getItem('token');
  }

  getRol(): string | null {
    return this.userRole();
  }

  getUsername(): string | null {
    return this.username();
  }
}
