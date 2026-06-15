import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Producto } from '../models/producto.model';

/**
 * Servicio para consumir productos desde el sitio público.
 * No requiere autenticación.
 */
@Injectable({ providedIn: 'root' })
export class ProductoPublicoService {
  private readonly url = `${environment.apiUrl}/publico/productos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.url);
  }
}
