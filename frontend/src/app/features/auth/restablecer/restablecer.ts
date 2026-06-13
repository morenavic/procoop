import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { RecuperacionService } from '../../../core/services/recuperacion.service';

/**
 * Componente para restablecer la contraseña usando el token del email.
 * CU25 — Enviar enlace de recuperación.
 *
 * El token se lee automáticamente desde el parámetro de la URL.
 */
@Component({
  selector: 'app-restablecer',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './restablecer.html',
  styleUrl: './restablecer.scss',
})
export class RestablecerComponent implements OnInit {
  formulario: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);
  exitoso = signal(false);
  tokenValido = signal(true);
  mostrarNueva = signal(false);
  mostrarConfirmacion = signal(false);

  private token = '';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private recuperacionService: RecuperacionService,
  ) {
    this.formulario = this.fb.group({
      contraseniaNueva: ['', [Validators.required, Validators.minLength(6)]],
      confirmacion: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token') ?? '';
    if (!this.token) {
      this.tokenValido.set(false);
      this.error.set('El enlace de recuperación no es válido.');
    }
  }

  get contraseniaNueva() {
    return this.formulario.get('contraseniaNueva')!;
  }
  get confirmacion() {
    return this.formulario.get('confirmacion')!;
  }

  get confirmacionNoCoincide(): boolean {
    return this.confirmacion.touched && this.contraseniaNueva.value !== this.confirmacion.value;
  }

  guardar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    if (this.contraseniaNueva.value !== this.confirmacion.value) {
      this.error.set('Las contraseñas no coinciden.');
      return;
    }

    this.cargando.set(true);
    this.error.set(null);

    //console.log('Enviando token:', this.token); // DEBUG TEMPORAL

    this.recuperacionService
      .restablecer({
        token: this.token,
        contraseniaNueva: this.contraseniaNueva.value,
        confirmacion: this.confirmacion.value,
      })
      .subscribe({
        next: () => {
          this.cargando.set(false);
          this.exitoso.set(true);
          setTimeout(() => this.router.navigate(['/acceso/login']), 2500);
        },
        error: (err) => {
          this.cargando.set(false);
          console.log('Error completo:', err);
          console.log('err.error:', err.error);
          console.log('err.status:', err.status);

          // Intentar obtener el mensaje de distintas estructuras posibles
          const mensaje =
            err.error?.error ||
            err.error?.mensaje ||
            err.message ||
            'No se pudo restablecer la contraseña.';

          this.error.set(mensaje);
        },
      });
  }
}
