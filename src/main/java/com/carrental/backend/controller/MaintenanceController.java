package com.carrental.backend.controller;

import com.carrental.backend.dto.MaintenanceRecordDto;
import com.carrental.backend.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping("/records")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<MaintenanceRecordDto> createMaintenanceRecord(@Valid @RequestBody MaintenanceRecordDto maintenanceRecordDto) {
        MaintenanceRecordDto createdRecord = maintenanceService.createMaintenanceRecord(maintenanceRecordDto);
        return ResponseEntity.ok(createdRecord);
    }

    @GetMapping("/records/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<MaintenanceRecordDto> getMaintenanceRecordById(@PathVariable Long id) {
        MaintenanceRecordDto record = maintenanceService.getMaintenanceRecordById(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/cars/{carId}/records")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<MaintenanceRecordDto>> getMaintenanceRecordsByCarId(@PathVariable Long carId) {
        List<MaintenanceRecordDto> records = maintenanceService.getMaintenanceRecordsByCarId(carId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<MaintenanceRecordDto>> getOverdueMaintenance() {
        List<MaintenanceRecordDto> overdueRecords = maintenanceService.getOverdueMaintenance();
        return ResponseEntity.ok(overdueRecords);
    }
} 