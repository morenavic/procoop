import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentoPublicoService } from '../../../core/services/documento-publico.service';
import { Documento } from '../../../core/models/documento.model';

/**
 * Componente de documentos del cliente.
 * CU18 — Listar, CU19 — Buscar, CU20 — Descargar, CU21 — Filtrar.
 *
 * Todo en un solo componente: búsqueda, filtro por tipo y descarga.
 */
@Component({
  selector: 'app-documentos-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './documentos-cliente.html',
  styleUrl: './documentos-cliente.scss',
})
export class DocumentosClienteComponent implements OnInit {
  documentos = signal<Documento[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  busqueda = '';
  filtroTipo = 'TODOS';

  readonly filtros = [
    { valor: 'TODOS', etiqueta: 'Todos' },
    { valor: 'MANUAL', etiqueta: 'Manuales' },
    { valor: 'GUIA', etiqueta: 'Guías' },
    { valor: 'OTRO', etiqueta: 'Otros' },
  ];

  constructor(private documentoService: DocumentoPublicoService) {}

  ngOnInit(): void {
    this.cargarDocumentos();
  }

  cargarDocumentos(): void {
    this.cargando.set(true);
    this.error.set(null);

    this.documentoService.listar(this.busqueda, this.filtroTipo).subscribe({
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

  alBuscar(): void {
    this.filtroTipo = 'TODOS';
    this.cargarDocumentos();
  }

  limpiarBusqueda(): void {
    this.busqueda = '';
    this.cargarDocumentos();
  }

  setFiltro(tipo: string): void {
    this.filtroTipo = tipo;
    this.busqueda = '';
    this.cargarDocumentos();
  }

  descargar(documento: Documento): void {
    if (!documento.archivoUrl) return;
    const link = document.createElement('a');
    link.href = documento.archivoUrl;
    link.target = '_blank';
    link.click();
  }

  etiquetaTipo(tipo: string): string {
    const etiquetas: Record<string, string> = {
      MANUAL: 'Manual',
      GUIA: 'Guía',
      OTRO: 'Otro',
    };
    return etiquetas[tipo] ?? tipo;
  }

  claseTipo(tipo: string): string {
    const clases: Record<string, string> = {
      MANUAL: 'tipo--manual',
      GUIA: 'tipo--guia',
      OTRO: 'tipo--otro',
    };
    return clases[tipo] ?? '';
  }
}
