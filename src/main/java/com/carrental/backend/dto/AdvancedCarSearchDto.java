package com.carrental.backend.dto;

import java.math.BigDecimal;

public class AdvancedCarSearchDto {
    
    // Basic filters
    private String brand;
    private String model;
    private String type;
    private String city;
    private Long branchId;
    
    // Price range
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    
    // New advanced filters
    private String fuelType;
    private String transmission;
    private Integer minSeatingCapacity;
    private Integer maxSeatingCapacity;
    private Integer minYear;
    private Integer maxYear;
    private String color;
    private String engineSize;
    
    // Rating filters
    private Double minRating;
    private Integer minRatingCount;
    
    // Sorting
    private String sortBy; // "price", "rating", "year", "mileage"
    private String sortOrder; // "asc", "desc"
    
    // Pagination
    private Integer page = 0;
    private Integer size = 20;
    
    // Availability
    private Boolean availableOnly = true;
    
    // Default constructor
    public AdvancedCarSearchDto() {}
    
    // Constructor with basic filters
    public AdvancedCarSearchDto(String brand, String model, String type, String city, 
                               BigDecimal minPrice, BigDecimal maxPrice) {
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.city = city;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
    
    // Getters and Setters
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
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public Long getBranchId() {
        return branchId;
    }
    
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
    
    public BigDecimal getMinPrice() {
        return minPrice;
    }
    
    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }
    
    public BigDecimal getMaxPrice() {
        return maxPrice;
    }
    
    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
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
    
    public Integer getMinSeatingCapacity() {
        return minSeatingCapacity;
    }
    
    public void setMinSeatingCapacity(Integer minSeatingCapacity) {
        this.minSeatingCapacity = minSeatingCapacity;
    }
    
    public Integer getMaxSeatingCapacity() {
        return maxSeatingCapacity;
    }
    
    public void setMaxSeatingCapacity(Integer maxSeatingCapacity) {
        this.maxSeatingCapacity = maxSeatingCapacity;
    }
    
    public Integer getMinYear() {
        return minYear;
    }
    
    public void setMinYear(Integer minYear) {
        this.minYear = minYear;
    }
    
    public Integer getMaxYear() {
        return maxYear;
    }
    
    public void setMaxYear(Integer maxYear) {
        this.maxYear = maxYear;
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
    
    public Double getMinRating() {
        return minRating;
    }
    
    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }
    
    public Integer getMinRatingCount() {
        return minRatingCount;
    }
    
    public void setMinRatingCount(Integer minRatingCount) {
        this.minRatingCount = minRatingCount;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public Integer getSize() {
        return size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }
    
    public Boolean getAvailableOnly() {
        return availableOnly;
    }
    
    public void setAvailableOnly(Boolean availableOnly) {
        this.availableOnly = availableOnly;
    }
    
    // Helper methods
    public boolean hasPriceFilter() {
        return minPrice != null || maxPrice != null;
    }
    
    public boolean hasYearFilter() {
        return minYear != null || maxYear != null;
    }
    
    public boolean hasSeatingCapacityFilter() {
        return minSeatingCapacity != null || maxSeatingCapacity != null;
    }
    
    public boolean hasRatingFilter() {
        return minRating != null || minRatingCount != null;
    }
    
    public boolean hasSorting() {
        return sortBy != null && sortOrder != null;
    }
} 