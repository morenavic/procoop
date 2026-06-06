/**
 * Modelos de dominio para Clientes.
 */
export type EstadoCliente = 'ACTIVO' | 'INACTIVO' | 'PENDIENTE';

export interface Cliente {
  idUsuario: number;
  nombre: string;
  email: string;
  numeroCuenta: string;
  estado: EstadoCliente;
}

export interface ClienteRequest {
  nombre: string;
  email: string;
  numeroCuenta: string;
}
