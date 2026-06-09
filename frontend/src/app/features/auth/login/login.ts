import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

/**
 * Componente de inicio de sesión.
 *
 * Maneja el formulario de login con validaciones reactivas.
 * Al autenticarse exitosamente, redirige según el rol:
 *  - ADMINISTRADOR → /admin/inicio
 *  - CLIENTE       → /cliente/inicio (futuro)
 *
 * Usa signals para el estado de carga y errores,
 * evitando variables booleanas sueltas.
 */
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class LoginComponent {
  formulario: FormGroup;
  cargando = signal(false);
  errorMensaje = signal<string | null>(null);
  mostrarContrasenia = signal(false);

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
    this.formulario = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      contrasenia: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  get email() {
    return this.formulario.get('email')!;
  }
  get contrasenia() {
    return this.formulario.get('contrasenia')!;
  }

  toggleContrasenia(): void {
    this.mostrarContrasenia.update((v) => !v);
  }

  enviar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.cargando.set(true);
    this.errorMensaje.set(null);

    this.authService.login(this.formulario.value).subscribe({
      next: (respuesta) => {
        this.cargando.set(false);
        if (respuesta.rol === 'ADMINISTRADOR') {
          this.router.navigate(['/admin/inicio']);
        } else {
          this.router.navigate(['/cliente/inicio']);
        }
      },
      error: (err) => {
        this.cargando.set(false);
        this.errorMensaje.set(err.error?.error ?? 'No se pudo conectar con el servidor');
      },
    });
  }
}
