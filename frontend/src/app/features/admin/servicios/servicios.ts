import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServicioService } from '../../../core/services/servicio.service';
import { Servicio } from '../../../core/models/servicio.model';
import { ServicioFormComponent } from './servicio-form/servicio-form';

/**
 * Componente principal de gestión de servicios.
 * Mismo patrón que Novedades y Productos.
 */
@Component({
  selector: 'app-servicios',
  standalone: true,
  imports: [CommonModule, ServicioFormComponent],
  templateUrl: './servicios.html',
  styleUrls: ['./servicios.scss'],
})
export class ServiciosComponent implements OnInit {
  servicios = signal<Servicio[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  formularioAbierto = signal(false);
  servicioEditando = signal<Servicio | null>(null);
  servicioAEliminar = signal<Servicio | null>(null);
  eliminando = signal(false);

  constructor(private servicioService: ServicioService) {}

  ngOnInit(): void {
    this.cargarServicios();
  }

  cargarServicios(): void {
    this.cargando.set(true);
    this.servicioService.listar().subscribe({
      next: (data) => {
        this.servicios.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los servicios.');
        this.cargando.set(false);
      },
    });
  }

  abrirFormularioNuevo(): void {
    this.servicioEditando.set(null);
    this.formularioAbierto.set(true);
  }

  abrirFormularioEdicion(servicio: Servicio): void {
    this.servicioEditando.set(servicio);
    this.formularioAbierto.set(true);
  }

  cerrarFormulario(): void {
    this.formularioAbierto.set(false);
    this.servicioEditando.set(null);
  }

  alGuardar(): void {
    this.cerrarFormulario();
    this.cargarServicios();
  }

  confirmarEliminacion(servicio: Servicio): void {
    this.servicioAEliminar.set(servicio);
  }

  cancelarEliminacion(): void {
    this.servicioAEliminar.set(null);
  }

  ejecutarEliminacion(): void {
    const servicio = this.servicioAEliminar();
    if (!servicio) return;

    this.eliminando.set(true);
    this.servicioService.eliminar(servicio.idServicio).subscribe({
      next: () => {
        this.eliminando.set(false);
        this.servicioAEliminar.set(null);
        this.cargarServicios();
      },
      error: () => {
        this.eliminando.set(false);
        this.error.set('No se pudo eliminar el servicio.');
      },
    });
  }
}
