import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Documento } from '../models/documento.model';

/**
 * Servicio para la gestión de documentos del administrador.
 * Usa multipart/form-data para enviar datos y archivo juntos.
 */
@Injectable({ providedIn: 'root' })
export class DocumentoService {
  private readonly url = `${environment.apiUrl}/admin/documentos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Documento[]> {
    return this.http.get<Documento[]>(this.url);
  }

  obtener(id: number): Observable<Documento> {
    return this.http.get<Documento>(`${this.url}/${id}`);
  }

  crear(datos: FormData): Observable<Documento> {
    return this.http.post<Documento>(this.url, datos);
  }

  editar(id: number, datos: FormData): Observable<Documento> {
    return this.http.put<Documento>(`${this.url}/${id}`, datos);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
