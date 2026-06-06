import { Component, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../core/services/auth.service';

/**
 * Layout principal del panel de administración.
 *
 * Estructura visual:
 *  - Sidebar fijo con navegación principal
 *  - Topbar con nombre del usuario y botón de salir
 *  - Área de contenido con <router-outlet>
 *
 * El sidebar puede colapsarse en mobile.
 * La ruta activa se detecta automáticamente para resaltar
 * el ítem de navegación correspondiente.
 */
@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-layout.html',
  styleUrls: ['./admin-layout.scss'],
})
export class AdminLayoutComponent {
  sidebarAbierto = signal(true);
  rutaActual = signal('');

  readonly usuario = computed(() => this.authService.usuarioActual());

  readonly itemsNavegacion = [
    { ruta: '/admin/inicio', etiqueta: 'Inicio', icono: 'inicio' },
    { ruta: '/admin/novedades', etiqueta: 'Novedades', icono: 'novedades' },
    { ruta: '/admin/productos', etiqueta: 'Productos', icono: 'productos' },
    { ruta: '/admin/servicios', etiqueta: 'Servicios', icono: 'servicios' },
    { ruta: '/admin/clientes', etiqueta: 'Clientes', icono: 'clientes' },
    { ruta: '/admin/documentos', etiqueta: 'Documentos', icono: 'documentos' },
  ];

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {
    // Seguimiento de la ruta activa para resaltar el ítem del sidebar
    this.router.events
      .pipe(filter((e) => e instanceof NavigationEnd))
      .subscribe((e: any) => this.rutaActual.set(e.urlAfterRedirects));
  }

  esRutaActiva(ruta: string): boolean {
    return this.rutaActual().startsWith(ruta);
  }

  toggleSidebar(): void {
    this.sidebarAbierto.update((v) => !v);
  }

  cerrarSesion(): void {
    this.authService.cerrarSesion();
  }
}
