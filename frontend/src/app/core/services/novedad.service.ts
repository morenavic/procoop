import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Novedad } from '../models/novedad.model';

/**
 * Servicio para la gestión de novedades del administrador.
 * Usa multipart/form-data para enviar datos e imagen juntos.
 */
@Injectable({ providedIn: 'root' })
export class NovedadService {
  private readonly url = `${environment.apiUrl}/admin/novedades`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Novedad[]> {
    return this.http.get<Novedad[]>(this.url);
  }

  obtener(id: number): Observable<Novedad> {
    return this.http.get<Novedad>(`${this.url}/${id}`);
  }

  crear(datos: FormData): Observable<Novedad> {
    return this.http.post<Novedad>(this.url, datos);
  }

  editar(id: number, datos: FormData): Observable<Novedad> {
    return this.http.put<Novedad>(`${this.url}/${id}`, datos);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
