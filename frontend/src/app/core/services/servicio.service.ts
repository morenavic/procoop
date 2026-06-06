import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Servicio, ServicioRequest } from '../models/servicio.model';

/**
 * Servicio para la gestión de servicios del administrador.
 * Usa JSON simple — sin multipart.
 */
@Injectable({ providedIn: 'root' })
export class ServicioService {
  private readonly url = `${environment.apiUrl}/admin/servicios`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(this.url);
  }

  obtener(id: number): Observable<Servicio> {
    return this.http.get<Servicio>(`${this.url}/${id}`);
  }

  crear(datos: ServicioRequest): Observable<Servicio> {
    return this.http.post<Servicio>(this.url, datos);
  }

  editar(id: number, datos: ServicioRequest): Observable<Servicio> {
    return this.http.put<Servicio>(`${this.url}/${id}`, datos);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
