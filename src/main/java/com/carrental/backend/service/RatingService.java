package com.carrental.backend.service;

import com.carrental.backend.dto.RatingDto;
import com.carrental.backend.entities.Car;
import com.carrental.backend.entities.Rating;
import com.carrental.backend.entities.User;
import com.carrental.backend.exception.BadRequestException;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.repository.CarRepository;
import com.carrental.backend.repository.RatingRepository;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Add or update a rating for a car
     */
    public RatingDto addOrUpdateRating(Long userId, Long carId, Integer rating) {
        // Validate car exists
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));

        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Validate rating value
        if (rating < 1 || rating > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }

        // Check if user has already rated this car
        Rating existingRating = ratingRepository.findByUserIdAndCarId(userId, carId).orElse(null);

        if (existingRating != null) {
            // Update existing rating
            existingRating.setRating(rating);
            Rating updatedRating = ratingRepository.save(existingRating);
            return convertToDto(updatedRating);
        } else {
            // Create new rating
            Rating newRating = new Rating(user, car, rating);
            Rating savedRating = ratingRepository.save(newRating);
            return convertToDto(savedRating);
        }
    }

    /**
     * Get average rating for a car
     */
    public Double getAverageRating(Long carId) {
        Double averageRating = ratingRepository.getAverageRatingByCarId(carId);
        return averageRating != null ? averageRating : 0.0;
    }

    /**
     * Get total number of ratings for a car
     */
    public long getRatingCount(Long carId) {
        return ratingRepository.countByCarId(carId);
    }

    /**
     * Get rating distribution for a car
     */
    public Map<Integer, Long> getRatingDistribution(Long carId) {
        List<Object[]> distribution = ratingRepository.getRatingDistributionByCarId(carId);
        return distribution.stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> (Long) row[1]
                ));
    }

    /**
     * Get all ratings for a car
     */
    public List<RatingDto> getCarRatings(Long carId) {
        List<Rating> ratings = ratingRepository.findByCarId(carId);
        return ratings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get user's rating for a specific car
     */
    public RatingDto getUserRating(Long userId, Long carId) {
        Rating rating = ratingRepository.findByUserIdAndCarId(userId, carId)
                .orElse(null);
        return rating != null ? convertToDto(rating) : null;
    }

    /**
     * Check if user has rated a car
     */
    public boolean hasUserRated(Long userId, Long carId) {
        return ratingRepository.existsByUserIdAndCarId(userId, carId);
    }

    /**
     * Delete a rating
     */
    public void deleteRating(Long userId, Long carId) {
        Rating rating = ratingRepository.findByUserIdAndCarId(userId, carId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "user and car", userId + "-" + carId));
        ratingRepository.delete(rating);
    }

    /**
     * Convert Rating entity to DTO
     */
    private RatingDto convertToDto(Rating rating) {
        return new RatingDto(
                rating.getId(),
                rating.getCar().getId(),
                rating.getRating(),
                rating.getUser().getFullName(),
                rating.getCreatedAt(),
                rating.getUpdatedAt()
        );
    }
} 