package com.carrental.backend.dto;

import java.math.BigDecimal;

public class CarSpecificationsDto {
    private Long carId;
    private String brand;
    private String model;
    private Integer year;
    
    // Engine specifications
    private String engineSize;
    private Integer horsepower;
    private Integer torque;
    private String fuelType;
    private String transmission;
    
    // Performance specifications
    private Double acceleration0To60;
    private Integer topSpeed;
    private Double fuelEfficiencyCity;
    private Double fuelEfficiencyHighway;
    private Double fuelTankCapacity;
    
    // Dimensions and capacity
    private Integer seatingCapacity;
    private Double trunkCapacity;
    private Double groundClearance;
    private Double wheelbase;
    private Double length;
    private Double width;
    private Double height;
    private Double curbWeight;
    
    // Features
    private String features;
    private String safetyFeatures;
    private String entertainmentFeatures;
    private String comfortFeatures;
    private String technologyFeatures;
    
    // Rental rates
    private BigDecimal dailyRate;
    private BigDecimal weeklyRate;
    private BigDecimal monthlyRate;
    private BigDecimal depositAmount;
    
    // Rental policies
    private Boolean insuranceIncluded;
    private Boolean maintenanceIncluded;
    private Boolean unlimitedMileage;
    private Integer minimumRentalDays;
    private Integer maximumRentalDays;
    private Integer ageRequirement;
    private String licenseRequirement;
    private BigDecimal additionalDriverFee;
    private BigDecimal lateReturnFee;
    private BigDecimal earlyReturnDiscount;

    public CarSpecificationsDto() {}

    // Getters and Setters
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(String engineSize) {
        this.engineSize = engineSize;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    public Integer getTorque() {
        return torque;
    }

    public void setTorque(Integer torque) {
        this.torque = torque;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Double getAcceleration0To60() {
        return acceleration0To60;
    }

    public void setAcceleration0To60(Double acceleration0To60) {
        this.acceleration0To60 = acceleration0To60;
    }

    public Integer getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(Integer topSpeed) {
        this.topSpeed = topSpeed;
    }

    public Double getFuelEfficiencyCity() {
        return fuelEfficiencyCity;
    }

    public void setFuelEfficiencyCity(Double fuelEfficiencyCity) {
        this.fuelEfficiencyCity = fuelEfficiencyCity;
    }

    public Double getFuelEfficiencyHighway() {
        return fuelEfficiencyHighway;
    }

    public void setFuelEfficiencyHighway(Double fuelEfficiencyHighway) {
        this.fuelEfficiencyHighway = fuelEfficiencyHighway;
    }

    public Double getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(Double fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Double getTrunkCapacity() {
        return trunkCapacity;
    }

    public void setTrunkCapacity(Double trunkCapacity) {
        this.trunkCapacity = trunkCapacity;
    }

    public Double getGroundClearance() {
        return groundClearance;
    }

    public void setGroundClearance(Double groundClearance) {
        this.groundClearance = groundClearance;
    }

    public Double getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(Double wheelbase) {
        this.wheelbase = wheelbase;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getCurbWeight() {
        return curbWeight;
    }

    public void setCurbWeight(Double curbWeight) {
        this.curbWeight = curbWeight;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getSafetyFeatures() {
        return safetyFeatures;
    }

    public void setSafetyFeatures(String safetyFeatures) {
        this.safetyFeatures = safetyFeatures;
    }

    public String getEntertainmentFeatures() {
        return entertainmentFeatures;
    }

    public void setEntertainmentFeatures(String entertainmentFeatures) {
        this.entertainmentFeatures = entertainmentFeatures;
    }

    public String getComfortFeatures() {
        return comfortFeatures;
    }

    public void setComfortFeatures(String comfortFeatures) {
        this.comfortFeatures = comfortFeatures;
    }

    public String getTechnologyFeatures() {
        return technologyFeatures;
    }

    public void setTechnologyFeatures(String technologyFeatures) {
        this.technologyFeatures = technologyFeatures;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public BigDecimal getWeeklyRate() {
        return weeklyRate;
    }

    public void setWeeklyRate(BigDecimal weeklyRate) {
        this.weeklyRate = weeklyRate;
    }

    public BigDecimal getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(BigDecimal monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Boolean getInsuranceIncluded() {
        return insuranceIncluded;
    }

    public void setInsuranceIncluded(Boolean insuranceIncluded) {
        this.insuranceIncluded = insuranceIncluded;
    }

    public Boolean getMaintenanceIncluded() {
        return maintenanceIncluded;
    }

    public void setMaintenanceIncluded(Boolean maintenanceIncluded) {
        this.maintenanceIncluded = maintenanceIncluded;
    }

    public Boolean getUnlimitedMileage() {
        return unlimitedMileage;
    }

    public void setUnlimitedMileage(Boolean unlimitedMileage) {
        this.unlimitedMileage = unlimitedMileage;
    }

    public Integer getMinimumRentalDays() {
        return minimumRentalDays;
    }

    public void setMinimumRentalDays(Integer minimumRentalDays) {
        this.minimumRentalDays = minimumRentalDays;
    }

    public Integer getMaximumRentalDays() {
        return maximumRentalDays;
    }

    public void setMaximumRentalDays(Integer maximumRentalDays) {
        this.maximumRentalDays = maximumRentalDays;
    }

    public Integer getAgeRequirement() {
        return ageRequirement;
    }

    public void setAgeRequirement(Integer ageRequirement) {
        this.ageRequirement = ageRequirement;
    }

    public String getLicenseRequirement() {
        return licenseRequirement;
    }

    public void setLicenseRequirement(String licenseRequirement) {
        this.licenseRequirement = licenseRequirement;
    }

    public BigDecimal getAdditionalDriverFee() {
        return additionalDriverFee;
    }

    public void setAdditionalDriverFee(BigDecimal additionalDriverFee) {
        this.additionalDriverFee = additionalDriverFee;
    }

    public BigDecimal getLateReturnFee() {
        return lateReturnFee;
    }

    public void setLateReturnFee(BigDecimal lateReturnFee) {
        this.lateReturnFee = lateReturnFee;
    }

    public BigDecimal getEarlyReturnDiscount() {
        return earlyReturnDiscount;
    }

    public void setEarlyReturnDiscount(BigDecimal earlyReturnDiscount) {
        this.earlyReturnDiscount = earlyReturnDiscount;
    }
} 