package com.carrental.backend.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class RatingDto {
    
    private Long id;
    
    @NotNull(message = "Car ID is required")
    private Long carId;
    
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Default constructor
    public RatingDto() {}
    
    // Constructor with required fields
    public RatingDto(Long carId, Integer rating) {
        this.carId = carId;
        this.rating = rating;
    }
    
    // Full constructor
    public RatingDto(Long id, Long carId, Integer rating, String userName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.carId = carId;
        this.rating = rating;
        this.userName = userName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCarId() {
        return carId;
    }
    
    public void setCarId(Long carId) {
        this.carId = carId;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
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