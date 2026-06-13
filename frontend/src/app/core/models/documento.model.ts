/**
 * Modelos de dominio para Documentos.
 */
export interface Documento {
  idDocumento: number;
  nombre: string;
  descripcion: string | null;
  tipo: string;
  archivoUrl: string | null;
}

export interface DocumentoRequest {
  nombre: string;
  descripcion: string | null;
  tipo: string;
}
