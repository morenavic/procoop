import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServicioPublicoService } from '../../../core/services/servicio-publico.service';
import { Servicio } from '../../../core/models/servicio.model';

/**
 * CU05 — Consultar Servicios (públicos)
 * CU06 — Ver más información de un servicio (modal)
 */
@Component({
  selector: 'app-servicios-publico',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './servicios.html',
  styleUrl: './servicios.scss',
})
export class ServiciosPublicoComponent implements OnInit {
  readonly LIMITE_INICIAL = 6;

  todos = signal<Servicio[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);
  mostrarTodos = signal(false);
  servicioSeleccionado = signal<Servicio | null>(null);

  constructor(private servicioService: ServicioPublicoService) {}

  ngOnInit(): void {
    this.servicioService.listar().subscribe({
      next: (data) => {
        this.todos.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los servicios.');
        this.cargando.set(false);
      },
    });
  }

  get serviciosMostrados(): Servicio[] {
    return this.mostrarTodos() ? this.todos() : this.todos().slice(0, this.LIMITE_INICIAL);
  }

  get hayMas(): boolean {
    return this.todos().length > this.LIMITE_INICIAL;
  }

  abrirModal(servicio: Servicio): void {
    this.servicioSeleccionado.set(servicio);
    document.body.style.overflow = 'hidden';
  }

  cerrarModal(): void {
    this.servicioSeleccionado.set(null);
    document.body.style.overflow = '';
  }
}
