import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

import { LoginRequest, UsuarioAutenticado } from '../models/usuario.model';
import { environment } from '../../environments/environment';

/**
 * Servicio central de autenticación.
 *
 * Responsabilidades:
 *  - Ejecutar el login contra la API
 *  - Persistir el token y datos del usuario en localStorage
 *  - Exponer señales reactivas del estado de sesión
 *  - Proveer lógica de cierre de sesión
 *
 * Usa Signals de Angular para que los componentes reaccionen
 * automáticamente a cambios en el estado de autenticación.
 */
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly CLAVE_SESION = 'procoop_sesion';
  private readonly urlAuth = `${environment.apiUrl}/auth`;

  // Signal privado — fuente de verdad del estado de sesión
  private _usuarioActual = signal<UsuarioAutenticado | null>(this.cargarSesionGuardada());

  // Señales públicas de solo lectura
  readonly usuarioActual = this._usuarioActual.asReadonly();
  readonly estaAutenticado = computed(() => this._usuarioActual() !== null);
  readonly esAdmin = computed(() => this._usuarioActual()?.rol === 'ADMINISTRADOR');

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  /**
   * Envía las credenciales al backend y guarda la sesión si es exitoso.
   */
  login(credenciales: LoginRequest): Observable<UsuarioAutenticado> {
    return this.http.post<UsuarioAutenticado>(`${this.urlAuth}/login`, credenciales).pipe(
      tap((respuesta) => {
        this._usuarioActual.set(respuesta);
        localStorage.setItem(this.CLAVE_SESION, JSON.stringify(respuesta));
      }),
    );
  }

  /**
   * Cierra la sesión: limpia el estado y redirige al login.
   */
  cerrarSesion(): void {
    this._usuarioActual.set(null);
    localStorage.removeItem(this.CLAVE_SESION);
    this.router.navigate(['/acceso/login']);
  }

  /**
   * Obtiene el token JWT para incluirlo en las peticiones HTTP.
   */
  obtenerToken(): string | null {
    return this._usuarioActual()?.token ?? null;
  }

  /**
   * Recupera la sesión guardada en localStorage al iniciar la app.
   */
  private cargarSesionGuardada(): UsuarioAutenticado | null {
    try {
      const datos = localStorage.getItem(this.CLAVE_SESION);
      return datos ? JSON.parse(datos) : null;
    } catch {
      return null;
    }
  }
}
