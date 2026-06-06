import { Routes } from '@angular/router';
import { adminGuard } from './core/guards/admin.guard';


/**
 * Definición de rutas principales de la aplicación.
 *
 * Estructura:
 *  /acceso         → Módulo público de autenticación (login)
 *  /admin          → Panel de administración (protegido por adminGuard)
 *  /               → Redirige al login por defecto
 */
export const routes: Routes = [
  {
    path: 'acceso',
    loadChildren: () => import('./features/auth/auth.routes').then((m) => m.authRoutes),
  },
  {
    path: 'admin',
    canActivate: [adminGuard],
    loadComponent: () =>
      import('./layout/admin-layout/admin-layout').then((m) =>
        // Compatibilidad con diferentes estilos de export (named, suffixed, default)
        (m as any).AdminLayout || (m as any).AdminLayoutComponent || (m as any).default
      ),
    loadChildren: () => import('./features/admin/admin.routes').then((m) => m.adminRoutes),
  },
  {
    path: '',
    redirectTo: 'acceso/login',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: 'acceso/login',
  },
];
