package com.carrental.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BranchDto {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "City is required")
    @Size(max = 255, message = "City must not exceed 255 characters")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    private Integer carCount;

    // Constructors
    public BranchDto() {}

    public BranchDto(Long id, String name, String city, String address, Integer carCount) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.carCount = carCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }
}

