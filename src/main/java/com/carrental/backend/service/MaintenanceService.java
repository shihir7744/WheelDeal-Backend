package com.carrental.backend.service;

import com.carrental.backend.dto.MaintenanceRecordDto;
import com.carrental.backend.entities.Car;
import com.carrental.backend.entities.MaintenanceRecord;
import com.carrental.backend.entities.MaintenanceStatus;
import com.carrental.backend.entities.MaintenanceType;
import com.carrental.backend.entities.User;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.repository.CarRepository;
import com.carrental.backend.repository.MaintenanceRecordRepository;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRecordRepository maintenanceRecordRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    // Create new maintenance record
    public MaintenanceRecordDto createMaintenanceRecord(MaintenanceRecordDto maintenanceRecordDto) {
        Car car = carRepository.findById(maintenanceRecordDto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", maintenanceRecordDto.getCarId()));

        User performedBy = null;
        if (maintenanceRecordDto.getPerformedById() != null) {
            performedBy = userRepository.findById(maintenanceRecordDto.getPerformedById()).orElse(null);
        }

        MaintenanceRecord maintenanceRecord = new MaintenanceRecord();
        maintenanceRecord.setCar(car);
        maintenanceRecord.setMaintenanceType(maintenanceRecordDto.getMaintenanceType());
        maintenanceRecord.setServiceProvider(maintenanceRecordDto.getServiceProvider());
        maintenanceRecord.setServiceDate(maintenanceRecordDto.getServiceDate());
        maintenanceRecord.setNextServiceDate(maintenanceRecordDto.getNextServiceDate());
        maintenanceRecord.setMileageAtService(maintenanceRecordDto.getMileageAtService());
        maintenanceRecord.setDescription(maintenanceRecordDto.getDescription());
        maintenanceRecord.setCost(maintenanceRecordDto.getCost());
        maintenanceRecord.setInvoiceNumber(maintenanceRecordDto.getInvoiceNumber());
        maintenanceRecord.setTechnicianNotes(maintenanceRecordDto.getTechnicianNotes());
        maintenanceRecord.setPartsReplaced(maintenanceRecordDto.getPartsReplaced());
        maintenanceRecord.setLaborHours(maintenanceRecordDto.getLaborHours());
        maintenanceRecord.setWarrantyCovered(maintenanceRecordDto.getWarrantyCovered());
        maintenanceRecord.setStatus(maintenanceRecordDto.getStatus() != null ? maintenanceRecordDto.getStatus() : MaintenanceStatus.COMPLETED);
        maintenanceRecord.setPerformedBy(performedBy);

        MaintenanceRecord savedRecord = maintenanceRecordRepository.save(maintenanceRecord);
        return convertToDto(savedRecord);
    }

    // Get maintenance record by ID
    public MaintenanceRecordDto getMaintenanceRecordById(Long id) {
        MaintenanceRecord maintenanceRecord = maintenanceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRecord", "id", id));
        return convertToDto(maintenanceRecord);
    }

    // Get all maintenance records for a car
    public List<MaintenanceRecordDto> getMaintenanceRecordsByCarId(Long carId) {
        List<MaintenanceRecord> records = maintenanceRecordRepository.findByCarIdOrderByServiceDateDesc(carId);
        return records.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Get overdue maintenance
    public List<MaintenanceRecordDto> getOverdueMaintenance() {
        List<MaintenanceRecord> records = maintenanceRecordRepository.findOverdueMaintenance(LocalDate.now());
        return records.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Convert entity to DTO
    private MaintenanceRecordDto convertToDto(MaintenanceRecord maintenanceRecord) {
        MaintenanceRecordDto dto = new MaintenanceRecordDto();
        dto.setId(maintenanceRecord.getId());
        dto.setCarId(maintenanceRecord.getCar().getId());
        dto.setCarBrand(maintenanceRecord.getCar().getBrand());
        dto.setCarModel(maintenanceRecord.getCar().getModel());
        dto.setCarLicensePlate("N/A"); // License plate not available in current Car entity
        dto.setMaintenanceType(maintenanceRecord.getMaintenanceType());
        dto.setServiceProvider(maintenanceRecord.getServiceProvider());
        dto.setServiceDate(maintenanceRecord.getServiceDate());
        dto.setNextServiceDate(maintenanceRecord.getNextServiceDate());
        dto.setMileageAtService(maintenanceRecord.getMileageAtService());
        dto.setDescription(maintenanceRecord.getDescription());
        dto.setCost(maintenanceRecord.getCost());
        dto.setInvoiceNumber(maintenanceRecord.getInvoiceNumber());
        dto.setTechnicianNotes(maintenanceRecord.getTechnicianNotes());
        dto.setPartsReplaced(maintenanceRecord.getPartsReplaced());
        dto.setLaborHours(maintenanceRecord.getLaborHours());
        dto.setWarrantyCovered(maintenanceRecord.getWarrantyCovered());
        dto.setStatus(maintenanceRecord.getStatus());
        
        if (maintenanceRecord.getPerformedBy() != null) {
            dto.setPerformedById(maintenanceRecord.getPerformedBy().getId());
            dto.setPerformedByFullName(maintenanceRecord.getPerformedBy().getFullName());
        }
        
        dto.setCreatedAt(maintenanceRecord.getCreatedAt());
        dto.setUpdatedAt(maintenanceRecord.getUpdatedAt());
        
        return dto;
    }
} 