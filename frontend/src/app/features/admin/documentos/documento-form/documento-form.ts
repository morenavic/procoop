import { Component, Input, Output, EventEmitter, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DocumentoService } from '../../../../core/services/documento.service';
import { Documento } from '../../../../core/models/documento.model';

/**
 * Formulario de creación y edición de documentos.
 *
 * En modo creación:
 *  - El archivo es obligatorio
 *
 * En modo edición:
 *  - El archivo es opcional — si no se sube uno nuevo se conserva el existente
 *  - Muestra el nombre del archivo actual
 */
@Component({
  selector: 'app-documento-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './documento-form.html',
  styleUrls: ['./documento-form.scss'],
})
export class DocumentoFormComponent implements OnInit {
  @Input() documento: Documento | null = null;
  @Output() alCerrar = new EventEmitter<void>();
  @Output() alGuardar = new EventEmitter<void>();

  formulario!: FormGroup;
  cargando = signal(false);
  error = signal<string | null>(null);
  archivoSeleccionado = signal<File | null>(null);

  get esEdicion(): boolean {
    return this.documento !== null;
  }
  get titulo(): string {
    return this.esEdicion ? 'Editar documento' : 'Nuevo documento';
  }

  get nombreArchivoActual(): string {
    if (!this.documento?.archivoUrl) return '';
    const partes = this.documento.archivoUrl.split('/');
    return partes[partes.length - 1];
  }

  constructor(
    private fb: FormBuilder,
    private documentoService: DocumentoService,
  ) {}

  ngOnInit(): void {
    this.formulario = this.fb.group({
      nombre: [this.documento?.nombre ?? '', [Validators.required, Validators.maxLength(200)]],
      descripcion: [this.documento?.descripcion ?? '', []],
      tipo: [this.documento?.tipo ?? '', [Validators.required]],
    });
  }

  alSeleccionarArchivo(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;
    this.archivoSeleccionado.set(input.files[0]);
  }

  quitarArchivo(): void {
    this.archivoSeleccionado.set(null);
  }

  guardar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    if (!this.esEdicion && !this.archivoSeleccionado()) {
      this.error.set('El archivo es obligatorio.');
      return;
    }

    this.cargando.set(true);
    this.error.set(null);

    const formData = new FormData();
    formData.append(
      'datos',
      new Blob([JSON.stringify(this.formulario.value)], { type: 'application/json' }),
    );

    if (this.archivoSeleccionado()) {
      formData.append('archivo', this.archivoSeleccionado()!);
    }

    const operacion = this.esEdicion
      ? this.documentoService.editar(this.documento!.idDocumento, formData)
      : this.documentoService.crear(formData);

    operacion.subscribe({
      next: () => {
        this.cargando.set(false);
        this.alGuardar.emit();
      },
      error: (err) => {
        this.cargando.set(false);
        this.error.set(err.error?.error ?? 'No se pudo guardar el documento.');
      },
    });
  }
}
