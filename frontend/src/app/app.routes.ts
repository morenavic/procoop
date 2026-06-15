import { Routes } from '@angular/router';
import { adminGuard } from './core/guards/admin.guard';
import { authGuard } from './core/guards/auth.guard';
import { clienteGuard } from './core/guards/cliente.guard';


/**
 * Definición de rutas principales de la aplicación.
 *
 * Estructura:
 *  /acceso         → Módulo público de autenticación (login)
 *  /admin          → Panel de administración (protegido por adminGuard)
 *  /               → Redirige al login por defecto
 */
export const routes: Routes = [
  // Sitio público
  {
    path: '',
    loadChildren: () => import('./features/public/publico.routes').then((m) => m.publicoRoutes),
  },

  // Autenticación
  {
    path: 'acceso',
    loadChildren: () => import('./features/auth/auth.routes').then((m) => m.authRoutes),
  },

  // Panel administrador
  {
    path: 'admin',
    canActivate: [adminGuard],
    loadComponent: () =>
      import('./layout/admin-layout/admin-layout').then(
        (m) =>
          // Compatibilidad con diferentes estilos de export (named, suffixed, default)
          (m as any).AdminLayout || (m as any).AdminLayoutComponent || (m as any).default,
      ),
    loadChildren: () => import('./features/admin/admin.routes').then((m) => m.adminRoutes),
  },

  // Panel cliente
  {
    path: 'cliente',
    canActivate: [clienteGuard],
    loadComponent: () =>
      import('./layout/cliente-layout/cliente-layout').then((m) => m.ClienteLayoutComponent),
    loadChildren: () => import('./features/cliente/cliente.routes').then((m) => m.clienteRoutes),
  },

  // Fallback
  {
    path: '**',
    redirectTo: 'acceso/login',
  },
];
