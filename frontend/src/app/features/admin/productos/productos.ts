import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductoService } from '../../../core/services/producto.service';
import { Producto } from '../../../core/models/producto.model';
import { ProductoFormComponent } from './producto-form/producto-form';

/**
 * Componente principal de gestión de productos.
 *
 * Muestra el listado de productos con acciones para:
 *  - Crear nuevo producto (abre panel lateral)
 *  - Editar producto existente (abre panel con datos precargados)
 *  - Eliminar producto (con confirmación)
 */
@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, ProductoFormComponent],
  templateUrl: './productos.html',
  styleUrls: ['./productos.scss'],
})
export class ProductosComponent implements OnInit {
  productos = signal<Producto[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  formularioAbierto = signal(false);
  productoEditando = signal<Producto | null>(null);
  productoAEliminar = signal<Producto | null>(null);
  eliminando = signal(false);

  constructor(private productoService: ProductoService) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cargando.set(true);
    this.productoService.listar().subscribe({
      next: (data) => {
        this.productos.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los productos.');
        this.cargando.set(false);
      },
    });
  }

  abrirFormularioNuevo(): void {
    this.productoEditando.set(null);
    this.formularioAbierto.set(true);
  }

  abrirFormularioEdicion(producto: Producto): void {
    this.productoEditando.set(producto);
    this.formularioAbierto.set(true);
  }

  cerrarFormulario(): void {
    this.formularioAbierto.set(false);
    this.productoEditando.set(null);
  }

  alGuardar(): void {
    this.cerrarFormulario();
    this.cargarProductos();
  }

  confirmarEliminacion(producto: Producto): void {
    this.productoAEliminar.set(producto);
  }

  cancelarEliminacion(): void {
    this.productoAEliminar.set(null);
  }

  ejecutarEliminacion(): void {
    const producto = this.productoAEliminar();
    if (!producto) return;

    this.eliminando.set(true);
    this.productoService.eliminar(producto.idProducto).subscribe({
      next: () => {
        this.eliminando.set(false);
        this.productoAEliminar.set(null);
        this.cargarProductos();
      },
      error: () => {
        this.eliminando.set(false);
        this.error.set('No se pudo eliminar el producto.');
      },
    });
  }
}
