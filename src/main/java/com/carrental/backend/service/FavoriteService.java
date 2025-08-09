package com.carrental.backend.service;

import com.carrental.backend.dto.CarDto;
import com.carrental.backend.dto.FavoriteDto;
import com.carrental.backend.entities.Car;
import com.carrental.backend.entities.Favorite;
import com.carrental.backend.entities.User;
import com.carrental.backend.exception.BadRequestException;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.repository.CarRepository;
import com.carrental.backend.repository.FavoriteRepository;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    // Add a car to favorites
    public FavoriteDto addToFavorites(Long userId, Long carId) {
        // Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Check if car exists
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));

        // Check if already favorited
        if (favoriteRepository.existsByUserIdAndCarId(userId, carId)) {
            throw new BadRequestException("Car is already in favorites");
        }

        // Create and save favorite
        Favorite favorite = new Favorite(user, car);
        Favorite savedFavorite = favoriteRepository.save(favorite);

        return convertToDto(savedFavorite);
    }

    // Remove a car from favorites
    public void removeFromFavorites(Long userId, Long carId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        // Check if car exists
        if (!carRepository.existsById(carId)) {
            throw new ResourceNotFoundException("Car", "id", carId);
        }

        // Check if favorite exists
        if (!favoriteRepository.existsByUserIdAndCarId(userId, carId)) {
            throw new BadRequestException("Car is not in favorites");
        }

        // Delete favorite
        favoriteRepository.deleteByUserIdAndCarId(userId, carId);
    }

    // Get all favorites for a user
    public List<FavoriteDto> getUserFavorites(Long userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        return favorites.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get favorite cars for a user (just the cars, not the favorite objects)
    public List<CarDto> getUserFavoriteCars(Long userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        List<Long> carIds = favoriteRepository.findCarIdsByUserId(userId);
        return carIds.stream()
                .map(carId -> carService.getCarById(carId))
                .collect(Collectors.toList());
    }

    // Check if a car is favorited by a user
    public boolean isCarFavorited(Long userId, Long carId) {
        return favoriteRepository.existsByUserIdAndCarId(userId, carId);
    }

    // Get favorite count for a user
    public long getFavoriteCount(Long userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        return favoriteRepository.countByUserId(userId);
    }

    // Toggle favorite status (add if not favorited, remove if favorited)
    public FavoriteDto toggleFavorite(Long userId, Long carId) {
        if (isCarFavorited(userId, carId)) {
            removeFromFavorites(userId, carId);
            return null; // Return null to indicate removal
        } else {
            return addToFavorites(userId, carId);
        }
    }

    // Convert Favorite entity to DTO
    private FavoriteDto convertToDto(Favorite favorite) {
        CarDto carDto = carService.getCarById(favorite.getCar().getId());
        
        return new FavoriteDto(
                favorite.getId(),
                favorite.getUser().getId(),
                favorite.getCar().getId(),
                carDto,
                favorite.getCreatedAt()
        );
    }
} 