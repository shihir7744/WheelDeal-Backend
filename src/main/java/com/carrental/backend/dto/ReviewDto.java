package com.carrental.backend.dto;

import com.carrental.backend.entities.ReviewStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ReviewDto {
    
    private Long id;
    
    @NotNull(message = "Car ID is required")
    private Long carId;
    
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    
    @NotBlank(message = "Comment is required")
    @Size(min = 10, max = 1000, message = "Comment must be between 10 and 1000 characters")
    private String comment;
    
    private String userName;
    private ReviewStatus status;
    private String moderatorNotes;
    private String moderatedBy;
    private LocalDateTime moderatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Default constructor
    public ReviewDto() {}
    
    // Constructor with required fields
    public ReviewDto(Long carId, Integer rating, String comment) {
        this.carId = carId;
        this.rating = rating;
        this.comment = comment;
    }
    
    // Full constructor
    public ReviewDto(Long id, Long carId, Integer rating, String comment, String userName, 
                    ReviewStatus status, String moderatorNotes, String moderatedBy, 
                    LocalDateTime moderatedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.carId = carId;
        this.rating = rating;
        this.comment = comment;
        this.userName = userName;
        this.status = status;
        this.moderatorNotes = moderatorNotes;
        this.moderatedBy = moderatedBy;
        this.moderatedAt = moderatedAt;
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
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public ReviewStatus getStatus() {
        return status;
    }
    
    public void setStatus(ReviewStatus status) {
        this.status = status;
    }
    
    public String getModeratorNotes() {
        return moderatorNotes;
    }
    
    public void setModeratorNotes(String moderatorNotes) {
        this.moderatorNotes = moderatorNotes;
    }
    
    public String getModeratedBy() {
        return moderatedBy;
    }
    
    public void setModeratedBy(String moderatedBy) {
        this.moderatedBy = moderatedBy;
    }
    
    public LocalDateTime getModeratedAt() {
        return moderatedAt;
    }
    
    public void setModeratedAt(LocalDateTime moderatedAt) {
        this.moderatedAt = moderatedAt;
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