import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductoPublicoService } from '../../../core/services/producto-publico.service';
import { ServicioPublicoService } from '../../../core/services/servicio-publico.service';
import { Producto } from '../../../core/models/producto.model';
import { Servicio } from '../../../core/models/servicio.model';
import { environment } from '../../../environments/environment';

/**
 * Pantalla de inicio del sitio público.
 * Muestra un resumen de productos y servicios destacados
 * junto con secciones institucionales hardcodeadas.
 */
@Component({
  selector: 'app-inicio-publico',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './inicio.html',
  styleUrl: './inicio.scss',
})
export class InicioPublicoComponent implements OnInit {
  productos = signal<Producto[]>([]);
  servicios = signal<Servicio[]>([]);

  readonly razones = [
    {
      titulo: '+20 años de experiencia',
      descripcion:
        'Más de 20 años trabajando con cooperativas nos dan el conocimiento necesario para entender tu negocio.',
    },
    {
      titulo: 'Soluciones a medida',
      descripcion:
        'Cada solución es el resultado del análisis particular de cada cliente.',
    },
    {
      titulo: 'Socios tecnológicos',
      descripcion:
        'No somos solo proveedores, somos socios que acompañan el crecimiento de tu cooperativa.',
    },
    {
      titulo: 'Calidad garantizada',
      descripcion:
        'Contamos con certificación de calidad y un compromiso permanente con la mejora continua.',
    },
  ];

  constructor(
    private productoService: ProductoPublicoService,
    private servicioService: ServicioPublicoService,
  ) {}

  ngOnInit(): void {
    this.productoService.listar().subscribe({
      next: (data) => this.productos.set(data.slice(0, 3)),
    });

    this.servicioService.listar().subscribe({
      next: (data) => this.servicios.set(data.slice(0, 3)),
    });
  }

  imagenUrl(imagen: string | null): string {
    if (!imagen) return '';
    return `${environment.uploadsUrl}/uploads/${imagen}`;
  }
}
