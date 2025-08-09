package com.carrental.backend.repository;

import com.carrental.backend.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
    List<Rating> findByCarId(Long carId);
    
    List<Rating> findByUserId(Long userId);
    
    Optional<Rating> findByUserIdAndCarId(Long userId, Long carId);
    
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.car.id = :carId")
    Double getAverageRatingByCarId(@Param("carId") Long carId);
    
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.car.id = :carId")
    long countByCarId(@Param("carId") Long carId);
    
    @Query("SELECT r.rating, COUNT(r) FROM Rating r WHERE r.car.id = :carId GROUP BY r.rating ORDER BY r.rating DESC")
    List<Object[]> getRatingDistributionByCarId(@Param("carId") Long carId);
    
    boolean existsByUserIdAndCarId(Long userId, Long carId);
} 