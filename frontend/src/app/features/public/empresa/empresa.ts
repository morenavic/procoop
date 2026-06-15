import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

/**
 * CU01 — Consultar información de la Empresa
 * CU02 — Descargar certificado de calidad
 *
 * Datos hardcodeados en el frontend basados en procoopsrl.com.ar/empresa
 * El certificado es un PDF estático en assets/
 */
@Component({
  selector: 'app-empresa',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './empresa.html',
  styleUrl: './empresa.scss',
})
export class EmpresaComponent {
  readonly valores = [
    {
      titulo: 'Orientación al cliente',
      descripcion:
        'La satisfacción que generamos en nuestros clientes y el mantenimiento de relaciones comerciales a largo plazo son parte importante de nuestro éxito.',
    },
    {
      titulo: 'Tratamiento individual',
      descripcion:
        'Todas las soluciones que proponemos son el resultado del análisis particular de cada cliente.',
    },
    {
      titulo: 'Máxima calidad',
      descripcion:
        'El trabajo bien hecho es un valor insustituible y se convierte en una máxima en cada proyecto que abordamos.',
    },
    {
      titulo: 'Trabajo en equipo',
      descripcion:
        'Las soluciones de ProCoop se basan en el conocimiento y experiencia de sus profesionales, maximizando el valor de los negocios.',
    },
    {
      titulo: 'Evolución tecnológica',
      descripcion:
        'Aplicar soluciones tecnológicas de vanguardia requiere un proceso constante de renovación tecnológica.',
    },
  ];

  readonly sedes = [
    { ciudad: 'Córdoba', direccion: 'José Baigorri 491 - 5000 - Córdoba' },
    { ciudad: 'Monte Maíz', direccion: 'Buenos Aires 1507 - X2659AKG - Monte Maíz' },
  ];

  descargarCertificado(): void {
    const link = document.createElement('a');
    link.href = 'certificado-procoopsrl-2014.pdf';
    link.download = 'Certificado-Calidad-Procoop.pdf';
    link.click();
  }
}
