import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NovedadService } from '../../../core/services/novedad.service';
import { Novedad } from '../../../core/models/novedad.model';
import { NovedadFormComponent } from './novedad-form/novedad-form';

/**
 * Componente principal de gestión de novedades.
 *
 * Muestra el listado de novedades con acciones para:
 *  - Crear nueva novedad (abre formulario)
 *  - Editar novedad existente (abre formulario con datos precargados)
 *  - Eliminar novedad (con confirmación)
 *
 * El formulario se muestra como panel lateral (no como modal ni nueva ruta),
 * para una experiencia más fluida dentro del panel admin.
 */
@Component({
  selector: 'app-novedades',
  standalone: true,
  imports: [CommonModule, NovedadFormComponent],
  templateUrl: './novedades.html',
  styleUrls: ['./novedades.scss'],
})
export class NovedadesComponent implements OnInit {
  novedades = signal<Novedad[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  // Control del formulario lateral
  formularioAbierto = signal(false);
  novedadEditando = signal<Novedad | null>(null);

  // Control del diálogo de confirmación de eliminación
  novedadAEliminar = signal<Novedad | null>(null);
  eliminando = signal(false);

  constructor(private novedadService: NovedadService) {}

  ngOnInit(): void {
    this.cargarNovedades();
  }

  cargarNovedades(): void {
    this.cargando.set(true);
    this.novedadService.listar().subscribe({
      next: (data) => {
        this.novedades.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las novedades.');
        this.cargando.set(false);
      },
    });
  }

  abrirFormularioNuevo(): void {
    this.novedadEditando.set(null);
    this.formularioAbierto.set(true);
  }

  abrirFormularioEdicion(novedad: Novedad): void {
    this.novedadEditando.set(novedad);
    this.formularioAbierto.set(true);
  }

  cerrarFormulario(): void {
    this.formularioAbierto.set(false);
    this.novedadEditando.set(null);
  }

  alGuardar(): void {
    this.cerrarFormulario();
    this.cargarNovedades();
  }

  confirmarEliminacion(novedad: Novedad): void {
    this.novedadAEliminar.set(novedad);
  }

  cancelarEliminacion(): void {
    this.novedadAEliminar.set(null);
  }

  ejecutarEliminacion(): void {
    const novedad = this.novedadAEliminar();
    if (!novedad) return;

    this.eliminando.set(true);
    this.novedadService.eliminar(novedad.idNovedad).subscribe({
      next: () => {
        this.eliminando.set(false);
        this.novedadAEliminar.set(null);
        this.cargarNovedades();
      },
      error: () => {
        this.eliminando.set(false);
        this.error.set('No se pudo eliminar la novedad.');
      },
    });
  }

  etiquetaTipo(tipo: string): string {
    return tipo === 'NOTICIA' ? 'Noticia' : 'Evento';
  }
}
