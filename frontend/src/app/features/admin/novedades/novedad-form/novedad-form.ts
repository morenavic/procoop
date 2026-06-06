import { Component, Input, Output, EventEmitter, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NovedadService } from '../../../../core/services/novedad.service';
import { Novedad } from '../../../../core/models/novedad.model';

/**
 * Formulario de creación y edición de novedades.
 *
 * Se muestra como panel deslizante sobre el listado.
 * Recibe la novedad a editar por @Input (null = modo creación).
 * Emite eventos al cerrar o guardar exitosamente.
 *
 * Manejo de imagen:
 *  - Muestra preview de la imagen seleccionada antes de subir
 *  - Si hay imagen existente (modo edición) la muestra como preview inicial
 *  - La imagen es opcional en creación y edición
 */
@Component({
  selector: 'app-novedad-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './novedad-form.html',
  styleUrls: ['./novedad-form.scss'],
})
export class NovedadFormComponent implements OnInit {
  @Input() novedad: Novedad | null = null;
  @Output() alCerrar = new EventEmitter<void>();
  @Output() alGuardar = new EventEmitter<void>();

  formulario!: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);
  imagenPreview = signal<string | null>(null);
  archivoImagen: File | null = null;

  get esEdicion(): boolean {
    return this.novedad !== null;
  }
  get titulo(): string {
    return this.esEdicion ? 'Editar novedad' : 'Nueva novedad';
  }

  constructor(
    private fb: FormBuilder,
    private novedadService: NovedadService,
  ) {}

  ngOnInit(): void {
    this.formulario = this.fb.group({
      titulo: [this.novedad?.titulo ?? '', [Validators.required, Validators.maxLength(200)]],
      descripcion: [this.novedad?.descripcion ?? '', [Validators.required]],
      fecha: [this.novedad?.fecha ?? '', [Validators.required]],
      tipo: [this.novedad?.tipo ?? '', [Validators.required]],
    });

    if (this.novedad?.imagenUrl) {
      this.imagenPreview.set(this.novedad.imagenUrl);
    }
  }

  alSeleccionarImagen(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;

    const archivo = input.files[0];
    this.archivoImagen = archivo;

    const reader = new FileReader();
    reader.onload = () => this.imagenPreview.set(reader.result as string);
    reader.readAsDataURL(archivo);
  }

  quitarImagen(): void {
    this.archivoImagen = null;
    this.imagenPreview.set(null);
  }

  guardar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.cargando.set(true);
    this.error.set(null);

    const formData = new FormData();
    formData.append(
      'datos',
      new Blob([JSON.stringify(this.formulario.value)], { type: 'application/json' }),
    );

    if (this.archivoImagen) {
      formData.append('imagen', this.archivoImagen);
    }

    const operacion = this.esEdicion
      ? this.novedadService.editar(this.novedad!.idNovedad, formData)
      : this.novedadService.crear(formData);

    operacion.subscribe({
      next: () => {
        this.cargando.set(false);
        this.alGuardar.emit();
      },
      error: (err) => {
        this.cargando.set(false);
        this.error.set(err.error?.error ?? 'No se pudo guardar la novedad.');
      },
    });
  }
}
