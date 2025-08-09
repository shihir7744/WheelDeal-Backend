package com.carrental.backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_records")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "maintenance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaintenanceType maintenanceType;

    @Column(name = "service_provider", nullable = false)
    private String serviceProvider;

    @Column(name = "service_date", nullable = false)
    private LocalDate serviceDate;

    @Column(name = "next_service_date")
    private LocalDate nextServiceDate;

    @Column(name = "mileage_at_service")
    private Integer mileageAtService;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "technician_notes", columnDefinition = "TEXT")
    private String technicianNotes;

    @Column(name = "parts_replaced", columnDefinition = "TEXT")
    private String partsReplaced;

    @Column(name = "labor_hours")
    private Double laborHours;

    @Column(name = "warranty_covered")
    private Boolean warrantyCovered = false;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status = MaintenanceStatus.COMPLETED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    private User performedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public MaintenanceRecord() {}

    public MaintenanceRecord(Car car, MaintenanceType maintenanceType, String serviceProvider, LocalDate serviceDate) {
        this.car = car;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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

    public User getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
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