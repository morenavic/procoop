/**
 * Modelos de dominio para Servicios.
 */
export interface Servicio {
  idServicio: number;
  titulo: string;
  descripcion: string;
}

export interface ServicioRequest {
  titulo: string;
  descripcion: string;
}
