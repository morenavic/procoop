import { Component, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../core/services/auth.service';

/**
 * Layout principal del panel del cliente.
 *
 * Estructura visual:
 *  - Topbar con logo, nombre del usuario y botón hamburguesa
 *  - Sidebar deslizante con navegación
 *  - Área de contenido con <router-outlet>
 *
 * El menú hamburguesa abre/cierra el sidebar lateral.
 */
@Component({
  selector: 'app-cliente-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cliente-layout.html',
  styleUrl: './cliente-layout.scss',
})
export class ClienteLayoutComponent {
  menuAbierto = signal(false);
  rutaActual = signal('');

  readonly usuario = computed(() => this.authService.usuarioActual());

  readonly itemsNavegacion = [
    { ruta: '/cliente/inicio', etiqueta: 'Inicio', icono: 'inicio' },
    { ruta: '/cliente/perfil', etiqueta: 'Mi Perfil', icono: 'perfil' },
    { ruta: '/cliente/cambiar-contrasenia', etiqueta: 'Cambiar contraseña', icono: 'contrasenia' },
    { ruta: '/cliente/documentos', etiqueta: 'Documentos', icono: 'documentos' },
  ];

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {
    this.router.events.pipe(filter((e) => e instanceof NavigationEnd)).subscribe((e: any) => {
      this.rutaActual.set(e.urlAfterRedirects);
      this.menuAbierto.set(false); // cerrar menú al navegar
    });
  }

  esRutaActiva(ruta: string): boolean {
    return this.rutaActual().startsWith(ruta);
  }

  toggleMenu(): void {
    this.menuAbierto.update((v) => !v);
  }

  cerrarMenu(): void {
    this.menuAbierto.set(false);
  }

  cerrarSesion(): void {
    this.authService.cerrarSesion();
  }
}
