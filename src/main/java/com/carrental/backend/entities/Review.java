package com.carrental.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    
    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;
    
    @NotBlank
    @Size(min = 10, max = 1000)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReviewStatus status = ReviewStatus.PENDING;
    
    @Column(name = "moderator_notes")
    private String moderatorNotes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderated_by")
    private User moderatedBy;
    
    @Column(name = "moderated_at")
    private LocalDateTime moderatedAt;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Default constructor
    public Review() {}
    
    // Constructor with required fields
    public Review(User user, Car car, Integer rating, String comment) {
        this.user = user;
        this.car = car;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Car getCar() {
        return car;
    }
    
    public void setCar(Car car) {
        this.car = car;
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
    
    public User getModeratedBy() {
        return moderatedBy;
    }
    
    public void setModeratedBy(User moderatedBy) {
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
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 