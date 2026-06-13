import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface RecuperarRequest {
  email: string;
  numeroCuenta: string;
}

export interface RestablecerRequest {
  token: string;
  contraseniaNueva: string;
  confirmacion: string;
}

/**
 * Servicio para recuperación y restablecimiento de contraseña.
 */
@Injectable({ providedIn: 'root' })
export class RecuperacionService {

  private readonly url = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  recuperar(datos: RecuperarRequest): Observable<void> {
    return this.http.post<void>(`${this.url}/recuperar`, datos);
  }

  restablecer(datos: RestablecerRequest): Observable<void> {
    return this.http.post<void>(`${this.url}/restablecer`, datos);
  }
}
