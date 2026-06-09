import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ActivacionService } from '../../../core/services/activacion.service';

/**
 * Componente de activación de cuenta.
 *
 * Solo requiere número de cuenta y contraseña nueva.
 * El nombre y email ya fueron cargados por el administrador.
 */
@Component({
  selector: 'app-activacion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './activacion.html',
  styleUrl: './activacion.scss',
})
export class ActivacionComponent {
  formulario: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);
  exitoso = signal(false);
  mostrarContrasenia = signal(false);

  constructor(
    private fb: FormBuilder,
    private activacionService: ActivacionService,
    private router: Router,
  ) {
    this.formulario = this.fb.group({
      numeroCuenta: ['', [Validators.required]],
      contrasenia: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  get numeroCuenta() {
    return this.formulario.get('numeroCuenta')!;
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
    this.error.set(null);

    this.activacionService.activar(this.formulario.value).subscribe({
      next: () => {
        this.cargando.set(false);
        this.exitoso.set(true);
        setTimeout(() => this.router.navigate(['/acceso/login']), 2500);
      },
      error: (err) => {
        this.cargando.set(false);
        if (err.status === 404) {
          this.error.set(
            'El número de cuenta no existe. Verificá el dato o contactá al administrador.',
          );
        } else if (err.status === 409) {
          this.error.set('Esta cuenta ya fue activada o está inactiva.');
        } else {
          this.error.set('No se pudo activar la cuenta. Intentá nuevamente.');
        }
      },
    });
  }
}
