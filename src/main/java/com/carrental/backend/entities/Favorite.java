package com.carrental.backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "car_id"})
})
public class Favorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Constructors
    public Favorite() {}
    
    public Favorite(User user, Car car) {
        this.user = user;
        this.car = car;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Favorite favorite = (Favorite) o;
        
        if (user != null ? !user.equals(favorite.user) : favorite.user != null) return false;
        return car != null ? car.equals(favorite.car) : favorite.car == null;
    }
    
    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (car != null ? car.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", carId=" + (car != null ? car.getId() : null) +
                ", createdAt=" + createdAt +
                '}';
    }
} 