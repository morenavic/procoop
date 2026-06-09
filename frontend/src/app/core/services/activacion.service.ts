import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

/**
 * Modelo para la solicitud de activación de cuenta.
 */
export interface ActivacionRequest {
  nombre: string;
  email: string;
  contrasenia: string;
  numeroCuenta: string;
}

/**
 * Servicio para la activación de cuenta del usuario cliente.
 */
@Injectable({ providedIn: 'root' })
export class ActivacionService {

  private readonly url = `${environment.apiUrl}/auth/activar`;

  constructor(private http: HttpClient) {}

  activar(datos: ActivacionRequest): Observable<void> {
    return this.http.post<void>(this.url, datos);
  }
}
