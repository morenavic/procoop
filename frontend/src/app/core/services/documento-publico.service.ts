import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Documento } from '../models/documento.model';

/**
 * Servicio para consumir documentos desde el panel del cliente.
 * Soporta búsqueda por nombre y filtrado por tipo.
 */
@Injectable({ providedIn: 'root' })
export class DocumentoPublicoService {

  private readonly url = `${environment.apiUrl}/cliente/documentos`;

  constructor(private http: HttpClient) {}

  listar(buscar?: string, tipo?: string): Observable<Documento[]> {
    let params = new HttpParams();
    if (buscar && buscar.trim()) params = params.set('buscar', buscar.trim());
    if (tipo && tipo !== 'TODOS') params = params.set('tipo', tipo);
    return this.http.get<Documento[]>(this.url, { params });
  }
}
