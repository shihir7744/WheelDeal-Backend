package com.carrental.backend.controller;

import com.carrental.backend.dto.BookingDto;
import com.carrental.backend.entities.BookingStatus;
import com.carrental.backend.entities.User;
import com.carrental.backend.exception.BadRequestException;
import com.carrental.backend.service.AuthService;
import com.carrental.backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AuthService authService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<BookingDto> createBooking(@RequestBody Map<String, Object> bookingRequest) {
        try {
            System.out.println("Received booking request: " + bookingRequest);

            // Basic payload validation
            if (bookingRequest == null || !bookingRequest.containsKey("carId")
                    || !bookingRequest.containsKey("startDate") || !bookingRequest.containsKey("endDate")) {
                throw new BadRequestException("carId, startDate and endDate are required");
            }

            User currentUser = authService.getCurrentUser();
            if (currentUser == null) {
                throw new BadRequestException("Unable to resolve current user from token");
            }
            System.out.println("Current user: " + currentUser.getFullName() + " (ID: " + currentUser.getId() + ")");

            Long carId;
            LocalDate startDate;
            LocalDate endDate;

            try {
                carId = Long.valueOf(String.valueOf(bookingRequest.get("carId")));
            } catch (NumberFormatException e) {
                throw new BadRequestException("Invalid carId");
            }

            try {
                DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
                startDate = LocalDate.parse(String.valueOf(bookingRequest.get("startDate")), fmt);
                endDate = LocalDate.parse(String.valueOf(bookingRequest.get("endDate")), fmt);
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Dates must be in ISO format YYYY-MM-DD");
            }

            System.out.println("Parsed booking data - Car ID: " + carId + ", Start: " + startDate + ", End: " + endDate);

            BookingDto booking = bookingService.createBooking(currentUser.getId(), carId, startDate, endDate);
            System.out.println("Booking created successfully with ID: " + booking.getId());
            return ResponseEntity.ok(booking);
        } catch (BadRequestException e) {
            // Re-throw to be handled by GlobalExceptionHandler with 400
            throw e;
        } catch (Exception e) {
            System.err.println("Error creating booking: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<List<BookingDto>> getUserBookings() {
        User currentUser = authService.getCurrentUser();
        List<BookingDto> bookings = bookingService.getUserBookings(currentUser.getId());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        BookingDto booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BookingDto> updateBookingStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusRequest) {

        BookingStatus status = BookingStatus.valueOf(statusRequest.get("status"));
        BookingDto updatedBooking = bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok(updatedBooking);
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BookingDto> activateBooking(@PathVariable Long id) {
        BookingDto updated = bookingService.updateBookingStatus(id, BookingStatus.ACTIVE);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BookingDto> completeBooking(@PathVariable Long id) {
        BookingDto updated = bookingService.updateBookingStatus(id, BookingStatus.COMPLETED);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BookingDto> cancelBookingAsManager(@PathVariable Long id) {
        BookingDto updated = bookingService.updateBookingStatus(id, BookingStatus.CANCELLED);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        User currentUser = authService.getCurrentUser();
        bookingService.cancelBooking(id, currentUser.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<BookingDto>> getBranchBookings(@PathVariable Long branchId) {
        List<BookingDto> bookings = bookingService.getBranchBookings(branchId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<BookingDto>> getBookingsByStatus(@PathVariable BookingStatus status) {
        List<BookingDto> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/dates")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<BookingDto>> getBookingsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<BookingDto> bookings = bookingService.getBookingsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(bookings);
    }

    /**
     * Check if a car is available for a specific date range
     */
    @GetMapping("/availability/{carId}")
    public ResponseEntity<Map<String, Object>> checkCarAvailability(
            @PathVariable Long carId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        boolean isAvailable = bookingService.isCarAvailableForDateRange(carId, startDate, endDate);
        List<BookingDto> conflictingBookings = bookingService.getConflictingBookings(carId, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("carId", carId);
        response.put("startDate", startDate);
        response.put("endDate", endDate);
        response.put("isAvailable", isAvailable);
        response.put("conflictingBookings", conflictingBookings);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }
}

