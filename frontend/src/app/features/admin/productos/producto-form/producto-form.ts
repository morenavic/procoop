import { Component, Input, Output, EventEmitter, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../../../core/services/producto.service';
import { Producto } from '../../../../core/models/producto.model';

/**
 * Formulario de creación y edición de productos.
 *
 * Se muestra como panel deslizante lateral.
 * Maneja preview de imagen antes de subir.
 * La imagen es opcional en creación y edición.
 */
@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './producto-form.html',
  styleUrls: ['./producto-form.scss'],
})
export class ProductoFormComponent implements OnInit {
  @Input() producto: Producto | null = null;
  @Output() alCerrar = new EventEmitter<void>();
  @Output() alGuardar = new EventEmitter<void>();

  formulario!: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);
  imagenPreview = signal<string | null>(null);
  archivoImagen: File | null = null;

  get esEdicion(): boolean {
    return this.producto !== null;
  }
  get titulo(): string {
    return this.esEdicion ? 'Editar producto' : 'Nuevo producto';
  }

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
  ) {}

  ngOnInit(): void {
    this.formulario = this.fb.group({
      titulo: [this.producto?.titulo ?? '', [Validators.required, Validators.maxLength(200)]],
      subtitulo: [this.producto?.subtitulo ?? '', [Validators.maxLength(300)]],
      descripcion: [this.producto?.descripcion ?? '', [Validators.required]],
    });

    if (this.producto?.imagenUrl) {
      this.imagenPreview.set(this.producto.imagenUrl);
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
      ? this.productoService.editar(this.producto!.idProducto, formData)
      : this.productoService.crear(formData);

    operacion.subscribe({
      next: () => {
        this.cargando.set(false);
        this.alGuardar.emit();
      },
      error: (err) => {
        this.cargando.set(false);
        this.error.set(err.error?.error ?? 'No se pudo guardar el producto.');
      },
    });
  }
}
