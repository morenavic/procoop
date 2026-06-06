/**
 * Modelos de dominio para Productos.
 */
export interface Producto {
  idProducto: number;
  titulo: string;
  subtitulo: string | null;
  descripcion: string;
  imagenUrl: string | null;
}

export interface ProductoRequest {
  titulo: string;
  subtitulo: string | null;
  descripcion: string;
}
