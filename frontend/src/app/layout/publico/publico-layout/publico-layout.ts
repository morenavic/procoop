import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

/**
 * Layout del sitio público (CU01-CU09).
 * Contiene navbar superior y footer con redes sociales.
 * Las pantallas públicas se renderizan en el router-outlet central.
 */
@Component({
  selector: 'app-publico-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './publico-layout.html',
  styleUrl: './publico-layout.scss',
})
export class PublicoLayoutComponent {
  menuAbierto = false;
  anioActual = new Date().getFullYear();

  toggleMenu(): void {
    this.menuAbierto = !this.menuAbierto;
  }

  cerrarMenu(): void {
    this.menuAbierto = false;
  }
}
