package com.carrental.backend.controller;

import com.carrental.backend.dto.BookingConfirmationDto;
import com.carrental.backend.dto.BookingDto;
import com.carrental.backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-confirmation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookingConfirmationController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<BookingDto>> getPendingBookings() {
        List<BookingDto> pendingBookings = bookingService.getPendingBookings();
        return ResponseEntity.ok(pendingBookings);
    }

    @GetMapping("/pending/branch/{branchId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<BookingDto>> getPendingBookingsByBranch(@PathVariable Long branchId) {
        List<BookingDto> pendingBookings = bookingService.getPendingBookingsByBranch(branchId);
        return ResponseEntity.ok(pendingBookings);
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BookingDto> confirmBooking(@RequestBody BookingConfirmationDto confirmationDto) {
        Long adminId = getCurrentUserId();
        BookingDto confirmedBooking = bookingService.confirmBooking(
            confirmationDto.getBookingId(), 
            adminId, 
            confirmationDto.getAdminNotes()
        );
        return ResponseEntity.ok(confirmedBooking);
    }

    @PostMapping("/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BookingDto> rejectBooking(@RequestBody BookingConfirmationDto confirmationDto) {
        Long adminId = getCurrentUserId();
        BookingDto rejectedBooking = bookingService.rejectBooking(
            confirmationDto.getBookingId(), 
            adminId, 
            confirmationDto.getAdminNotes()
        );
        return ResponseEntity.ok(rejectedBooking);
    }

    @GetMapping("/pending/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Long> getPendingBookingsCount() {
        long count = bookingService.getPendingBookingsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/pending/count/branch/{branchId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Long> getPendingBookingsCountByBranch(@PathVariable Long branchId) {
        long count = bookingService.getPendingBookingsCountByBranch(branchId);
        return ResponseEntity.ok(count);
    }

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