package com.carrental.backend.repository;

import com.carrental.backend.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    // Find all favorites for a user
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId ORDER BY f.createdAt DESC")
    List<Favorite> findByUserId(@Param("userId") Long userId);
    
    // Find a specific favorite by user and car
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId AND f.car.id = :carId")
    Optional<Favorite> findByUserIdAndCarId(@Param("userId") Long userId, @Param("carId") Long carId);
    
    // Check if a car is favorited by a user
    @Query("SELECT COUNT(f) > 0 FROM Favorite f WHERE f.user.id = :userId AND f.car.id = :carId")
    boolean existsByUserIdAndCarId(@Param("userId") Long userId, @Param("carId") Long carId);
    
    // Count favorites for a user
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    // Delete favorite by user and car
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId AND f.car.id = :carId")
    void deleteByUserIdAndCarId(@Param("userId") Long userId, @Param("carId") Long carId);
    
    // Find all car IDs favorited by a user
    @Query("SELECT f.car.id FROM Favorite f WHERE f.user.id = :userId")
    List<Long> findCarIdsByUserId(@Param("userId") Long userId);
} 