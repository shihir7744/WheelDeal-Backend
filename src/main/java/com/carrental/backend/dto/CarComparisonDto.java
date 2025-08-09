package com.carrental.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CarComparisonDto {
    
    @NotEmpty(message = "At least one car ID is required")
    @Size(min = 2, max = 5, message = "Comparison must include between 2 and 5 cars")
    private List<Long> carIds;
    
    private String comparisonType = "specifications"; // specifications, features, pricing, all
    
    public CarComparisonDto() {}
    
    public CarComparisonDto(List<Long> carIds) {
        this.carIds = carIds;
    }
    
    public CarComparisonDto(List<Long> carIds, String comparisonType) {
        this.carIds = carIds;
        this.comparisonType = comparisonType;
    }
    
    // Getters and Setters
    public List<Long> getCarIds() {
        return carIds;
    }
    
    public void setCarIds(List<Long> carIds) {
        this.carIds = carIds;
    }
    
    public String getComparisonType() {
        return comparisonType;
    }
    
    public void setComparisonType(String comparisonType) {
        this.comparisonType = comparisonType;
    }
} 