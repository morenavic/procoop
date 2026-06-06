import { Component } from '@angular/core';

/**
 * Componente placeholder del dashboard.
 * Se implementará completamente en el CU28 (Acceder a Inicio).
 */
@Component({
  selector: 'app-dashboard',
  standalone: true,
  template: `
    <div style="padding:2rem">
      <h2 style="font-family:'Poppins',sans-serif;color:#0052A6">Panel de Administración</h2>
      <p style="color:#6B7280;margin-top:0.5rem">
        Bienvenido. El dashboard se implementará en el próximo paso.
      </p>
    </div>
  `,
})
export class DashboardComponent {}
