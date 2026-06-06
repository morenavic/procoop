/**
 * Modelos de dominio para el usuario autenticado.
 * Se usan en el servicio de autenticación y en los guards.
 */

export interface UsuarioAutenticado {
  token: string;
  nombreUsuario: string;
  email: string;
  rol: 'ADMINISTRADOR' | 'CLIENTE';
}

export interface LoginRequest {
  email: string;
  contrasenia: string;
}
