package com.carrental.backend.dto;

import java.util.List;
import java.util.Map;

public class CarComparisonResultDto {
    
    private List<CarDto> cars;
    private Map<String, List<ComparisonAttributeDto>> specifications;
    private Map<String, List<ComparisonAttributeDto>> features;
    private Map<String, List<ComparisonAttributeDto>> pricing;
    private Map<String, List<ComparisonAttributeDto>> rental;
    private String comparisonType;
    
    public CarComparisonResultDto() {}
    
    public CarComparisonResultDto(List<CarDto> cars, String comparisonType) {
        this.cars = cars;
        this.comparisonType = comparisonType;
    }
    
    // Getters and Setters
    public List<CarDto> getCars() {
        return cars;
    }
    
    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }
    
    public Map<String, List<ComparisonAttributeDto>> getSpecifications() {
        return specifications;
    }
    
    public void setSpecifications(Map<String, List<ComparisonAttributeDto>> specifications) {
        this.specifications = specifications;
    }
    
    public Map<String, List<ComparisonAttributeDto>> getFeatures() {
        return features;
    }
    
    public void setFeatures(Map<String, List<ComparisonAttributeDto>> features) {
        this.features = features;
    }
    
    public Map<String, List<ComparisonAttributeDto>> getPricing() {
        return pricing;
    }
    
    public void setPricing(Map<String, List<ComparisonAttributeDto>> pricing) {
        this.pricing = pricing;
    }
    
    public Map<String, List<ComparisonAttributeDto>> getRental() {
        return rental;
    }
    
    public void setRental(Map<String, List<ComparisonAttributeDto>> rental) {
        this.rental = rental;
    }
    
    public String getComparisonType() {
        return comparisonType;
    }
    
    public void setComparisonType(String comparisonType) {
        this.comparisonType = comparisonType;
    }
} 