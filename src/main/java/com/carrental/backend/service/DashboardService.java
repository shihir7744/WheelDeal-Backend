package com.carrental.backend.service;

import com.carrental.backend.dto.DashboardStatsDto;
import com.carrental.backend.entities.BookingStatus;
import com.carrental.backend.repository.BookingRepository;
import com.carrental.backend.repository.CarRepository;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class DashboardService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get comprehensive dashboard statistics for admin
     */
    public DashboardStatsDto getAdminDashboardStats() {
        DashboardStatsDto stats = new DashboardStatsDto();
        
        // Basic counts
        stats.setTotalCars(carRepository.count());
        stats.setAvailableCars(carRepository.countByAvailabilityTrue());
        stats.setTotalUsers(userRepository.count());
        stats.setTotalBookings(bookingRepository.count());
        
        // Booking statistics
        stats.setActiveBookings(bookingRepository.countByStatus(BookingStatus.ACTIVE));
        stats.setPendingBookings(bookingRepository.countByStatus(BookingStatus.PENDING));
        stats.setConfirmedBookings(bookingRepository.countByStatus(BookingStatus.CONFIRMED));
        stats.setCompletedBookings(bookingRepository.countByStatus(BookingStatus.COMPLETED));
        stats.setCancelledBookings(bookingRepository.countByStatus(BookingStatus.CANCELLED));
        
        // Revenue calculations
        stats.setTotalRevenue(calculateTotalRevenue());
        stats.setMonthlyRevenue(calculateMonthlyRevenue());
        stats.setAverageBookingValue(calculateAverageBookingValue());
        
        // Fleet utilization
        stats.setFleetUtilizationRate(calculateFleetUtilization());
        
        // Recent activity
        stats.setRecentBookings(getRecentBookingsCount());
        stats.setNewUsersThisMonth(getNewUsersThisMonth());
        
        return stats;
    }

    /**
     * Get dashboard statistics for a specific branch manager
     */
    public DashboardStatsDto getManagerDashboardStats(Long branchId) {
        DashboardStatsDto stats = new DashboardStatsDto();
        
        // Branch-specific counts
        stats.setTotalCars(carRepository.countByBranchId(branchId));
        stats.setAvailableCars(carRepository.countByBranchIdAndAvailabilityTrue(branchId));
        
        // Branch-specific bookings
        stats.setTotalBookings(bookingRepository.countByBranchId(branchId));
        stats.setActiveBookings(bookingRepository.countByBranchIdAndStatus(branchId, BookingStatus.ACTIVE));
        stats.setPendingBookings(bookingRepository.countByBranchIdAndStatus(branchId, BookingStatus.PENDING));
        stats.setConfirmedBookings(bookingRepository.countByBranchIdAndStatus(branchId, BookingStatus.CONFIRMED));
        stats.setCompletedBookings(bookingRepository.countByBranchIdAndStatus(branchId, BookingStatus.COMPLETED));
        stats.setCancelledBookings(bookingRepository.countByBranchIdAndStatus(branchId, BookingStatus.CANCELLED));
        
        // Branch-specific revenue
        stats.setTotalRevenue(calculateBranchRevenue(branchId));
        stats.setMonthlyRevenue(calculateBranchMonthlyRevenue(branchId));
        
        // Branch utilization
        stats.setFleetUtilizationRate(calculateBranchUtilization(branchId));
        
        return stats;
    }

    /**
     * Calculate total revenue from all completed bookings
     */
    private BigDecimal calculateTotalRevenue() {
        // This would need a more complex query to calculate actual revenue
        // For now, we'll use a placeholder calculation
        long completedBookings = bookingRepository.countByStatus(BookingStatus.COMPLETED);
        return BigDecimal.valueOf(completedBookings * 100); // Placeholder: $100 per booking
    }

    /**
     * Calculate monthly revenue
     */
    private BigDecimal calculateMonthlyRevenue() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        long monthlyBookings = bookingRepository.countByStatusAndCreatedAtAfter(
            BookingStatus.COMPLETED, startOfMonth.atStartOfDay());
        return BigDecimal.valueOf(monthlyBookings * 100); // Placeholder calculation
    }

    /**
     * Calculate average booking value
     */
    private BigDecimal calculateAverageBookingValue() {
        long totalBookings = bookingRepository.count();
        if (totalBookings == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalRevenue = calculateTotalRevenue();
        return totalRevenue.divide(BigDecimal.valueOf(totalBookings), 2, java.math.RoundingMode.HALF_UP);
    }

    /**
     * Calculate fleet utilization rate
     */
    private double calculateFleetUtilization() {
        long totalCars = carRepository.count();
        if (totalCars == 0) {
            return 0.0;
        }
        long activeBookings = bookingRepository.countByStatus(BookingStatus.ACTIVE);
        return (double) activeBookings / totalCars * 100;
    }

    /**
     * Get count of recent bookings (last 7 days)
     */
    private long getRecentBookingsCount() {
        LocalDateTime weekAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        return bookingRepository.countByCreatedAtAfter(weekAgo);
    }

    /**
     * Get count of new users this month
     */
    private long getNewUsersThisMonth() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        return userRepository.countByCreatedAtAfter(startOfMonth.atStartOfDay());
    }

    /**
     * Calculate branch-specific revenue
     */
    private BigDecimal calculateBranchRevenue(Long branchId) {
        long branchBookings = bookingRepository.countByBranchIdAndStatus(branchId, BookingStatus.COMPLETED);
        return BigDecimal.valueOf(branchBookings * 100); // Placeholder calculation
    }

    /**
     * Calculate branch monthly revenue
     */
    private BigDecimal calculateBranchMonthlyRevenue(Long branchId) {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        long monthlyBookings = bookingRepository.countByBranchIdAndStatusAndCreatedAtAfter(
            branchId, BookingStatus.COMPLETED, startOfMonth.atStartOfDay());
        return BigDecimal.valueOf(monthlyBookings * 100); // Placeholder calculation
    }

    /**
     * Calculate branch-specific utilization
     */
    private double calculateBranchUtilization(Long branchId) {
        long totalCars = carRepository.countByBranchId(branchId);
        if (totalCars == 0) {
            return 0.0;
        }
        long activeBookings = bookingRepository.countByBranchIdAndStatus(branchId, BookingStatus.ACTIVE);
        return (double) activeBookings / totalCars * 100;
    }
} 