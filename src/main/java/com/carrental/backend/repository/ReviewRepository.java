package com.carrental.backend.repository;

import com.carrental.backend.entities.Review;
import com.carrental.backend.entities.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByCarId(Long carId);
    
    List<Review> findByCarIdAndStatus(Long carId, ReviewStatus status);
    
    List<Review> findByUserId(Long userId);
    
    Optional<Review> findByUserIdAndCarId(Long userId, Long carId);
    
    Page<Review> findByStatus(ReviewStatus status, Pageable pageable);
    
    Page<Review> findByStatusAndCarId(ReviewStatus status, Long carId, Pageable pageable);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.car.id = :carId AND r.status = :status")
    long countByCarIdAndStatus(@Param("carId") Long carId, @Param("status") ReviewStatus status);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.car.id = :carId AND r.status = :status")
    Double getAverageRatingByCarIdAndStatus(@Param("carId") Long carId, @Param("status") ReviewStatus status);
    
    @Query("SELECT r FROM Review r WHERE r.status = 'PENDING' ORDER BY r.createdAt ASC")
    List<Review> findPendingReviews();
    
    @Query("SELECT r FROM Review r WHERE r.car.id = :carId AND r.status = 'APPROVED' ORDER BY r.createdAt DESC")
    List<Review> findApprovedReviewsByCarId(@Param("carId") Long carId);
    
    boolean existsByUserIdAndCarId(Long userId, Long carId);
} 