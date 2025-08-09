package com.carrental.backend.dto;

import java.math.BigDecimal;

public class DashboardStatsDto {
    private long totalCars;
    private long availableCars;
    private long totalUsers;
    private long totalBookings;
    private long activeBookings;
    private long pendingBookings;
    private long confirmedBookings;
    private long completedBookings;
    private long cancelledBookings;
    private BigDecimal totalRevenue;
    private BigDecimal monthlyRevenue;
    private BigDecimal averageBookingValue;
    private double fleetUtilizationRate;
    private long recentBookings;
    private long newUsersThisMonth;

    // Default constructor
    public DashboardStatsDto() {}

    // Getters and Setters
    public long getTotalCars() {
        return totalCars;
    }

    public void setTotalCars(long totalCars) {
        this.totalCars = totalCars;
    }

    public long getAvailableCars() {
        return availableCars;
    }

    public void setAvailableCars(long availableCars) {
        this.availableCars = availableCars;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getActiveBookings() {
        return activeBookings;
    }

    public void setActiveBookings(long activeBookings) {
        this.activeBookings = activeBookings;
    }

    public long getPendingBookings() {
        return pendingBookings;
    }

    public void setPendingBookings(long pendingBookings) {
        this.pendingBookings = pendingBookings;
    }

    public long getConfirmedBookings() {
        return confirmedBookings;
    }

    public void setConfirmedBookings(long confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }

    public long getCompletedBookings() {
        return completedBookings;
    }

    public void setCompletedBookings(long completedBookings) {
        this.completedBookings = completedBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(BigDecimal monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public BigDecimal getAverageBookingValue() {
        return averageBookingValue;
    }

    public void setAverageBookingValue(BigDecimal averageBookingValue) {
        this.averageBookingValue = averageBookingValue;
    }

    public double getFleetUtilizationRate() {
        return fleetUtilizationRate;
    }

    public void setFleetUtilizationRate(double fleetUtilizationRate) {
        this.fleetUtilizationRate = fleetUtilizationRate;
    }

    public long getRecentBookings() {
        return recentBookings;
    }

    public void setRecentBookings(long recentBookings) {
        this.recentBookings = recentBookings;
    }

    public long getNewUsersThisMonth() {
        return newUsersThisMonth;
    }

    public void setNewUsersThisMonth(long newUsersThisMonth) {
        this.newUsersThisMonth = newUsersThisMonth;
    }
} 