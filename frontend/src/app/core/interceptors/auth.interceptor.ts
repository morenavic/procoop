import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

/**
 * Interceptor funcional HTTP.
 * Adjunta automáticamente el token JWT en el header Authorization
 * de todas las peticiones salientes hacia la API.
 *
 * Si no hay token activo (usuario no autenticado), la petición
 * se envía sin modificaciones.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.obtenerToken();

  if (token) {
    const reqAutenticada = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    });
    return next(reqAutenticada);
  }

  return next(req);
};
