package com.carrental.backend.controller;

import com.carrental.backend.dto.CarDto;
import com.carrental.backend.dto.FavoriteDto;
import com.carrental.backend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // Add car to favorites
    @PostMapping("/cars/{carId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<FavoriteDto> addToFavorites(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        FavoriteDto favorite = favoriteService.addToFavorites(userId, carId);
        return ResponseEntity.ok(favorite);
    }

    // Remove car from favorites
    @DeleteMapping("/cars/{carId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> removeFromFavorites(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        favoriteService.removeFromFavorites(userId, carId);
        return ResponseEntity.ok().build();
    }

    // Toggle favorite status
    @PostMapping("/cars/{carId}/toggle")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> toggleFavorite(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        FavoriteDto favorite = favoriteService.toggleFavorite(userId, carId);
        
        if (favorite != null) {
            return ResponseEntity.ok(favorite);
        } else {
            return ResponseEntity.ok().body("{\"message\": \"Removed from favorites\"}");
        }
    }

    // Get all favorites for current user
    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<FavoriteDto>> getUserFavorites() {
        Long userId = getCurrentUserId();
        List<FavoriteDto> favorites = favoriteService.getUserFavorites(userId);
        return ResponseEntity.ok(favorites);
    }

    // Get favorite cars for current user
    @GetMapping("/cars")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CarDto>> getUserFavoriteCars() {
        Long userId = getCurrentUserId();
        List<CarDto> cars = favoriteService.getUserFavoriteCars(userId);
        return ResponseEntity.ok(cars);
    }

    // Check if a car is favorited by current user
    @GetMapping("/cars/{carId}/check")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Boolean> isCarFavorited(@PathVariable Long carId) {
        Long userId = getCurrentUserId();
        boolean isFavorited = favoriteService.isCarFavorited(userId, carId);
        return ResponseEntity.ok(isFavorited);
    }

    // Get favorite count for current user
    @GetMapping("/count")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Long> getFavoriteCount() {
        Long userId = getCurrentUserId();
        long count = favoriteService.getFavoriteCount(userId);
        return ResponseEntity.ok(count);
    }

    // Get current user ID from authentication context
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
            // You might need to adjust this based on how you store user ID in UserDetails
            // For now, assuming you have a custom UserPrincipal that extends UserDetails
            if (authentication.getPrincipal() instanceof com.carrental.backend.security.UserPrincipal) {
                return ((com.carrental.backend.security.UserPrincipal) authentication.getPrincipal()).getId();
            }
        }
        throw new RuntimeException("User not authenticated or user ID not found");
    }
} 