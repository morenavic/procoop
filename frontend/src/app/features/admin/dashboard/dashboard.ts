import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService, DashboardData } from '../../../core/services/dashboard.service';

/**
 * Componente del dashboard del administrador.
 *
 * Muestra:
 *  - Tarjetas con métricas: clientes activos, documentos, productos
 *  - Tabla de actividades recientes de todas las entidades del sistema
 *
 * Usa signals para manejar el estado de carga y los datos.
 */
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class DashboardComponent implements OnInit {
  datos = signal<DashboardData | null>(null);
  cargando = signal(true);
  error = signal<string | null>(null);

  readonly metricas = [
    {
      clave: 'totalClientesActivos',
      etiqueta: 'Clientes activos',
      icono: 'clientes',
      color: 'azul',
    },
    {
      clave: 'totalProductos',
      etiqueta: 'Productos',
      icono: 'productos',
      color: 'verde',
    },
    {
      clave: 'totalDocumentos',
      etiqueta: 'Documentos',
      icono: 'documentos',
      color: 'naranja',
    },
  ];

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.obtenerDashboard().subscribe({
      next: (data) => {
        this.datos.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los datos del dashboard.');
        this.cargando.set(false);
      },
    });
  }

  obtenerValorMetrica(clave: string): number {
    const datos = this.datos();
    if (!datos) return 0;
    return (datos as any)[clave] ?? 0;
  }

  obtenerColorTipo(tipo: string): string {
    const colores: Record<string, string> = {
      Novedad: 'etiqueta--novedad',
      Producto: 'etiqueta--producto',
      Servicio: 'etiqueta--servicio',
      Documento: 'etiqueta--documento',
      Cliente: 'etiqueta--cliente',
    };
    return colores[tipo] ?? 'etiqueta--default';
  }
}
