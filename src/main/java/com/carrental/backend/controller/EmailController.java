package com.carrental.backend.controller;

import com.carrental.backend.dto.EmailRequest;
import com.carrental.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * Send a custom templated email
     */
    @PostMapping("/send")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendTemplatedEmail(emailRequest);
            return ResponseEntity.ok().body(Map.of("message", "Email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }

    /**
     * Test booking confirmation email
     */
    @PostMapping("/test/booking-confirmation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testBookingConfirmation(@RequestParam String to) {
        try {
            emailService.sendBookingConfirmation(
                to,
                "John Doe",
                "Toyota Camry 2023 - White",
                "Dec 15, 2024 - Dec 20, 2024",
                "$500.00",
                "#BK12345"
            );
            return ResponseEntity.ok().body(Map.of("message", "Booking confirmation email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }

    /**
     * Test welcome email
     */
    @PostMapping("/test/welcome")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testWelcomeEmail(@RequestParam String to) {
        try {
            emailService.sendWelcomeEmail(to, "John Doe");
            return ResponseEntity.ok().body(Map.of("message", "Welcome email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }

    /**
     * Test booking cancellation email
     */
    @PostMapping("/test/booking-cancellation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testBookingCancellation(@RequestParam String to) {
        try {
            emailService.sendBookingCancellation(
                to,
                "John Doe",
                "Toyota Camry 2023 - White",
                "Dec 15, 2024 - Dec 20, 2024",
                "#BK12345"
            );
            return ResponseEntity.ok().body(Map.of("message", "Booking cancellation email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }

    /**
     * Test password reset email
     */
    @PostMapping("/test/password-reset")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testPasswordReset(@RequestParam String to) {
        try {
            emailService.sendPasswordResetEmail(to, "John Doe", "reset-token-12345");
            return ResponseEntity.ok().body(Map.of("message", "Password reset email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }

    /**
     * Test booking reminder email
     */
    @PostMapping("/test/booking-reminder")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testBookingReminder(@RequestParam String to) {
        try {
            emailService.sendBookingReminder(
                to,
                "John Doe",
                "Toyota Camry 2023 - White",
                "Dec 15, 2024 - Dec 20, 2024",
                "WheelDeal Downtown Branch"
            );
            return ResponseEntity.ok().body(Map.of("message", "Booking reminder email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }
} 