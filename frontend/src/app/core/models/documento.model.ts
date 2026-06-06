/**
 * Modelos de dominio para Documentos.
 */
export interface Documento {
  idDocumento: number;
  nombre: string;
  descripcion: string | null;
  archivoUrl: string | null;
}

export interface DocumentoRequest {
  nombre: string;
  descripcion: string | null;
}
