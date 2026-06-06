import { Component, Input, Output, EventEmitter, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ClienteService } from '../../../../core/services/cliente.service';
import { Cliente } from '../../../../core/models/cliente.model';

/**
 * Formulario de creación y edición de clientes.
 *
 * En modo creación:
 *  - Permite ingresar nombre, email y número de cuenta
 *  - El estado inicial siempre es PENDIENTE
 *  - La contraseña inicial es el número de cuenta
 *
 * En modo edición:
 *  - Permite modificar nombre, email y número de cuenta
 *  - No permite cambiar el estado (se hace desde la tabla)
 */
@Component({
  selector: 'app-cliente-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cliente-form.html',
  styleUrl: './cliente-form.scss',
})
export class ClienteFormComponent implements OnInit {
  @Input() cliente: Cliente | null = null;
  @Output() alCerrar = new EventEmitter<void>();
  @Output() alGuardar = new EventEmitter<void>();

  formulario!: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);

  get esEdicion(): boolean {
    return this.cliente !== null;
  }
  get titulo(): string {
    return this.esEdicion ? 'Editar cliente' : 'Nuevo cliente';
  }

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
  ) {}

  ngOnInit(): void {
    this.formulario = this.fb.group({
      nombre: [this.cliente?.nombre ?? '', [Validators.required, Validators.maxLength(100)]],
      email: [this.cliente?.email ?? '', [Validators.required, Validators.email]],
      numeroCuenta: [
        this.cliente?.numeroCuenta ?? '',
        [Validators.required, Validators.maxLength(50)],
      ],
    });
  }

  guardar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.cargando.set(true);
    this.error.set(null);

    const operacion = this.esEdicion
      ? this.clienteService.editar(this.cliente!.idUsuario, this.formulario.value)
      : this.clienteService.crear(this.formulario.value);

    operacion.subscribe({
      next: () => {
        this.cargando.set(false);
        this.alGuardar.emit();
      },
      error: (err) => {
        this.cargando.set(false);
        this.error.set(err.error?.error ?? 'No se pudo guardar el cliente.');
      },
    });
  }
}
