import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Guard que protege rutas exclusivas del cliente.
 * Requiere sesión activa Y rol CLIENTE.
 */
export const clienteGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.estaAutenticado() &&
      authService.usuarioActual()?.rol === 'CLIENTE') {
    return true;
  }

  return router.createUrlTree(['/acceso/login']);
};
