import { Routes } from '@angular/router';

export const clienteRoutes: Routes = [
  {
    path: 'inicio',
    loadComponent: () => import('./inicio/inicio').then((m) => m.InicioClienteComponent),
  },
  {
    path: 'noticias',
    loadComponent: () => import('./noticias/noticias').then((m) => m.NoticiasComponent),
  },
  {
    path: 'eventos',
    loadComponent: () => import('./eventos/eventos').then((m) => m.EventosComponent),
  },
  {
    path: '',
    redirectTo: 'inicio',
    pathMatch: 'full',
  },
];
