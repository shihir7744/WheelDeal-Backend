package com.carrental.backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pricing_calculations")
public class PricingCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "calculation_date", nullable = false)
    private LocalDate calculationDate;

    @Column(name = "base_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal basePrice;

    @Column(name = "final_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal finalPrice;

    @Column(name = "total_adjustment", precision = 10, scale = 2)
    private BigDecimal totalAdjustment;

    @Column(name = "adjustment_percentage", precision = 5, scale = 2)
    private BigDecimal adjustmentPercentage;

    @Column(name = "applied_rules", columnDefinition = "TEXT")
    private String appliedRules; // JSON string of applied rules

    @Column(name = "calculation_factors", columnDefinition = "TEXT")
    private String calculationFactors; // JSON string of factors considered

    @Column(name = "occupancy_rate")
    private Double occupancyRate;

    @Column(name = "demand_level")
    private String demandLevel; // LOW, MEDIUM, HIGH, PEAK

    @Column(name = "season")
    private String season;

    @Column(name = "is_weekend")
    private Boolean isWeekend;

    @Column(name = "is_holiday")
    private Boolean isHoliday;

    @Column(name = "weather_condition")
    private String weatherCondition;

    @Column(name = "event_nearby")
    private String eventNearby;

    @Column(name = "days_until_booking")
    private Integer daysUntilBooking;

    @Column(name = "rental_duration_days")
    private Integer rentalDurationDays;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public PricingCalculation() {}

    public PricingCalculation(Car car, LocalDate calculationDate, BigDecimal basePrice, BigDecimal finalPrice) {
        this.car = car;
        this.calculationDate = calculationDate;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
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

    public LocalDate getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(LocalDate calculationDate) {
        this.calculationDate = calculationDate;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BigDecimal getTotalAdjustment() {
        return totalAdjustment;
    }

    public void setTotalAdjustment(BigDecimal totalAdjustment) {
        this.totalAdjustment = totalAdjustment;
    }

    public BigDecimal getAdjustmentPercentage() {
        return adjustmentPercentage;
    }

    public void setAdjustmentPercentage(BigDecimal adjustmentPercentage) {
        this.adjustmentPercentage = adjustmentPercentage;
    }

    public String getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(String appliedRules) {
        this.appliedRules = appliedRules;
    }

    public String getCalculationFactors() {
        return calculationFactors;
    }

    public void setCalculationFactors(String calculationFactors) {
        this.calculationFactors = calculationFactors;
    }

    public Double getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(Double occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public String getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(String demandLevel) {
        this.demandLevel = demandLevel;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
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

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getEventNearby() {
        return eventNearby;
    }

    public void setEventNearby(String eventNearby) {
        this.eventNearby = eventNearby;
    }

    public Integer getDaysUntilBooking() {
        return daysUntilBooking;
    }

    public void setDaysUntilBooking(Integer daysUntilBooking) {
        this.daysUntilBooking = daysUntilBooking;
    }

    public Integer getRentalDurationDays() {
        return rentalDurationDays;
    }

    public void setRentalDurationDays(Integer rentalDurationDays) {
        this.rentalDurationDays = rentalDurationDays;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 