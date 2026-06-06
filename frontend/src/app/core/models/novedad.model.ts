/**
 * Modelos de dominio para Novedades.
 */

export interface Novedad {
  idNovedad: number;
  titulo: string;
  descripcion: string;
  fecha: string;
  imagenUrl: string | null;
  tipo: 'NOTICIA' | 'EVENTO';
}

export interface NovedadRequest {
  titulo: string;
  descripcion: string;
  fecha: string;
  tipo: string;
}
