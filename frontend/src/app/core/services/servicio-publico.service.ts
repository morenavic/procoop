import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Servicio } from '../models/servicio.model';

/**
 * Servicio para consumir servicios desde el sitio público.
 * No requiere autenticación.
 */
@Injectable({ providedIn: 'root' })
export class ServicioPublicoService {
  private readonly url = `${environment.apiUrl}/publico/servicios`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(this.url);
  }
}
