package com.carrental.backend.service;

import com.carrental.backend.dto.ReviewDto;
import com.carrental.backend.entities.Car;
import com.carrental.backend.entities.Review;
import com.carrental.backend.entities.ReviewStatus;
import com.carrental.backend.entities.User;
import com.carrental.backend.exception.BadRequestException;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.repository.CarRepository;
import com.carrental.backend.repository.ReviewRepository;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Add or update a review for a car
     */
    public ReviewDto addOrUpdateReview(Long userId, Long carId, Integer rating, String comment) {
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

        // Validate comment length
        if (comment == null || comment.trim().length() < 10) {
            throw new BadRequestException("Comment must be at least 10 characters long");
        }

        // Check if user has already reviewed this car
        Review existingReview = reviewRepository.findByUserIdAndCarId(userId, carId).orElse(null);

        if (existingReview != null) {
            // Update existing review
            existingReview.setRating(rating);
            existingReview.setComment(comment);
            existingReview.setStatus(ReviewStatus.PENDING); // Reset to pending for moderation
            Review updatedReview = reviewRepository.save(existingReview);
            return convertToDto(updatedReview);
        } else {
            // Create new review
            Review newReview = new Review(user, car, rating, comment);
            Review savedReview = reviewRepository.save(newReview);
            return convertToDto(savedReview);
        }
    }

    /**
     * Get approved reviews for a car
     */
    public List<ReviewDto> getApprovedReviews(Long carId) {
        List<Review> reviews = reviewRepository.findApprovedReviewsByCarId(carId);
        return reviews.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all reviews for a car (admin/moderator only)
     */
    public List<ReviewDto> getAllCarReviews(Long carId) {
        List<Review> reviews = reviewRepository.findByCarId(carId);
        return reviews.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get user's review for a specific car
     */
    public ReviewDto getUserReview(Long userId, Long carId) {
        Review review = reviewRepository.findByUserIdAndCarId(userId, carId)
                .orElse(null);
        return review != null ? convertToDto(review) : null;
    }

    /**
     * Get pending reviews for moderation
     */
    public List<ReviewDto> getPendingReviews() {
        List<Review> reviews = reviewRepository.findPendingReviews();
        return reviews.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Moderate a review (approve/reject)
     */
    public ReviewDto moderateReview(Long reviewId, ReviewStatus status, String moderatorNotes, Long moderatorId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        User moderator = userRepository.findById(moderatorId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", moderatorId));

        review.setStatus(status);
        review.setModeratorNotes(moderatorNotes);
        review.setModeratedBy(moderator);
        review.setModeratedAt(LocalDateTime.now());

        Review moderatedReview = reviewRepository.save(review);
        return convertToDto(moderatedReview);
    }

    /**
     * Get average rating for a car from approved reviews
     */
    public Double getAverageRating(Long carId) {
        Double averageRating = reviewRepository.getAverageRatingByCarIdAndStatus(carId, ReviewStatus.APPROVED);
        return averageRating != null ? averageRating : 0.0;
    }

    /**
     * Get review count for a car by status
     */
    public long getReviewCount(Long carId, ReviewStatus status) {
        return reviewRepository.countByCarIdAndStatus(carId, status);
    }

    /**
     * Check if user has reviewed a car
     */
    public boolean hasUserReviewed(Long userId, Long carId) {
        return reviewRepository.existsByUserIdAndCarId(userId, carId);
    }

    /**
     * Delete a review
     */
    public void deleteReview(Long userId, Long carId) {
        Review review = reviewRepository.findByUserIdAndCarId(userId, carId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "user and car", userId + "-" + carId));
        reviewRepository.delete(review);
    }

    /**
     * Convert Review entity to DTO
     */
    private ReviewDto convertToDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getCar().getId(),
                review.getRating(),
                review.getComment(),
                review.getUser().getFullName(),
                review.getStatus(),
                review.getModeratorNotes(),
                review.getModeratedBy() != null ? review.getModeratedBy().getFullName() : null,
                review.getModeratedAt(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
} 