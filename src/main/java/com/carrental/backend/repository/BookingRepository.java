package com.carrental.backend.repository;

import com.carrental.backend.entities.Booking;
import com.carrental.backend.entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByUserId(Long userId);
    
    List<Booking> findByCarId(Long carId);
    
    List<Booking> findByStatus(BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.car.branch.id = :branchId")
    List<Booking> findByBranchId(@Param("branchId") Long branchId);
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.status = :status")
    List<Booking> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.car.id = :carId AND b.status IN ('ACTIVE', 'PENDING', 'CONFIRMED') AND " +
           "((b.startDate <= :endDate AND b.endDate >= :startDate))")
    List<Booking> findConflictingBookings(@Param("carId") Long carId, 
                                         @Param("startDate") LocalDate startDate, 
                                         @Param("endDate") LocalDate endDate);
    
    @Query("SELECT b FROM Booking b WHERE b.car.branch.id = :branchId AND b.status = :status")
    List<Booking> findByBranchIdAndStatus(@Param("branchId") Long branchId, @Param("status") BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.startDate >= :startDate AND b.endDate <= :endDate")
    List<Booking> findBookingsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    long countByStatus(@Param("status") BookingStatus status);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.car.branch.id = :branchId")
    long countByBranchId(@Param("branchId") Long branchId);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.car.branch.id = :branchId AND b.status = :status")
    long countByBranchIdAndStatus(@Param("branchId") Long branchId, @Param("status") BookingStatus status);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.createdAt >= :dateTime")
    long countByCreatedAtAfter(@Param("dateTime") LocalDateTime dateTime);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status AND b.createdAt >= :dateTime")
    long countByStatusAndCreatedAtAfter(@Param("status") BookingStatus status, @Param("dateTime") LocalDateTime dateTime);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.car.branch.id = :branchId AND b.status = :status AND b.createdAt >= :dateTime")
    long countByBranchIdAndStatusAndCreatedAtAfter(@Param("branchId") Long branchId, @Param("status") BookingStatus status, @Param("dateTime") LocalDateTime dateTime);
    
    // Confirmation workflow queries
    @Query("SELECT b FROM Booking b WHERE b.status = 'PENDING' ORDER BY b.createdAt ASC")
    List<Booking> findPendingBookings();
    
    @Query("SELECT b FROM Booking b WHERE b.car.branch.id = :branchId AND b.status = 'PENDING' ORDER BY b.createdAt ASC")
    List<Booking> findPendingBookingsByBranch(@Param("branchId") Long branchId);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'PENDING'")
    long countPendingBookings();
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.car.branch.id = :branchId AND b.status = 'PENDING'")
    long countPendingBookingsByBranch(@Param("branchId") Long branchId);
}

