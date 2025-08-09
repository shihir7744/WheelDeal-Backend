package com.carrental.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class DynamicPricingRequestDto {
    @NotNull(message = "Car ID is required")
    private Long carId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Positive(message = "Rental duration must be positive")
    private Integer rentalDurationDays;

    private String weatherCondition;
    private String eventType;
    private Boolean isWeekend;
    private Boolean isHoliday;
    private Integer occupancyRate;
    private String demandLevel;
    private Integer daysUntilBooking;

    // Constructors
    public DynamicPricingRequestDto() {}

    public DynamicPricingRequestDto(Long carId, LocalDate startDate, LocalDate endDate) {
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public Integer getRentalDurationDays() {
        return rentalDurationDays;
    }

    public void setRentalDurationDays(Integer rentalDurationDays) {
        this.rentalDurationDays = rentalDurationDays;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Boolean getIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(Boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    public Boolean getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    public Integer getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(Integer occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public String getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(String demandLevel) {
        this.demandLevel = demandLevel;
    }

    public Integer getDaysUntilBooking() {
        return daysUntilBooking;
    }

    public void setDaysUntilBooking(Integer daysUntilBooking) {
        this.daysUntilBooking = daysUntilBooking;
    }
} 