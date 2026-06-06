import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Guard que protege rutas exclusivas del administrador.
 * Requiere sesión activa Y rol ADMINISTRADOR.
 * Redirige al login si no cumple ambas condiciones.
 */
export const adminGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.estaAutenticado() && authService.esAdmin()) {
    return true;
  }

  return router.createUrlTree(['/acceso/login']);
};
