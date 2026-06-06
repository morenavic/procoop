import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Producto } from '../models/producto.model';

/**
 * Servicio para la gestión de productos del administrador.
 * Usa multipart/form-data para enviar datos e imagen juntos.
 */
@Injectable({ providedIn: 'root' })
export class ProductoService {
  private readonly url = `${environment.apiUrl}/admin/productos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.url);
  }

  obtener(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.url}/${id}`);
  }

  crear(datos: FormData): Observable<Producto> {
    return this.http.post<Producto>(this.url, datos);
  }

  editar(id: number, datos: FormData): Observable<Producto> {
    return this.http.put<Producto>(`${this.url}/${id}`, datos);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
