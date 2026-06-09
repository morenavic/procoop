import { Routes } from '@angular/router';

export const clienteRoutes: Routes = [
  {
    path: 'inicio',
    loadComponent: () => import('./inicio/inicio').then((m) => m.InicioClienteComponent),
  },
  {
    path: '',
    redirectTo: 'inicio',
    pathMatch: 'full',
  },
];
