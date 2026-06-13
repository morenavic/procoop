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
    path: 'perfil',
    loadComponent: () => import('./perfil/perfil').then((m) => m.PerfilComponent),
  },
  {
    path: 'cambiar-contrasenia',
    loadComponent: () =>
      import('./cambiar-contrasenia/cambiar-contrasenia').then(
        (m) => m.CambiarContraseniaComponent,
      ),
  },
  {
    path: 'documentos',
    loadComponent: () =>
      import('./documentos-cliente/documentos-cliente').then(
        (m) => m.DocumentosClienteComponent,
      ),
  },
  {
    path: '',
    redirectTo: 'inicio',
    pathMatch: 'full',
  },
];
