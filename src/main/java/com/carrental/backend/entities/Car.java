package com.carrental.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String brand;

    @NotBlank
    @Size(max = 255)
    private String model;

    @NotNull
    private Integer year;

    @NotBlank
    @Size(max = 100)
    private String type;

    @NotNull
    @Positive
    @Column
    private BigDecimal price;

    @Column(name = "fuel_type", length = 50)
    private String fuelType;

    @Column(name = "transmission", length = 50)
    private String transmission;

    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "engine_size", length = 20)
    private String engineSize;

    @Column(name = "horsepower")
    private Integer horsepower;

    @Column(name = "torque")
    private Integer torque;

    @Column(name = "acceleration_0_60")
    private Double acceleration0To60;

    @Column(name = "top_speed")
    private Integer topSpeed;

    @Column(name = "fuel_efficiency_city")
    private Double fuelEfficiencyCity;

    @Column(name = "fuel_efficiency_highway")
    private Double fuelEfficiencyHighway;

    @Column(name = "fuel_tank_capacity")
    private Double fuelTankCapacity;

    @Column(name = "trunk_capacity")
    private Double trunkCapacity;

    @Column(name = "ground_clearance")
    private Double groundClearance;

    @Column(name = "wheelbase")
    private Double wheelbase;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "curb_weight")
    private Double curbWeight;

    @Column(name = "features", columnDefinition = "TEXT")
    private String features;

    @Column(name = "safety_features", columnDefinition = "TEXT")
    private String safetyFeatures;

    @Column(name = "entertainment_features", columnDefinition = "TEXT")
    private String entertainmentFeatures;

    @Column(name = "comfort_features", columnDefinition = "TEXT")
    private String comfortFeatures;

    @Column(name = "technology_features", columnDefinition = "TEXT")
    private String technologyFeatures;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "is_new_arrival")
    private Boolean isNewArrival = false;

    @Column(name = "is_popular")
    private Boolean isPopular = false;

    @Column(name = "daily_rate")
    private BigDecimal dailyRate;

    @Column(name = "weekly_rate")
    private BigDecimal weeklyRate;

    @Column(name = "monthly_rate")
    private BigDecimal monthlyRate;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "insurance_included")
    private Boolean insuranceIncluded = false;

    @Column(name = "maintenance_included")
    private Boolean maintenanceIncluded = false;

    @Column(name = "unlimited_mileage")
    private Boolean unlimitedMileage = false;

    @Column(name = "minimum_rental_days")
    private Integer minimumRentalDays = 1;

    @Column(name = "maximum_rental_days")
    private Integer maximumRentalDays = 365;

    @Column(name = "age_requirement")
    private Integer ageRequirement = 21;

    @Column(name = "license_requirement")
    private String licenseRequirement = "Valid driver's license";

    @Column(name = "additional_driver_fee")
    private BigDecimal additionalDriverFee = BigDecimal.ZERO;

    @Column(name = "late_return_fee")
    private BigDecimal lateReturnFee = BigDecimal.ZERO;

    @Column(name = "early_return_discount")
    private BigDecimal earlyReturnDiscount = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean availability = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("sortOrder ASC, createdAt ASC")
    private Set<CarImage> images = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MaintenanceRecord> maintenanceRecords = new HashSet<>();

    @Column(name = "average_rating")
    private Double averageRating = 0.0;

    @Column(name = "rating_count")
    private Long ratingCount = 0L;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public Car() {}

    public Car(String brand, String model, Integer year, String type, BigDecimal price, Branch branch) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.type = type;
        this.price = price;
        this.branch = branch;
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

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }

    public Set<CarImage> getImages() {
        return images;
    }

    public void setImages(Set<CarImage> images) {
        this.images = images;
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

    public Set<MaintenanceRecord> getMaintenanceRecords() {
        return maintenanceRecords;
    }

    public void setMaintenanceRecords(Set<MaintenanceRecord> maintenanceRecords) {
        this.maintenanceRecords = maintenanceRecords;
    }
}

