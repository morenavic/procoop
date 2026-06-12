import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PerfilService, PerfilData } from '../../../core/services/perfil.service';

/**
 * Componente Mi Perfil del cliente.
 *
 * Muestra los datos del cliente y permite actualizar la foto de perfil.
 * CU16 — Acceder a Mi Perfil.
 */
@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './perfil.html',
  styleUrl: './perfil.scss',
})
export class PerfilComponent implements OnInit {
  perfil = signal<PerfilData | null>(null);
  cargando = signal(true);
  error = signal<string | null>(null);
  subiendoFoto = signal(false);
  exitoFoto = signal(false);

  constructor(private perfilService: PerfilService) {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil(): void {
    this.perfilService.obtener().subscribe({
      next: (data) => {
        this.perfil.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudo cargar el perfil.');
        this.cargando.set(false);
      },
    });
  }

  alSeleccionarFoto(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;

    const archivo = input.files[0];
    const formData = new FormData();
    formData.append('imagen', archivo);

    this.subiendoFoto.set(true);
    this.exitoFoto.set(false);
    this.error.set(null);

    this.perfilService.actualizarFoto(formData).subscribe({
      next: (data) => {
        this.perfil.set(data);
        this.subiendoFoto.set(false);
        this.exitoFoto.set(true);
        setTimeout(() => this.exitoFoto.set(false), 3000);
      },
      error: (err) => {
        this.subiendoFoto.set(false);
        this.error.set(err.error?.error ?? 'No se pudo actualizar la foto.');
      },
    });
  }
}
