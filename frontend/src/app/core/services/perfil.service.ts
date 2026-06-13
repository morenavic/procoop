import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface PerfilData {
  idUsuario: number;
  nombre: string;
  email: string;
  numeroCuenta: string;
  imagenUrl: string | null;
}

export interface CambiarContraseniaRequest {
  contraseniaActual: string;
  contraseniaNueva: string;
  confirmacion: string;
}

/**
 * Servicio para el perfil del cliente autenticado.
 */
@Injectable({ providedIn: 'root' })
export class PerfilService {

  private readonly url = `${environment.apiUrl}/cliente/perfil`;

  constructor(private http: HttpClient) {}

  obtener(): Observable<PerfilData> {
    return this.http.get<PerfilData>(this.url);
  }

  actualizarFoto(formData: FormData): Observable<PerfilData> {
    return this.http.patch<PerfilData>(`${this.url}/foto`, formData);
  }

  cambiarContrasenia(datos: CambiarContraseniaRequest): Observable<void> {
    return this.http.patch<void>(`${this.url}/contrasenia`, datos);
  }
}
