import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NovedadPublicaService } from '../../../core/services/novedad-publica.service';
import { Novedad } from '../../../core/models/novedad.model';

/**
 * Componente de histórico completo de noticias.
 * CU14 — Ver más Noticias.
 */
@Component({
  selector: 'app-noticias',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './noticias.html',
  styleUrl: './noticias.scss',
})
export class NoticiasComponent implements OnInit {
  noticias = signal<Novedad[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  constructor(private novedadService: NovedadPublicaService) {}

  ngOnInit(): void {
    this.novedadService.listarNoticias().subscribe({
      next: (data) => {
        this.noticias.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las noticias.');
        this.cargando.set(false);
      },
    });
  }
}
