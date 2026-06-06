import { Component, Input, Output, EventEmitter, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServicioService } from '../../../../core/services/servicio.service';
import { Servicio } from '../../../../core/models/servicio.model';

/**
 * Formulario de creación y edición de servicios.
 * Sin manejo de archivos — usa JSON simple.
 */
@Component({
  selector: 'app-servicio-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './servicio-form.html',
  styleUrls: ['./servicio-form.scss'],
})
export class ServicioFormComponent implements OnInit {
  @Input() servicio: Servicio | null = null;
  @Output() alCerrar = new EventEmitter<void>();
  @Output() alGuardar = new EventEmitter<void>();

  formulario!: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);

  get esEdicion(): boolean {
    return this.servicio !== null;
  }
  get titulo(): string {
    return this.esEdicion ? 'Editar servicio' : 'Nuevo servicio';
  }

  constructor(
    private fb: FormBuilder,
    private servicioService: ServicioService,
  ) {}

  ngOnInit(): void {
    this.formulario = this.fb.group({
      titulo: [this.servicio?.titulo ?? '', [Validators.required, Validators.maxLength(200)]],
      descripcion: [this.servicio?.descripcion ?? '', [Validators.required]],
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
      ? this.servicioService.editar(this.servicio!.idServicio, this.formulario.value)
      : this.servicioService.crear(this.formulario.value);

    operacion.subscribe({
      next: () => {
        this.cargando.set(false);
        this.alGuardar.emit();
      },
      error: (err) => {
        this.cargando.set(false);
        this.error.set(err.error?.error ?? 'No se pudo guardar el servicio.');
      },
    });
  }
}
