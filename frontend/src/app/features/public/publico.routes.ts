import { Routes } from '@angular/router';

export const publicoRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('../../layout/publico/publico-layout/publico-layout').then(
        m => m.PublicoLayoutComponent
      ),
    children: [
      {
        path: 'inicio',
        loadComponent: () =>
          import('./inicio/inicio').then(
            m => m.Inicio
          )
      },
      {
        path: 'empresa',
        loadComponent: () =>
          import('./empresa/empresa').then(
            m => m.Empresa
          )
      },
      {
        path: 'productos',
        loadComponent: () =>
          import('./productos/productos').then(
            m => m.Productos
          )
      },
      {
        path: 'servicios',
        loadComponent: () =>
          import('./servicios/servicios').then(
            m => m.Servicios
          )
      },
      {
        path: 'contacto',
        loadComponent: () =>
          import('./contacto/contacto').then(
            m => m.Contacto
          )
      },
      {
        path: '',
        redirectTo: 'inicio',
        pathMatch: 'full'
      }
    ]
  }
];
