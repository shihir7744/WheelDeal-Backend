package com.carrental.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Entity
@Table(name = "pricing_rules")
public class PricingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rule_name", nullable = false)
    private String ruleName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false)
    private PricingRuleType ruleType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "adjustment_type", nullable = false)
    private AdjustmentType adjustmentType;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "adjustment_value", precision = 10, scale = 2, nullable = false)
    private BigDecimal adjustmentValue;

    @Column(name = "min_days")
    private Integer minDays;

    @Column(name = "max_days")
    private Integer maxDays;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "start_time")
    private String startTime; // HH:mm format

    @Column(name = "end_time")
    private String endTime; // HH:mm format

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "is_weekend")
    private Boolean isWeekend;

    @Column(name = "is_holiday")
    private Boolean isHoliday;

    @Column(name = "min_occupancy_percentage")
    private Integer minOccupancyPercentage;

    @Column(name = "max_occupancy_percentage")
    private Integer maxOccupancyPercentage;

    @Column(name = "weather_condition")
    private String weatherCondition;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "priority")
    private Integer priority = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Enums
    public enum PricingRuleType {
        SEASONAL,
        WEEKEND,
        HOLIDAY,
        OCCUPANCY,
        WEATHER,
        EVENT,
        PEAK_HOURS,
        LAST_MINUTE,
        ADVANCE_BOOKING,
        LOYALTY,
        CUSTOM
    }

    public enum AdjustmentType {
        PERCENTAGE,
        FIXED_AMOUNT,
        MULTIPLIER
    }

    // Constructors
    public PricingRule() {}

    public PricingRule(String ruleName, PricingRuleType ruleType, AdjustmentType adjustmentType, BigDecimal adjustmentValue) {
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

    public PricingRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(PricingRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentType adjustmentType) {
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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