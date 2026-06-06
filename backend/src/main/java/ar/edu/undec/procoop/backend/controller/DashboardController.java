package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.response.DashboardResponseDTO;
import ar.edu.undec.procoop.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST del dashboard del administrador.
 *
 * Base path: /api/admin/dashboard
 * Requiere rol ADMINISTRADOR (configurado en SecurityConfig).
 */
@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * GET /api/admin/dashboard
     * Devuelve métricas generales y actividades recientes del sistema.
     */
    @GetMapping
    public ResponseEntity<DashboardResponseDTO> obtenerDashboard() {
        return ResponseEntity.ok(dashboardService.obtenerDashboard());
    }
}