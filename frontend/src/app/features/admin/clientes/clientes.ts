import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../../core/services/cliente.service';
import { Cliente } from '../../../core/models/cliente.model';
import { ClienteFormComponent } from './cliente-form/cliente-form';

/**
 * Componente principal de gestión de clientes.
 *
 * Funcionalidades adicionales respecto a otros módulos:
 *  - Cambio de estado ACTIVO/INACTIVO/PENDIENTE directamente desde la tabla
 *  - Filtrado por estado para ver pendientes de activación
 *  - Indicador visual del estado con colores diferenciados
 */
@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, ClienteFormComponent],
  templateUrl: './clientes.html',
  styleUrl: './clientes.scss',
})
export class ClientesComponent implements OnInit {
  clientes = signal<Cliente[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);
  filtroEstado = signal<string>('TODOS');

  formularioAbierto = signal(false);
  clienteEditando = signal<Cliente | null>(null);
  clienteAEliminar = signal<Cliente | null>(null);
  eliminando = signal(false);
  cambiandoEstado = signal<number | null>(null);

  // Clientes filtrados por estado
  clientesFiltrados = computed(() => {
    const filtro = this.filtroEstado();
    const todos = this.clientes();
    if (filtro === 'TODOS') return todos;
    return todos.filter((c) => c.estado === filtro);
  });

  // Contadores por estado para los botones de filtro
  totalPendientes = computed(() => this.clientes().filter((c) => c.estado === 'PENDIENTE').length);
  totalInactivos = computed(() => this.clientes().filter((c) => c.estado === 'INACTIVO').length);

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.cargando.set(true);
    this.clienteService.listar().subscribe({
      next: (data) => {
        this.clientes.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los clientes.');
        this.cargando.set(false);
      },
    });
  }

  abrirFormularioNuevo(): void {
    this.clienteEditando.set(null);
    this.formularioAbierto.set(true);
  }

  abrirFormularioEdicion(cliente: Cliente): void {
    this.clienteEditando.set(cliente);
    this.formularioAbierto.set(true);
  }

  cerrarFormulario(): void {
    this.formularioAbierto.set(false);
    this.clienteEditando.set(null);
  }

  alGuardar(): void {
    this.cerrarFormulario();
    this.cargarClientes();
  }

  activar(cliente: Cliente): void {
    this.cambiarEstado(cliente, 'ACTIVO');
  }

  inactivar(cliente: Cliente): void {
    this.cambiarEstado(cliente, 'INACTIVO');
  }

  private cambiarEstado(cliente: Cliente, nuevoEstado: string): void {
    this.cambiandoEstado.set(cliente.idUsuario);
    this.clienteService.cambiarEstado(cliente.idUsuario, nuevoEstado).subscribe({
      next: () => {
        this.cambiandoEstado.set(null);
        this.cargarClientes();
      },
      error: () => {
        this.cambiandoEstado.set(null);
        this.error.set('No se pudo cambiar el estado del cliente.');
      },
    });
  }

  confirmarEliminacion(cliente: Cliente): void {
    this.clienteAEliminar.set(cliente);
  }

  cancelarEliminacion(): void {
    this.clienteAEliminar.set(null);
  }

  ejecutarEliminacion(): void {
    const cliente = this.clienteAEliminar();
    if (!cliente) return;

    this.eliminando.set(true);
    this.clienteService.eliminar(cliente.idUsuario).subscribe({
      next: () => {
        this.eliminando.set(false);
        this.clienteAEliminar.set(null);
        this.cargarClientes();
      },
      error: () => {
        this.eliminando.set(false);
        this.error.set('No se pudo eliminar el cliente.');
      },
    });
  }

  setFiltro(filtro: string): void {
    this.filtroEstado.set(filtro);
  }

  claseEstado(estado: string): string {
    const clases: Record<string, string> = {
      ACTIVO: 'estado--activo',
      INACTIVO: 'estado--inactivo',
      PENDIENTE: 'estado--pendiente',
    };
    return clases[estado] ?? '';
  }
}
