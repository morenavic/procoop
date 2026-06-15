import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface ContactoRequest {
  nombre: string;
  email: string;
  telefono: string;
  asunto: string;
  mensaje: string;
}

/**
 * Servicio para enviar el formulario de contacto público.
 */
@Injectable({ providedIn: 'root' })
export class ContactoService {
  private readonly url = `${environment.apiUrl}/publico/contacto`;

  constructor(private http: HttpClient) {}

  enviar(datos: ContactoRequest): Observable<void> {
    return this.http.post<void>(this.url, datos);
  }
}
