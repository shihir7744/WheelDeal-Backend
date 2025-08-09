package com.carrental.backend.dto;

import java.time.LocalDateTime;

public class FavoriteDto {
    
    private Long id;
    private Long userId;
    private Long carId;
    private CarDto car;
    private LocalDateTime createdAt;
    
    // Constructors
    public FavoriteDto() {}
    
    public FavoriteDto(Long id, Long userId, Long carId, CarDto car, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.car = car;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getCarId() {
        return carId;
    }
    
    public void setCarId(Long carId) {
        this.carId = carId;
    }
    
    public CarDto getCar() {
        return car;
    }
    
    public void setCar(CarDto car) {
        this.car = car;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "FavoriteDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", carId=" + carId +
                ", car=" + car +
                ", createdAt=" + createdAt +
                '}';
    }
} 