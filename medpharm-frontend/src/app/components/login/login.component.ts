import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  readonly loginForm: FormGroup = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  readonly errorMensaje = signal<string | null>(null);
  readonly cargando = signal<boolean>(false);

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.cargando.set(true);
    this.errorMensaje.set(null);

    const { username, password } = this.loginForm.value;

    this.authService.login({ username, password }).subscribe({
      next: () => {
        this.cargando.set(false);
        this.router.navigate(['/recetas']);
      },
      error: (err) => {
        this.cargando.set(false);
        if (err.status === 401) {
          this.errorMensaje.set('Credenciales incorrectas. Verifique su usuario y contraseña.');
        } else {
          this.errorMensaje.set('Error al conectar con el servidor.');
        }
      }
    });
  }

  // Ayudante para autocompletar credenciales en pruebas
  cargarCredenciales(usuario: string): void {
    this.loginForm.patchValue({
      username: usuario,
      password: 'password123'
    });
  }
}
