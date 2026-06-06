import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Cliente, ClienteRequest } from '../models/cliente.model';

/**
 * Servicio para la gestión de clientes del administrador.
 */
@Injectable({ providedIn: 'root' })
export class ClienteService {
  private readonly url = `${environment.apiUrl}/admin/clientes`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.url);
  }

  obtener(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.url}/${id}`);
  }

  crear(datos: ClienteRequest): Observable<Cliente> {
    return this.http.post<Cliente>(this.url, datos);
  }

  editar(id: number, datos: ClienteRequest): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.url}/${id}`, datos);
  }

  cambiarEstado(id: number, estado: string): Observable<Cliente> {
    return this.http.patch<Cliente>(`${this.url}/${id}/estado`, { estado });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
