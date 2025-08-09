package com.carrental.backend.dto;

import com.carrental.backend.entities.PricingRule;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class PricingRuleDto {
    private Long id;

    @NotBlank(message = "Rule name is required")
    private String ruleName;

    private String description;

    @NotNull(message = "Rule type is required")
    private PricingRule.PricingRuleType ruleType;

    @NotNull(message = "Adjustment type is required")
    private PricingRule.AdjustmentType adjustmentType;

    @NotNull(message = "Adjustment value is required")
    @DecimalMin(value = "0.0", message = "Adjustment value must be positive")
    private BigDecimal adjustmentValue;

    private Integer minDays;
    private Integer maxDays;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String startTime;
    private String endTime;
    private DayOfWeek dayOfWeek;
    private Boolean isWeekend;
    private Boolean isHoliday;
    private Integer minOccupancyPercentage;
    private Integer maxOccupancyPercentage;
    private String weatherCondition;
    private String eventType;
    private Integer priority;
    private Boolean isActive;
    private Long carId;
    private Long branchId;
    private Long createdById;

    // Constructors
    public PricingRuleDto() {}

    public PricingRuleDto(String ruleName, PricingRule.PricingRuleType ruleType, 
                         PricingRule.AdjustmentType adjustmentType, BigDecimal adjustmentValue) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
        this.adjustmentType = adjustmentType;
        this.adjustmentValue = adjustmentValue;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PricingRule.PricingRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(PricingRule.PricingRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public PricingRule.AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(PricingRule.AdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public BigDecimal getAdjustmentValue() {
        return adjustmentValue;
    }

    public void setAdjustmentValue(BigDecimal adjustmentValue) {
        this.adjustmentValue = adjustmentValue;
    }

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    public Integer getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public Integer getMinOccupancyPercentage() {
        return minOccupancyPercentage;
    }

    public void setMinOccupancyPercentage(Integer minOccupancyPercentage) {
        this.minOccupancyPercentage = minOccupancyPercentage;
    }

    public Integer getMaxOccupancyPercentage() {
        return maxOccupancyPercentage;
    }

    public void setMaxOccupancyPercentage(Integer maxOccupancyPercentage) {
        this.maxOccupancyPercentage = maxOccupancyPercentage;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }
} 