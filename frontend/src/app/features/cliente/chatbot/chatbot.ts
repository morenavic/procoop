import { Component, signal, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatbotService, ChatMensaje } from '../../../core/services/chatbot.service';

/**
 * Componente chatbot flotante del cliente.
 * CU22 — Interactuar con IA.
 *
 * Se muestra como botón flotante en la esquina inferior derecha.
 * Al hacer click abre una ventana de chat.
 * El historial persiste durante la sesión.
 */
@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbot.html',
  styleUrl: './chatbot.scss',
})
export class ChatbotComponent implements AfterViewChecked {
  @ViewChild('mensajesContainer') mensajesContainer!: ElementRef;

  abierto = signal(false);
  cargando = signal(false);
  mensajes = signal<ChatMensaje[]>([]);
  consulta = '';

  constructor(private chatbotService: ChatbotService) {}

  ngAfterViewChecked(): void {
    this.scrollAlFinal();
  }

  toggleChat(): void {
    this.abierto.update((v) => !v);
    if (this.abierto() && this.mensajes().length === 0) {
      this.agregarMensajeBienvenida();
    }
  }

  private agregarMensajeBienvenida(): void {
    this.mensajes.update((m) => [
      ...m,
      {
        rol: 'asistente',
        texto: '¡Hola! Soy el asistente virtual de Procoop. ¿En qué puedo ayudarte hoy?',
        timestamp: new Date(),
      },
    ]);
  }

  enviar(): void {
    if (!this.consulta.trim() || this.cargando()) return;

    const textoConsulta = this.consulta.trim();
    this.consulta = '';

    // Agregar mensaje del usuario
    this.mensajes.update((m) => [
      ...m,
      {
        rol: 'usuario',
        texto: textoConsulta,
        timestamp: new Date(),
      },
    ]);

    this.cargando.set(true);

    this.chatbotService.consultar(textoConsulta).subscribe({
      next: (res) => {
        this.cargando.set(false);
        this.mensajes.update((m) => [
          ...m,
          {
            rol: 'asistente',
            texto: res.respuesta,
            timestamp: new Date(),
          },
        ]);
      },
      error: () => {
        this.cargando.set(false);
        this.mensajes.update((m) => [
          ...m,
          {
            rol: 'asistente',
            texto: 'Lo siento, no pude procesar tu consulta. Intentá nuevamente.',
            timestamp: new Date(),
          },
        ]);
      },
    });
  }

  private scrollAlFinal(): void {
    if (this.mensajesContainer) {
      const el = this.mensajesContainer.nativeElement;
      el.scrollTop = el.scrollHeight;
    }
  }
}
