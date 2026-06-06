import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentoService } from '../../../core/services/documento.service';
import { Documento } from '../../../core/models/documento.model';
import { DocumentoFormComponent } from './documento-form/documento-form';

/**
 * Componente principal de gestión de documentos.
 *
 * Funcionalidades:
 *  - Listado de documentos con opción de descarga
 *  - Crear, editar y eliminar documentos
 */
@Component({
  selector: 'app-documentos',
  standalone: true,
  imports: [CommonModule, DocumentoFormComponent],
  templateUrl: './documentos.html',
  styleUrl: './documentos.scss',
})
export class DocumentosComponent implements OnInit {
  documentos = signal<Documento[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  formularioAbierto = signal(false);
  documentoEditando = signal<Documento | null>(null);
  documentoAEliminar = signal<Documento | null>(null);
  eliminando = signal(false);

  constructor(private documentoService: DocumentoService) {}

  ngOnInit(): void {
    this.cargarDocumentos();
  }

  cargarDocumentos(): void {
    this.cargando.set(true);
    this.documentoService.listar().subscribe({
      next: (data) => {
        this.documentos.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los documentos.');
        this.cargando.set(false);
      },
    });
  }

  abrirFormularioNuevo(): void {
    this.documentoEditando.set(null);
    this.formularioAbierto.set(true);
  }

  abrirFormularioEdicion(documento: Documento): void {
    this.documentoEditando.set(documento);
    this.formularioAbierto.set(true);
  }

  cerrarFormulario(): void {
    this.formularioAbierto.set(false);
    this.documentoEditando.set(null);
  }

  alGuardar(): void {
    this.cerrarFormulario();
    this.cargarDocumentos();
  }

  descargar(documento: Documento): void {
    if (!documento.archivoUrl) return;
    const link = document.createElement('a');
    link.href = documento.archivoUrl;
    link.download = documento.nombre;
    link.target = '_blank';
    link.click();
  }

  confirmarEliminacion(documento: Documento): void {
    this.documentoAEliminar.set(documento);
  }

  cancelarEliminacion(): void {
    this.documentoAEliminar.set(null);
  }

  ejecutarEliminacion(): void {
    const documento = this.documentoAEliminar();
    if (!documento) return;

    this.eliminando.set(true);
    this.documentoService.eliminar(documento.idDocumento).subscribe({
      next: () => {
        this.eliminando.set(false);
        this.documentoAEliminar.set(null);
        this.cargarDocumentos();
      },
      error: () => {
        this.eliminando.set(false);
        this.error.set('No se pudo eliminar el documento.');
      },
    });
  }

  obtenerExtension(url: string | null): string {
    if (!url) return '';
    const partes = url.split('.');
    return partes[partes.length - 1].toUpperCase();
  }

  claseExtension(url: string | null): string {
    const ext = this.obtenerExtension(url).toLowerCase();
    const clases: Record<string, string> = {
      pdf: 'ext--pdf',
      doc: 'ext--word',
      docx: 'ext--word',
      xls: 'ext--excel',
      xlsx: 'ext--excel',
    };
    return clases[ext] ?? 'ext--default';
  }
}
