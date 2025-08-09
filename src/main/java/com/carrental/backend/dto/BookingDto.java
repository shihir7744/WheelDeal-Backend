package com.carrental.backend.dto;

import com.carrental.backend.entities.BookingStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingDto {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;
    
    private String userFullName;
    private String userEmail;

    @NotNull(message = "Car ID is required")
    private Long carId;
    
    private String carBrand;
    private String carModel;
    private String carType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private BookingStatus status;
    private String adminNotes;
    private LocalDateTime confirmedAt;
    private String confirmedByFullName;
    private LocalDateTime createdAt;

    // Constructors
    public BookingDto() {}

    public BookingDto(Long id, Long userId, String userFullName, String userEmail,
                      Long carId, String carBrand, String carModel, String carType,
                      LocalDate startDate, LocalDate endDate, BookingStatus status,
                      String adminNotes, LocalDateTime confirmedAt, String confirmedByFullName,
                      LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carType = carType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.adminNotes = adminNotes;
        this.confirmedAt = confirmedAt;
        this.confirmedByFullName = confirmedByFullName;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public String getConfirmedByFullName() {
        return confirmedByFullName;
    }

    public void setConfirmedByFullName(String confirmedByFullName) {
        this.confirmedByFullName = confirmedByFullName;
    }
}

