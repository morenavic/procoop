import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { RecuperacionService } from '../../../core/services/recuperacion.service';

/**
 * Componente para solicitar recuperación de contraseña.
 * CU24 — Recuperar contraseña.
 */
@Component({
  selector: 'app-recuperar',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './recuperar.html',
  styleUrl: './recuperar.scss',
})
export class RecuperarComponent {
  formulario: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);
  exitoso = signal(false);

  constructor(
    private fb: FormBuilder,
    private recuperacionService: RecuperacionService,
  ) {
    this.formulario = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      numeroCuenta: ['', [Validators.required]],
    });
  }

  get email() {
    return this.formulario.get('email')!;
  }
  get numeroCuenta() {
    return this.formulario.get('numeroCuenta')!;
  }

  enviar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.cargando.set(true);
    this.error.set(null);

    this.recuperacionService.recuperar(this.formulario.value).subscribe({
      next: () => {
        this.cargando.set(false);
        this.exitoso.set(true);
      },
      error: (err) => {
        this.cargando.set(false);
        if (err.status === 404) {
          this.error.set('No existe una cuenta con ese email.');
        } else if (err.status === 400) {
          this.error.set('Los datos ingresados no coinciden.');
        } else if (err.status === 403) {
          this.error.set('La cuenta no está activa.');
        } else {
          this.error.set('No se pudo enviar el email. Intentá nuevamente.');
        }
      },
    });
  }
}
