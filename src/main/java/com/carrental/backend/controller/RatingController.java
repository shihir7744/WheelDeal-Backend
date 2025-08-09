package com.carrental.backend.controller;

import com.carrental.backend.dto.RatingDto;
import com.carrental.backend.service.RatingService;
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
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RatingController {

    @Autowired
    private RatingService ratingService;

    /**
     * Add or update a rating for a car
     */
    @PostMapping("/cars/{carId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<RatingDto> addOrUpdateRating(
            @PathVariable Long carId,
            @Valid @RequestBody RatingDto ratingDto) {
        
        Long userId = getCurrentUserId();
        RatingDto result = ratingService.addOrUpdateRating(userId, carId, ratingDto.getRating());
        return ResponseEntity.ok(result);
    }

    /**
     * Get average rating for a car
     */
    @GetMapping("/cars/{carId}/average")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Long carId) {
        Double averageRating = ratingService.getAverageRating(carId);
        long ratingCount = ratingService.getRatingCount(carId);
        
        Map<String, Object> response = Map.of(
            "carId", carId,
            "averageRating", averageRating,
            "ratingCount", ratingCount
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get rating distribution for a car
     */
    @GetMapping("/cars/{carId}/distribution")
    public ResponseEntity<Map<String, Object>> getRatingDistribution(@PathVariable Long carId) {
        Map<Integer, Long> distribution = ratingService.getRatingDistribution(carId);
        
        Map<String, Object> response = Map.of(
            "carId", carId,
            "distribution", distribution
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get all ratings for a car
     */
    @GetMapping("/cars/{carId}")
    public ResponseEntity<List<RatingDto>> getCarRatings(@PathVariable Long carId) {
        List<RatingDto> ratings = ratingService.getCarRatings(carId);
        return ResponseEntity.ok(ratings);
    }

    /**
     * Get user's rating for a specific car
     */
    @GetMapping("/cars/{carId}/user")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<RatingDto> getUserRating(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        RatingDto rating = ratingService.getUserRating(userId, carId);
        return ResponseEntity.ok(rating);
    }

    /**
     * Check if user has rated a car
     */
    @GetMapping("/cars/{carId}/has-rated")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> hasUserRated(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        boolean hasRated = ratingService.hasUserRated(userId, carId);
        
        Map<String, Object> response = Map.of(
            "carId", carId,
            "hasRated", hasRated
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a rating
     */
    @DeleteMapping("/cars/{carId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRating(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        ratingService.deleteRating(userId, carId);
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