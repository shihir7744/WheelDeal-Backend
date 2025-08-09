package com.carrental.backend.controller;

import com.carrental.backend.dto.DashboardStatsDto;
import com.carrental.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Get admin dashboard statistics
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardStatsDto> getAdminDashboard() {
        DashboardStatsDto stats = dashboardService.getAdminDashboardStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get manager dashboard statistics for a specific branch
     */
    @GetMapping("/manager/{branchId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<DashboardStatsDto> getManagerDashboard(@PathVariable Long branchId) {
        DashboardStatsDto stats = dashboardService.getManagerDashboardStats(branchId);
        return ResponseEntity.ok(stats);
    }

    /**
     * Get public dashboard statistics for home page
     */
    @GetMapping("/public")
    public ResponseEntity<DashboardStatsDto> getPublicDashboard() {
        DashboardStatsDto stats = dashboardService.getAdminDashboardStats();
        return ResponseEntity.ok(stats);
    }
} 