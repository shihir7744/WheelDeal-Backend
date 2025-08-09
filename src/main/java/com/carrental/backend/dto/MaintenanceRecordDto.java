package com.carrental.backend.dto;

import com.carrental.backend.entities.MaintenanceStatus;
import com.carrental.backend.entities.MaintenanceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MaintenanceRecordDto {
    
    private Long id;
    private Long carId;
    private String carBrand;
    private String carModel;
    private String carLicensePlate;
    private MaintenanceType maintenanceType;
    private String serviceProvider;
    private LocalDate serviceDate;
    private LocalDate nextServiceDate;
    private Integer mileageAtService;
    private String description;
    private BigDecimal cost;
    private String invoiceNumber;
    private String technicianNotes;
    private String partsReplaced;
    private Double laborHours;
    private Boolean warrantyCovered;
    private MaintenanceStatus status;
    private Long performedById;
    private String performedByFullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public MaintenanceRecordDto() {}

    public MaintenanceRecordDto(Long id, Long carId, String carBrand, String carModel, String carLicensePlate,
                               MaintenanceType maintenanceType, String serviceProvider, LocalDate serviceDate) {
        this.id = id;
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carLicensePlate = carLicensePlate;
        this.maintenanceType = maintenanceType;
        this.serviceProvider = serviceProvider;
        this.serviceDate = serviceDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
    }

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public LocalDate getNextServiceDate() {
        return nextServiceDate;
    }

    public void setNextServiceDate(LocalDate nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }

    public Integer getMileageAtService() {
        return mileageAtService;
    }

    public void setMileageAtService(Integer mileageAtService) {
        this.mileageAtService = mileageAtService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTechnicianNotes() {
        return technicianNotes;
    }

    public void setTechnicianNotes(String technicianNotes) {
        this.technicianNotes = technicianNotes;
    }

    public String getPartsReplaced() {
        return partsReplaced;
    }

    public void setPartsReplaced(String partsReplaced) {
        this.partsReplaced = partsReplaced;
    }

    public Double getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(Double laborHours) {
        this.laborHours = laborHours;
    }

    public Boolean getWarrantyCovered() {
        return warrantyCovered;
    }

    public void setWarrantyCovered(Boolean warrantyCovered) {
        this.warrantyCovered = warrantyCovered;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }

    public Long getPerformedById() {
        return performedById;
    }

    public void setPerformedById(Long performedById) {
        this.performedById = performedById;
    }

    public String getPerformedByFullName() {
        return performedByFullName;
    }

    public void setPerformedByFullName(String performedByFullName) {
        this.performedByFullName = performedByFullName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 