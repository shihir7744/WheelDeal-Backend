package com.carrental.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public class CarDto {
    private Long id;

    @NotBlank(message = "Brand is required")
    @Size(max = 255, message = "Brand must not exceed 255 characters")
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 255, message = "Model must not exceed 255 characters")
    private String model;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotBlank(message = "Type is required")
    @Size(max = 100, message = "Type must not exceed 100 characters")
    private String type;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private Boolean availability;

    @NotNull(message = "Branch ID is required")
    private Long branchId;
    
    private String branchName;
    private String branchCity;
    
    private Double averageRating;
    private Long ratingCount;
    private Long reviewCount;
    
    // New advanced fields
    private String fuelType;
    private String transmission;
    private Integer seatingCapacity;
    private Integer mileage;
    private String color;
    private String engineSize;
    
    // Enhanced specifications
    private Integer horsepower;
    private Integer torque;
    private Double acceleration0To60;
    private Integer topSpeed;
    private Double fuelEfficiencyCity;
    private Double fuelEfficiencyHighway;
    private Double fuelTankCapacity;
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
    
    // Rental information
    private Boolean isFeatured;
    private Boolean isNewArrival;
    private Boolean isPopular;
    private BigDecimal dailyRate;
    private BigDecimal weeklyRate;
    private BigDecimal monthlyRate;
    private BigDecimal depositAmount;
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
    
    // Images
    private List<CarImageDto> images;

    // Constructors
    public CarDto() {}

    public CarDto(Long id, String brand, String model, Integer year, String type, 
                  BigDecimal price, Boolean availability, Long branchId, 
                  String branchName, String branchCity, Double averageRating, 
                  Long ratingCount, Long reviewCount, String fuelType, String transmission,
                  Integer seatingCapacity, Integer mileage, String color, String engineSize,
                  Integer horsepower, Integer torque, Double acceleration0To60, Integer topSpeed,
                  Double fuelEfficiencyCity, Double fuelEfficiencyHighway, Double fuelTankCapacity,
                  Double trunkCapacity, Double groundClearance, Double wheelbase, Double length,
                  Double width, Double height, Double curbWeight, String features,
                  String safetyFeatures, String entertainmentFeatures, String comfortFeatures,
                  String technologyFeatures, Boolean isFeatured, Boolean isNewArrival,
                  Boolean isPopular, BigDecimal dailyRate, BigDecimal weeklyRate,
                  BigDecimal monthlyRate, BigDecimal depositAmount, Boolean insuranceIncluded,
                  Boolean maintenanceIncluded, Boolean unlimitedMileage, Integer minimumRentalDays,
                  Integer maximumRentalDays, Integer ageRequirement, String licenseRequirement,
                  BigDecimal additionalDriverFee, BigDecimal lateReturnFee, BigDecimal earlyReturnDiscount) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.type = type;
        this.price = price;
        this.availability = availability;
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchCity = branchCity;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.reviewCount = reviewCount;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.seatingCapacity = seatingCapacity;
        this.mileage = mileage;
        this.color = color;
        this.engineSize = engineSize;
        this.horsepower = horsepower;
        this.torque = torque;
        this.acceleration0To60 = acceleration0To60;
        this.topSpeed = topSpeed;
        this.fuelEfficiencyCity = fuelEfficiencyCity;
        this.fuelEfficiencyHighway = fuelEfficiencyHighway;
        this.fuelTankCapacity = fuelTankCapacity;
        this.trunkCapacity = trunkCapacity;
        this.groundClearance = groundClearance;
        this.wheelbase = wheelbase;
        this.length = length;
        this.width = width;
        this.height = height;
        this.curbWeight = curbWeight;
        this.features = features;
        this.safetyFeatures = safetyFeatures;
        this.entertainmentFeatures = entertainmentFeatures;
        this.comfortFeatures = comfortFeatures;
        this.technologyFeatures = technologyFeatures;
        this.isFeatured = isFeatured;
        this.isNewArrival = isNewArrival;
        this.isPopular = isPopular;
        this.dailyRate = dailyRate;
        this.weeklyRate = weeklyRate;
        this.monthlyRate = monthlyRate;
        this.depositAmount = depositAmount;
        this.insuranceIncluded = insuranceIncluded;
        this.maintenanceIncluded = maintenanceIncluded;
        this.unlimitedMileage = unlimitedMileage;
        this.minimumRentalDays = minimumRentalDays;
        this.maximumRentalDays = maximumRentalDays;
        this.ageRequirement = ageRequirement;
        this.licenseRequirement = licenseRequirement;
        this.additionalDriverFee = additionalDriverFee;
        this.lateReturnFee = lateReturnFee;
        this.earlyReturnDiscount = earlyReturnDiscount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(String branchCity) {
        this.branchCity = branchCity;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
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
    
    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }
    
    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }
    
    public Integer getMileage() {
        return mileage;
    }
    
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getEngineSize() {
        return engineSize;
    }
    
    public void setEngineSize(String engineSize) {
        this.engineSize = engineSize;
    }

    // Enhanced specifications getters and setters
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

    // Features getters and setters
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

    // Rental information getters and setters
    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsNewArrival() {
        return isNewArrival;
    }

    public void setIsNewArrival(Boolean isNewArrival) {
        this.isNewArrival = isNewArrival;
    }

    public Boolean getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(Boolean isPopular) {
        this.isPopular = isPopular;
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
    
    public List<CarImageDto> getImages() {
        return images;
    }

    public void setImages(List<CarImageDto> images) {
        this.images = images;
    }
}

