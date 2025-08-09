package com.carrental.backend.controller;

import com.carrental.backend.dto.ReviewDto;
import com.carrental.backend.entities.ReviewStatus;
import com.carrental.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Add or update a review for a car
     */
    @PostMapping("/cars/{carId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> addOrUpdateReview(
            @PathVariable Long carId,
            @Valid @RequestBody ReviewDto reviewDto) {
        
        Long userId = getCurrentUserId();
        ReviewDto result = reviewService.addOrUpdateReview(userId, carId, reviewDto.getRating(), reviewDto.getComment());
        return ResponseEntity.ok(result);
    }

    /**
     * Get approved reviews for a car
     */
    @GetMapping("/cars/{carId}")
    public ResponseEntity<List<ReviewDto>> getApprovedReviews(@PathVariable Long carId) {
        List<ReviewDto> reviews = reviewService.getApprovedReviews(carId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get all reviews for a car (admin/moderator only)
     */
    @GetMapping("/cars/{carId}/all")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<ReviewDto>> getAllCarReviews(@PathVariable Long carId) {
        List<ReviewDto> reviews = reviewService.getAllCarReviews(carId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get user's review for a specific car
     */
    @GetMapping("/cars/{carId}/user")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> getUserReview(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        ReviewDto review = reviewService.getUserReview(userId, carId);
        return ResponseEntity.ok(review);
    }

    /**
     * Get pending reviews for moderation
     */
    @GetMapping("/pending")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<ReviewDto>> getPendingReviews() {
        List<ReviewDto> reviews = reviewService.getPendingReviews();
        return ResponseEntity.ok(reviews);
    }

    /**
     * Moderate a review (approve/reject)
     */
    @PutMapping("/{reviewId}/moderate")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> moderateReview(
            @PathVariable Long reviewId,
            @RequestParam ReviewStatus status,
            @RequestParam(required = false) String moderatorNotes) {
        
        Long moderatorId = getCurrentUserId();
        ReviewDto result = reviewService.moderateReview(reviewId, status, moderatorNotes, moderatorId);
        return ResponseEntity.ok(result);
    }

    /**
     * Get average rating for a car from approved reviews
     */
    @GetMapping("/cars/{carId}/average")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Long carId) {
        Double averageRating = reviewService.getAverageRating(carId);
        long approvedCount = reviewService.getReviewCount(carId, ReviewStatus.APPROVED);
        long pendingCount = reviewService.getReviewCount(carId, ReviewStatus.PENDING);
        long rejectedCount = reviewService.getReviewCount(carId, ReviewStatus.REJECTED);
        
        Map<String, Object> response = Map.of(
            "carId", carId,
            "averageRating", averageRating,
            "approvedCount", approvedCount,
            "pendingCount", pendingCount,
            "rejectedCount", rejectedCount
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Check if user has reviewed a car
     */
    @GetMapping("/cars/{carId}/has-reviewed")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> hasUserReviewed(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        boolean hasReviewed = reviewService.hasUserReviewed(userId, carId);
        
        Map<String, Object> response = Map.of(
            "carId", carId,
            "hasReviewed", hasReviewed
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a review
     */
    @DeleteMapping("/cars/{carId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        reviewService.deleteReview(userId, carId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get current user ID from security context
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
            // This would need to be implemented based on your UserPrincipal implementation
            // For now, we'll throw an exception indicating this needs to be implemented
            throw new RuntimeException("User ID extraction from security context needs to be implemented");
        }
        throw new RuntimeException("User not authenticated");
    }
} 