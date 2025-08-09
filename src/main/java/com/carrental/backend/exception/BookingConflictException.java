package com.carrental.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookingConflictException extends RuntimeException {
    
    public BookingConflictException(String message) {
        super(message);
    }
    
    public BookingConflictException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static BookingConflictException carNotAvailable(Long carId, String startDate, String endDate) {
        return new BookingConflictException(
            String.format("Car with ID %d is not available for the selected date range (%s to %s)", 
                         carId, startDate, endDate)
        );
    }
    
    public static BookingConflictException dateRangeConflict(Long carId, String conflictingStartDate, String conflictingEndDate) {
        return new BookingConflictException(
            String.format("Car with ID %d has conflicting bookings from %s to %s", 
                         carId, conflictingStartDate, conflictingEndDate)
        );
    }
} 