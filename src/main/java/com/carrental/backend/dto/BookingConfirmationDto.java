package com.carrental.backend.dto;

import java.time.LocalDateTime;

public class BookingConfirmationDto {
    private Long bookingId;
    private String status; // CONFIRMED, REJECTED
    private String adminNotes;
    private LocalDateTime confirmedAt;
    private String confirmedBy;

    public BookingConfirmationDto() {}

    public BookingConfirmationDto(Long bookingId, String status, String adminNotes) {
        this.bookingId = bookingId;
        this.status = status;
        this.adminNotes = adminNotes;
    }

    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    @Override
    public String toString() {
        return "BookingConfirmationDto{" +
                "bookingId=" + bookingId +
                ", status='" + status + '\'' +
                ", adminNotes='" + adminNotes + '\'' +
                ", confirmedAt=" + confirmedAt +
                ", confirmedBy='" + confirmedBy + '\'' +
                '}';
    }
} 