import { Routes } from '@angular/router';

/**
 * Rutas del panel de administración.
 * Todas heredan el guard adminGuard definido en el padre (app.routes.ts).
 * Se irán completando caso de uso por caso de uso.
 */
export const adminRoutes: Routes = [
  {
    path: 'inicio',
    loadComponent: () =>
      import('./dashboard/dashboard').then(m => m.DashboardComponent)
  },
  {
    path: '',
    redirectTo: 'inicio',
    pathMatch: 'full'
  }
];
