import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductoPublicoService } from '../../../core/services/producto-publico.service';
import { Producto } from '../../../core/models/producto.model';

/**
 * CU03 — Consultar Productos (públicos)
 * CU04 — Ver más información de un producto (modal)
 *
 * Muestra los últimos 6 productos por defecto.
 * "Ver más" expande el listado completo.
 * Click en un producto abre modal con detalle.
 */
@Component({
  selector: 'app-productos-publico',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './productos.html',
  styleUrl: './productos.scss',
})
export class ProductosPublicoComponent implements OnInit {
  readonly LIMITE_INICIAL = 6;

  todos = signal<Producto[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);
  mostrarTodos = signal(false);
  productoSeleccionado = signal<Producto | null>(null);

  constructor(private productoService: ProductoPublicoService) {}

  ngOnInit(): void {
    this.productoService.listar().subscribe({
      next: (data) => {
        this.todos.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los productos.');
        this.cargando.set(false);
      },
    });
  }

  get productosMostrados(): Producto[] {
    return this.mostrarTodos() ? this.todos() : this.todos().slice(0, this.LIMITE_INICIAL);
  }

  get hayMas(): boolean {
    return this.todos().length > this.LIMITE_INICIAL;
  }

  abrirModal(producto: Producto): void {
    this.productoSeleccionado.set(producto);
    document.body.style.overflow = 'hidden';
  }

  cerrarModal(): void {
    this.productoSeleccionado.set(null);
    document.body.style.overflow = '';
  }

  imagenUrl(imagen: string | null): string {
    if (!imagen) return '';
    return `http://localhost:8080/uploads/${imagen}`;
  }
}
