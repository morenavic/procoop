import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NovedadPublicaService } from '../../../core/services/novedad-publica.service';
import { Novedad } from '../../../core/models/novedad.model';

/**
 * Componente de inicio del cliente.
 *
 * Muestra las últimas noticias y eventos de la empresa.
 * Permite navegar a "Ver más Noticias" y "Ver más Eventos".
 */
@Component({
  selector: 'app-inicio-cliente',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './inicio.html',
  styleUrl: './inicio.scss',
})
export class InicioClienteComponent implements OnInit {
  noticias = signal<Novedad[]>([]);
  eventos = signal<Novedad[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  constructor(private novedadService: NovedadPublicaService) {}

  ngOnInit(): void {
    this.cargarNovedades();
  }

  cargarNovedades(): void {
    this.novedadService.listar().subscribe({
      next: (data) => {
        // Separar y tomar las últimas 3 de cada tipo
        this.noticias.set(
          data
            .filter((n) => n.tipo === 'NOTICIA')
            .sort((a, b) => new Date(b.fecha).getTime() - new Date(a.fecha).getTime())
            .slice(0, 3),
        );
        this.eventos.set(
          data
            .filter((n) => n.tipo === 'EVENTO')
            .sort((a, b) => new Date(b.fecha).getTime() - new Date(a.fecha).getTime())
            .slice(0, 3),
        );
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las novedades.');
        this.cargando.set(false);
      },
    });
  }
}
