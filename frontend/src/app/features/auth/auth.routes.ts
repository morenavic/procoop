import { Routes } from '@angular/router';

/**
 * Rutas del módulo de autenticación.
 */
export const authRoutes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./login/login').then((m) => m.LoginComponent),
  },
  {
    path: 'activar',
    loadComponent: () => import('./activacion/activacion').then((m) => m.ActivacionComponent),
  },
  {
    path: 'recuperar',
    loadComponent: () =>
      import('./recuperar/recuperar').then((m) => m.RecuperarComponent),
  },
  {
    path: 'restablecer',
    loadComponent: () =>
      import('./restablecer/restablecer').then((m) => m.RestablecerComponent),
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
];
