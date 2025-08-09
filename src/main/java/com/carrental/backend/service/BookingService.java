package com.carrental.backend.service;

import com.carrental.backend.dto.BookingDto;
import com.carrental.backend.exception.BadRequestException;
import com.carrental.backend.exception.BookingConflictException;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.entities.Booking;
import com.carrental.backend.entities.BookingStatus;
import com.carrental.backend.entities.Car;
import com.carrental.backend.entities.User;
import com.carrental.backend.repository.BookingRepository;
import com.carrental.backend.repository.CarRepository;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public BookingDto createBooking(Long userId, Long carId, LocalDate startDate, LocalDate endDate) {
        // Validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Validate car
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));

        // Validate dates
        validateBookingDates(startDate, endDate);
        
        // Check if car is available
        System.out.println("Checking car availability for car ID: " + carId + ", Availability: " + car.getAvailability());
        if (!car.getAvailability()) {
            throw new BookingConflictException("Car is not available for booking");
        }

        // Check for date conflicts with detailed information
        System.out.println("Checking for conflicting bookings for car ID: " + carId + " from " + startDate + " to " + endDate);
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(carId, startDate, endDate);
        System.out.println("Found " + conflictingBookings.size() + " conflicting bookings");
        if (!conflictingBookings.isEmpty()) {
            Booking conflictingBooking = conflictingBookings.get(0);
            System.out.println("Conflict found: " + conflictingBooking.getStartDate() + " to " + conflictingBooking.getEndDate());
            throw BookingConflictException.dateRangeConflict(
                carId, 
                conflictingBooking.getStartDate().toString(), 
                conflictingBooking.getEndDate().toString()
            );
        }

        // Create booking
        System.out.println("Creating new booking for user: " + user.getFullName() + ", car: " + car.getBrand() + " " + car.getModel());
        Booking booking = new Booking(user, car, startDate, endDate);
        System.out.println("Booking object created, saving to database...");
        Booking savedBooking = bookingRepository.save(booking);
        System.out.println("Booking saved successfully with ID: " + savedBooking.getId());

        // Send booking confirmation email
        try {
            String carDetails = car.getBrand() + " " + car.getModel() + " (" + car.getYear() + ")";
            String bookingDates = startDate.toString() + " to " + endDate.toString();
            
            // Check if car price is not null before calculation
            String totalAmount = "N/A";
            if (car.getPrice() != null) {
                totalAmount = "$" + car.getPrice().multiply(java.math.BigDecimal.valueOf(
                    java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1
                )).toString();
            }
            
            String bookingId = "#BK" + savedBooking.getId();
            
            // Temporarily comment out email sending to debug booking issue
            // emailService.sendBookingConfirmation(
            //     user.getEmail(),
            //     user.getFullName(),
            //     carDetails,
            //     bookingDates,
            //     totalAmount,
            //     bookingId
            // );
            System.out.println("Booking created successfully. Email sending temporarily disabled for debugging.");
            System.out.println("Booking details: " + carDetails + " for " + bookingDates + " - Total: " + totalAmount);
        } catch (Exception e) {
            // Log error but don't fail the booking creation
            System.err.println("Failed to send booking confirmation email: " + e.getMessage());
        }

        return convertToDto(savedBooking);
    }
    
    /**
     * Validates booking dates for business rules
     */
    private void validateBookingDates(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        
        // Check if start date is in the past
        if (startDate.isBefore(today)) {
            throw new BadRequestException("Start date cannot be in the past");
        }
        
        // Check if end date is before start date
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }
        
        // Check if booking is too far in the future (optional business rule)
        if (startDate.isAfter(today.plusYears(1))) {
            throw new BadRequestException("Bookings cannot be made more than 1 year in advance");
        }
        
        // Check if booking duration is reasonable (optional business rule)
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween > 30) {
            throw new BadRequestException("Bookings cannot exceed 30 days");
        }
    }

    public List<BookingDto> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        return convertToDto(booking);
    }

    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingDto updateBookingStatus(Long id, BookingStatus targetStatus) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        BookingStatus current = booking.getStatus();

        // Validate allowed transitions
        boolean allowed = switch (current) {
            case PENDING -> (targetStatus == BookingStatus.CONFIRMED || targetStatus == BookingStatus.CANCELLED);
            case CONFIRMED -> (targetStatus == BookingStatus.ACTIVE || targetStatus == BookingStatus.CANCELLED);
            case ACTIVE -> (targetStatus == BookingStatus.COMPLETED || targetStatus == BookingStatus.CANCELLED);
            case COMPLETED, CANCELLED -> false;
            default -> false;
        };

        if (!allowed) {
            throw new BadRequestException("Invalid status transition from " + current + " to " + targetStatus);
        }

        booking.setStatus(targetStatus);

        // Side effects on car availability
        Car car = booking.getCar();
        if (targetStatus == BookingStatus.ACTIVE) {
            car.setAvailability(false);
            carRepository.save(car);
        }
        if (targetStatus == BookingStatus.COMPLETED || targetStatus == BookingStatus.CANCELLED) {
            car.setAvailability(true);
            carRepository.save(car);
        }

        Booking updatedBooking = bookingRepository.save(booking);
        return convertToDto(updatedBooking);
    }

    public void cancelBooking(Long id, Long userId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        // Check if the booking belongs to the user
        if (!booking.getUser().getId().equals(userId)) {
            throw new BadRequestException("You can only cancel your own bookings");
        }

        // Check if booking can be cancelled
        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new BadRequestException("Cannot cancel a completed booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        // Send booking cancellation email
        try {
            String carDetails = booking.getCar().getBrand() + " " + booking.getCar().getModel() + " (" + booking.getCar().getYear() + ")";
            String bookingDates = booking.getStartDate().toString() + " to " + booking.getEndDate().toString();
            String bookingId = "#BK" + booking.getId();
            
            emailService.sendBookingCancellation(
                booking.getUser().getEmail(),
                booking.getUser().getFullName(),
                carDetails,
                bookingDates,
                bookingId
            );
        } catch (Exception e) {
            // Log error but don't fail the booking cancellation
            System.err.println("Failed to send booking cancellation email: " + e.getMessage());
        }
    }

    public List<BookingDto> getBranchBookings(Long branchId) {
        return bookingRepository.findByBranchId(branchId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return bookingRepository.findBookingsBetweenDates(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Check if a car is available for a specific date range
     */
    public boolean isCarAvailableForDateRange(Long carId, LocalDate startDate, LocalDate endDate) {
        // Check if car exists and is generally available
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));
        
        if (!car.getAvailability()) {
            return false;
        }
        
        // Check for conflicting bookings
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(carId, startDate, endDate);
        System.out.println("Checking availability for car " + carId + " - Found " + conflictingBookings.size() + " conflicting bookings");
        return conflictingBookings.isEmpty();
    }
    
    /**
     * Get conflicting bookings for a car in a date range
     */
    public List<BookingDto> getConflictingBookings(Long carId, LocalDate startDate, LocalDate endDate) {
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(carId, startDate, endDate);
        return conflictingBookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Confirmation workflow methods
    public List<BookingDto> getPendingBookings() {
        return bookingRepository.findPendingBookings().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getPendingBookingsByBranch(Long branchId) {
        return bookingRepository.findPendingBookingsByBranch(branchId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingDto confirmBooking(Long bookingId, Long adminId, String adminNotes) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only pending bookings can be confirmed");
        }

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setAdminNotes(adminNotes);
        booking.setConfirmedAt(java.time.LocalDateTime.now());
        booking.setConfirmedBy(admin);

        Booking savedBooking = bookingRepository.save(booking);

        // Send confirmation email
        try {
            String carDetails = booking.getCar().getBrand() + " " + booking.getCar().getModel() + " (" + booking.getCar().getYear() + ")";
            String bookingDates = booking.getStartDate().toString() + " to " + booking.getEndDate().toString();
            String bookingIdStr = "#BK" + booking.getId();
            String pickupLocation = booking.getCar().getBranch().getName() + ", " + booking.getCar().getBranch().getCity();
            String confirmedBy = admin.getFullName();
            String confirmedAt = booking.getConfirmedAt().toString();
            String totalAmount = "$" + booking.getCar().getPrice().multiply(java.math.BigDecimal.valueOf(
                java.time.temporal.ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate()) + 1
            )).toString();
            
            emailService.sendBookingConfirmed(
                booking.getUser().getEmail(),
                booking.getUser().getFullName(),
                carDetails,
                bookingDates,
                totalAmount,
                bookingIdStr,
                pickupLocation,
                confirmedBy,
                confirmedAt
            );
        } catch (Exception e) {
            System.err.println("Failed to send booking confirmation email: " + e.getMessage());
        }

        return convertToDto(savedBooking);
    }

    public BookingDto rejectBooking(Long bookingId, Long adminId, String adminNotes) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only pending bookings can be rejected");
        }

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));

        booking.setStatus(BookingStatus.REJECTED);
        booking.setAdminNotes(adminNotes);
        booking.setConfirmedAt(java.time.LocalDateTime.now());
        booking.setConfirmedBy(admin);

        Booking savedBooking = bookingRepository.save(booking);

        // Send rejection email
        try {
            String carDetails = booking.getCar().getBrand() + " " + booking.getCar().getModel() + " (" + booking.getCar().getYear() + ")";
            String bookingDates = booking.getStartDate().toString() + " to " + booking.getEndDate().toString();
            String bookingIdStr = "#BK" + booking.getId();
            String pickupLocation = booking.getCar().getBranch().getName() + ", " + booking.getCar().getBranch().getCity();
            
            emailService.sendBookingRejected(
                booking.getUser().getEmail(),
                booking.getUser().getFullName(),
                carDetails,
                bookingDates,
                bookingIdStr,
                pickupLocation,
                booking.getAdminNotes()
            );
        } catch (Exception e) {
            System.err.println("Failed to send booking rejection email: " + e.getMessage());
        }

        return convertToDto(savedBooking);
    }

    public long getPendingBookingsCount() {
        return bookingRepository.countPendingBookings();
    }

    public long getPendingBookingsCountByBranch(Long branchId) {
        return bookingRepository.countPendingBookingsByBranch(branchId);
    }

    private BookingDto convertToDto(Booking booking) {
        try {
            System.out.println("Converting booking to DTO - ID: " + booking.getId());
            BookingDto dto = new BookingDto(
                    booking.getId(),
                    booking.getUser().getId(),
                    booking.getUser().getFullName(),
                    booking.getUser().getEmail(),
                    booking.getCar().getId(),
                    booking.getCar().getBrand(),
                    booking.getCar().getModel(),
                    booking.getCar().getType(),
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getStatus(),
                    booking.getAdminNotes(),
                    booking.getConfirmedAt(),
                    booking.getConfirmedBy() != null ? booking.getConfirmedBy().getFullName() : null,
                    booking.getCreatedAt()
            );
            System.out.println("DTO conversion successful");
            return dto;
        } catch (Exception e) {
            System.err.println("Error converting booking to DTO: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

