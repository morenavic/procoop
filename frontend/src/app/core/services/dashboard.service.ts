import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

/**
 * Modelos del dashboard.
 */
export interface ActividadReciente {
  tipo: string;
  titulo: string;
  descripcion: string | null;
}

export interface DashboardData {
  totalClientesActivos: number;
  totalDocumentos: number;
  totalProductos: number;
  actividadesRecientes: ActividadReciente[];
}

/**
 * Servicio para obtener los datos del dashboard del administrador.
 */
@Injectable({ providedIn: 'root' })
export class DashboardService {
  private readonly url = `${environment.apiUrl}/admin/dashboard`;

  constructor(private http: HttpClient) {}

  obtenerDashboard(): Observable<DashboardData> {
    return this.http.get<DashboardData>(this.url);
  }
}
