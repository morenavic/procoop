import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NovedadPublicaService } from '../../../core/services/novedad-publica.service';
import { Novedad } from '../../../core/models/novedad.model';

/**
 * Componente de histórico completo de eventos.
 * CU15 — Ver más Eventos.
 */
@Component({
  selector: 'app-eventos',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './eventos.html',
  styleUrl: './eventos.scss',
})
export class EventosComponent implements OnInit {
  eventos = signal<Novedad[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  constructor(private novedadService: NovedadPublicaService) {}

  ngOnInit(): void {
    this.novedadService.listarEventos().subscribe({
      next: (data) => {
        this.eventos.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los eventos.');
        this.cargando.set(false);
      },
    });
  }
}
