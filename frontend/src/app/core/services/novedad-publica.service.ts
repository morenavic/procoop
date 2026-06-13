import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Novedad } from '../models/novedad.model';

/**
 * Servicio para consumir novedades desde el panel del cliente.
 */
@Injectable({ providedIn: 'root' })
export class NovedadPublicaService {

  private readonly url = `${environment.apiUrl}/cliente/novedades`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Novedad[]> {
    return this.http.get<Novedad[]>(this.url);
  }

  listarNoticias(): Observable<Novedad[]> {
    return this.http.get<Novedad[]>(`${this.url}/noticias`);
  }

  listarEventos(): Observable<Novedad[]> {
    return this.http.get<Novedad[]>(`${this.url}/eventos`);
  }
}
