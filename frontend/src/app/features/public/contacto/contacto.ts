import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ContactoService } from '../../../core/services/contacto.service';


/**
 * CU07 — Acceder a Contacto
 * CU08 — Enviar mail de consulta
 */
@Component({
  selector: 'app-contacto',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contacto.html',
  styleUrl: './contacto.scss',
})
export class ContactoComponent {
  formulario: FormGroup;
  cargando = signal(false);
  enviado = signal(false);
  error = signal<string | null>(null);

  readonly mediosContacto = [
    {
      icono: 'telefono',
      titulo: 'Teléfono',
      valor: '+54 (351) 524 3700',
    },
    {
      icono: 'email',
      titulo: 'Email',
      valor: 'info@procoopsrl.com.ar',
    },
    {
      icono: 'ubicacion',
      titulo: 'Córdoba',
      valor: 'José Baigorri 491 - 5000',
    },
    {
      icono: 'ubicacion',
      titulo: 'Monte Maíz',
      valor: 'Buenos Aires 1507 - X2659AKG',
    },
  ];

  constructor(
    private fb: FormBuilder,
    private contactoService: ContactoService,
  ) {
    this.formulario = this.fb.group({
      nombre: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      telefono: [''],
      asunto: ['', [Validators.required]],
      mensaje: ['', [Validators.required, Validators.minLength(10)]],
    });
  }

  get nombre() {
    return this.formulario.get('nombre')!;
  }
  get email() {
    return this.formulario.get('email')!;
  }
  get asunto() {
    return this.formulario.get('asunto')!;
  }
  get mensaje() {
    return this.formulario.get('mensaje')!;
  }

  enviar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.cargando.set(true);
    this.error.set(null);

    this.contactoService.enviar(this.formulario.value).subscribe({
      next: () => {
        this.cargando.set(false);
        this.enviado.set(true);
        this.formulario.reset();
      },
      error: () => {
        this.cargando.set(false);
        this.error.set('No se pudo enviar la consulta. Intentá nuevamente.');
      },
    });
  }

  nuevaConsulta(): void {
    this.enviado.set(false);
  }
}
