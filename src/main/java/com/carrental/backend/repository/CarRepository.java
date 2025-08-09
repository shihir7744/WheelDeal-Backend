package com.carrental.backend.repository;

import com.carrental.backend.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
    List<Car> findByAvailability(Boolean availability);
    
    @Query("SELECT DISTINCT c FROM Car c " +
           "LEFT JOIN FETCH c.images " +
           "LEFT JOIN FETCH c.reviews " +
           "LEFT JOIN FETCH c.branch")
    List<Car> findAllWithImagesAndReviews();
    
    List<Car> findByBranchId(Long branchId);
    
    List<Car> findByBrand(String brand);
    
    List<Car> findByType(String type);
    
    List<Car> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT DISTINCT c FROM Car c " +
           "LEFT JOIN FETCH c.images " +
           "LEFT JOIN FETCH c.reviews " +
           "LEFT JOIN FETCH c.branch " +
           "WHERE c.availability = true AND c.branch.city = :city")
    List<Car> findAvailableCarsByCity(@Param("city") String city);
    
    @Query("SELECT c FROM Car c WHERE c.availability = true AND c.branch.id = :branchId")
    List<Car> findAvailableCarsByBranchId(@Param("branchId") Long branchId);
    
    @Query("SELECT c FROM Car c WHERE c.brand LIKE CONCAT('%', :brand, '%')")
    List<Car> findByBrandContainingIgnoreCase(@Param("brand") String brand);
    
    @Query("SELECT c FROM Car c WHERE c.model LIKE CONCAT('%', :model, '%')")
    List<Car> findByModelContainingIgnoreCase(@Param("model") String model);
    
    @Query("SELECT DISTINCT c FROM Car c " +
           "LEFT JOIN FETCH c.images " +
           "LEFT JOIN FETCH c.reviews " +
           "LEFT JOIN FETCH c.branch " +
           "WHERE c.availability = true AND " +
           "(:brand IS NULL OR c.brand LIKE CONCAT('%', :brand, '%')) AND " +
           "(:model IS NULL OR c.model LIKE CONCAT('%', :model, '%')) AND " +
           "(:type IS NULL OR c.type LIKE CONCAT('%', :type, '%')) AND " +
           "(:minPrice IS NULL OR c.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
           "(:branchId IS NULL OR c.branch.id = :branchId)")
    List<Car> findAvailableCarsWithFilters(
        @Param("brand") String brand,
        @Param("model") String model,
        @Param("type") String type,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("branchId") Long branchId
    );
    
    @Query("SELECT c FROM Car c WHERE c.id NOT IN " +
           "(SELECT b.car.id FROM Booking b WHERE " +
           "b.status = 'ACTIVE' AND " +
           "((b.startDate <= :endDate AND b.endDate >= :startDate)))")
    List<Car> findAvailableCarsForDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(c) FROM Car c WHERE c.availability = true")
    long countByAvailabilityTrue();
    
    @Query("SELECT COUNT(c) FROM Car c WHERE c.branch.id = :branchId")
    long countByBranchId(@Param("branchId") Long branchId);
    
    @Query("SELECT COUNT(c) FROM Car c WHERE c.branch.id = :branchId AND c.availability = true")
    long countByBranchIdAndAvailabilityTrue(@Param("branchId") Long branchId);
    
    // Advanced search methods
    @Query("SELECT c FROM Car c WHERE " +
           "(:availableOnly IS NULL OR c.availability = :availableOnly) AND " +
           "(:brand IS NULL OR c.brand LIKE CONCAT('%', :brand, '%')) AND " +
           "(:model IS NULL OR c.model LIKE CONCAT('%', :model, '%')) AND " +
           "(:type IS NULL OR c.type LIKE CONCAT('%', :type, '%')) AND " +
           "(:fuelType IS NULL OR c.fuelType LIKE CONCAT('%', :fuelType, '%')) AND " +
           "(:transmission IS NULL OR c.transmission LIKE CONCAT('%', :transmission, '%')) AND " +
           "(:color IS NULL OR c.color LIKE CONCAT('%', :color, '%')) AND " +
           "(:engineSize IS NULL OR c.engineSize LIKE CONCAT('%', :engineSize, '%')) AND " +
           "(:minPrice IS NULL OR c.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
           "(:minYear IS NULL OR c.year >= :minYear) AND " +
           "(:maxYear IS NULL OR c.year <= :maxYear) AND " +
           "(:minSeatingCapacity IS NULL OR c.seatingCapacity >= :minSeatingCapacity) AND " +
           "(:maxSeatingCapacity IS NULL OR c.seatingCapacity <= :maxSeatingCapacity) AND " +
           "(:minRating IS NULL OR c.averageRating >= :minRating) AND " +
           "(:minRatingCount IS NULL OR c.ratingCount >= :minRatingCount) AND " +
           "(:branchId IS NULL OR c.branch.id = :branchId) AND " +
           "(:city IS NULL OR c.branch.city LIKE CONCAT('%', :city, '%'))")
    List<Car> findCarsWithAdvancedFilters(
        @Param("availableOnly") Boolean availableOnly,
        @Param("brand") String brand,
        @Param("model") String model,
        @Param("type") String type,
        @Param("fuelType") String fuelType,
        @Param("transmission") String transmission,
        @Param("color") String color,
        @Param("engineSize") String engineSize,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("minYear") Integer minYear,
        @Param("maxYear") Integer maxYear,
        @Param("minSeatingCapacity") Integer minSeatingCapacity,
        @Param("maxSeatingCapacity") Integer maxSeatingCapacity,
        @Param("minRating") Double minRating,
        @Param("minRatingCount") Integer minRatingCount,
        @Param("branchId") Long branchId,
        @Param("city") String city
    );
    
    // Find distinct values for filters
    @Query("SELECT DISTINCT c.brand FROM Car c WHERE c.brand IS NOT NULL ORDER BY c.brand")
    List<String> findDistinctBrands();
    
    @Query("SELECT DISTINCT c.type FROM Car c WHERE c.type IS NOT NULL ORDER BY c.type")
    List<String> findDistinctTypes();
    
    @Query("SELECT DISTINCT c.fuelType FROM Car c WHERE c.fuelType IS NOT NULL ORDER BY c.fuelType")
    List<String> findDistinctFuelTypes();
    
    @Query("SELECT DISTINCT c.transmission FROM Car c WHERE c.transmission IS NOT NULL ORDER BY c.transmission")
    List<String> findDistinctTransmissions();
    
    @Query("SELECT DISTINCT c.color FROM Car c WHERE c.color IS NOT NULL ORDER BY c.color")
    List<String> findDistinctColors();
    
    @Query("SELECT DISTINCT c.engineSize FROM Car c WHERE c.engineSize IS NOT NULL ORDER BY c.engineSize")
    List<String> findDistinctEngineSizes();
    
    // Enhanced car queries
    List<Car> findByIsFeaturedTrue();
    
    List<Car> findByIsNewArrivalTrue();
    
    List<Car> findByIsPopularTrue();
    
    @Query("SELECT DISTINCT c FROM Car c " +
           "LEFT JOIN FETCH c.images " +
           "LEFT JOIN FETCH c.reviews " +
           "LEFT JOIN FETCH c.branch " +
           "WHERE c.id = :id")
    Car findByIdWithImagesAndReviews(@Param("id") Long id);
}

