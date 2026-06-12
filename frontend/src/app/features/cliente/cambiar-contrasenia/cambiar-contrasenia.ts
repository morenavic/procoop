import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PerfilService } from '../../../core/services/perfil.service';

/**
 * Componente Cambiar contraseña del cliente.
 * CU17 — Cambiar contraseña.
 *
 * Valida la contraseña actual y permite definir una nueva.
 * La confirmación se valida en el backend y en el frontend.
 */
@Component({
  selector: 'app-cambiar-contrasenia',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './cambiar-contrasenia.html',
  styleUrl: './cambiar-contrasenia.scss',
})
export class CambiarContraseniaComponent {
  formulario: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);
  exitoso = signal(false);
  mostrarActual = signal(false);
  mostrarNueva = signal(false);
  mostrarConfirmacion = signal(false);

  constructor(
    private fb: FormBuilder,
    private perfilService: PerfilService,
  ) {
    this.formulario = this.fb.group({
      contraseniaActual: ['', [Validators.required]],
      contraseniaNueva: ['', [Validators.required, Validators.minLength(6)]],
      confirmacion: ['', [Validators.required]],
    });
  }

  get contraseniaActual() {
    return this.formulario.get('contraseniaActual')!;
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
      this.error.set('La nueva contraseña y la confirmación no coinciden.');
      return;
    }

    this.cargando.set(true);
    this.error.set(null);

    this.perfilService.cambiarContrasenia(this.formulario.value).subscribe({
      next: () => {
        this.cargando.set(false);
        this.exitoso.set(true);
        this.formulario.reset();
      },
      error: (err) => {
        this.cargando.set(false);
        if (err.status === 400) {
          this.error.set(err.error?.error ?? 'Los datos ingresados son incorrectos.');
        } else {
          this.error.set('No se pudo cambiar la contraseña. Intentá nuevamente.');
        }
      },
    });
  }
}
