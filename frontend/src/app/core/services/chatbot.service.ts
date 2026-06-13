import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface ChatMensaje {
  rol: 'usuario' | 'asistente';
  texto: string;
  timestamp: Date;
}

/**
 * Servicio para el chatbot con IA.
 */
@Injectable({ providedIn: 'root' })
export class ChatbotService {
  private readonly url = `${environment.apiUrl}/cliente/chat`;

  constructor(private http: HttpClient) {}

  consultar(consulta: string): Observable<{ respuesta: string }> {
    return this.http.post<{ respuesta: string }>(this.url, { consulta });
  }
}
