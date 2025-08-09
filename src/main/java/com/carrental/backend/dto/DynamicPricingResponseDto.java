package com.carrental.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DynamicPricingResponseDto {
    private Long carId;
    private String carBrand;
    private String carModel;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer rentalDurationDays;
    
    // Pricing information
    private BigDecimal basePrice;
    private BigDecimal finalPrice;
    private BigDecimal totalAdjustment;
    private BigDecimal adjustmentPercentage;
    
    // Applied rules and factors
    private List<AppliedRuleDto> appliedRules;
    private Map<String, Object> calculationFactors;
    
    // Market conditions
    private String demandLevel;
    private Double occupancyRate;
    private String season;
    private Boolean isWeekend;
    private Boolean isHoliday;
    private String weatherCondition;
    private String eventNearby;
    
    // Pricing breakdown
    private List<PricingBreakdownDto> pricingBreakdown;
    
    // Recommendations
    private List<String> recommendations;
    private String pricingStrategy;

    // Constructors
    public DynamicPricingResponseDto() {}

    public DynamicPricingResponseDto(Long carId, BigDecimal basePrice, BigDecimal finalPrice) {
        this.carId = carId;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
    }

    // Getters and Setters
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

    public List<AppliedRuleDto> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(List<AppliedRuleDto> appliedRules) {
        this.appliedRules = appliedRules;
    }

    public Map<String, Object> getCalculationFactors() {
        return calculationFactors;
    }

    public void setCalculationFactors(Map<String, Object> calculationFactors) {
        this.calculationFactors = calculationFactors;
    }

    public String getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(String demandLevel) {
        this.demandLevel = demandLevel;
    }

    public Double getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(Double occupancyRate) {
        this.occupancyRate = occupancyRate;
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

    public List<PricingBreakdownDto> getPricingBreakdown() {
        return pricingBreakdown;
    }

    public void setPricingBreakdown(List<PricingBreakdownDto> pricingBreakdown) {
        this.pricingBreakdown = pricingBreakdown;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public String getPricingStrategy() {
        return pricingStrategy;
    }

    public void setPricingStrategy(String pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    // Inner classes for detailed breakdown
    public static class AppliedRuleDto {
        private String ruleName;
        private String ruleType;
        private String adjustmentType;
        private BigDecimal adjustmentValue;
        private BigDecimal appliedAdjustment;
        private String reason;

        // Getters and Setters
        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getRuleType() {
            return ruleType;
        }

        public void setRuleType(String ruleType) {
            this.ruleType = ruleType;
        }

        public String getAdjustmentType() {
            return adjustmentType;
        }

        public void setAdjustmentType(String adjustmentType) {
            this.adjustmentType = adjustmentType;
        }

        public BigDecimal getAdjustmentValue() {
            return adjustmentValue;
        }

        public void setAdjustmentValue(BigDecimal adjustmentValue) {
            this.adjustmentValue = adjustmentValue;
        }

        public BigDecimal getAppliedAdjustment() {
            return appliedAdjustment;
        }

        public void setAppliedAdjustment(BigDecimal appliedAdjustment) {
            this.appliedAdjustment = appliedAdjustment;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public static class PricingBreakdownDto {
        private String category;
        private BigDecimal amount;
        private String description;
        private String type; // BASE, ADJUSTMENT, DISCOUNT, SURCHARGE

        // Getters and Setters
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
} 