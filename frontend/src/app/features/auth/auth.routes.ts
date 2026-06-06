import { Routes } from '@angular/router';

/**
 * Rutas del módulo de autenticación.
 */
export const authRoutes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./login/login').then(m => m.LoginComponent)
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];
