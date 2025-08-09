package com.carrental.backend.service;

import com.carrental.backend.dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.email.from-name:WheelDeal Car Rental}")
    private String fromName;

    /**
     * Send a templated email
     */
    public void sendTemplatedEmail(EmailRequest emailRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Set email details
            helper.setFrom(fromEmail, fromName);
            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());

            // Process template
            Context context = new Context();
            if (emailRequest.getTemplateData() != null) {
                for (Map.Entry<String, Object> entry : emailRequest.getTemplateData().entrySet()) {
                    context.setVariable(entry.getKey(), entry.getValue());
                }
            }

            String htmlContent = templateEngine.process(emailRequest.getTemplateName(), context);
            helper.setText(htmlContent, true);

            // Send email
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", emailRequest.getTo());

        } catch (Exception e) {
            logger.error("Failed to send email to: {}", emailRequest.getTo(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Send booking confirmation email
     */
    public void sendBookingConfirmation(String to, String customerName, String carDetails, 
                                       String bookingDates, String totalAmount, String bookingId) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject("Booking Confirmation - WheelDeal Car Rental");
        emailRequest.setTemplateName("booking-confirmation");
        
        Map<String, Object> templateData = Map.of(
            "customerName", customerName,
            "carDetails", carDetails,
            "bookingDates", bookingDates,
            "totalAmount", totalAmount,
            "bookingId", bookingId
        );
        emailRequest.setTemplateData(templateData);

        sendTemplatedEmail(emailRequest);
    }

    /**
     * Send booking cancellation email
     */
    public void sendBookingCancellation(String to, String customerName, String carDetails, 
                                       String bookingDates, String bookingId) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject("Booking Cancellation - WheelDeal Car Rental");
        emailRequest.setTemplateName("booking-cancellation");
        
        Map<String, Object> templateData = Map.of(
            "customerName", customerName,
            "carDetails", carDetails,
            "bookingDates", bookingDates,
            "bookingId", bookingId
        );
        emailRequest.setTemplateData(templateData);

        sendTemplatedEmail(emailRequest);
    }

    /**
     * Send welcome email
     */
    public void sendWelcomeEmail(String to, String customerName) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject("Welcome to WheelDeal Car Rental");
        emailRequest.setTemplateName("welcome");
        
        Map<String, Object> templateData = Map.of(
            "customerName", customerName
        );
        emailRequest.setTemplateData(templateData);

        sendTemplatedEmail(emailRequest);
    }

    /**
     * Send password reset email
     */
    public void sendPasswordResetEmail(String to, String customerName, String resetToken) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject("Password Reset - WheelDeal Car Rental");
        emailRequest.setTemplateName("password-reset");
        
        Map<String, Object> templateData = Map.of(
            "customerName", customerName,
            "resetToken", resetToken
        );
        emailRequest.setTemplateData(templateData);

        sendTemplatedEmail(emailRequest);
    }

    /**
     * Send booking reminder email
     */
    public void sendBookingReminder(String to, String customerName, String carDetails, 
                                   String bookingDates, String pickupLocation) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject("Booking Reminder - WheelDeal Car Rental");
        emailRequest.setTemplateName("booking-reminder");
        
        Map<String, Object> templateData = Map.of(
            "customerName", customerName,
            "carDetails", carDetails,
            "bookingDates", bookingDates,
            "pickupLocation", pickupLocation
        );
        emailRequest.setTemplateData(templateData);

        sendTemplatedEmail(emailRequest);
    }

    /**
     * Send booking confirmed email (admin confirmation)
     */
    public void sendBookingConfirmed(String to, String customerName, String carDetails, 
                                   String bookingDates, String totalAmount, String bookingId,
                                   String pickupLocation, String confirmedBy, String confirmedAt) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject("Booking Confirmed - WheelDeal Car Rental");
        emailRequest.setTemplateName("booking-confirmed");
        
        Map<String, Object> templateData = Map.of(
            "customerName", customerName,
            "carDetails", carDetails,
            "bookingDates", bookingDates,
            "totalAmount", totalAmount,
            "bookingId", bookingId,
            "pickupLocation", pickupLocation,
            "confirmedBy", confirmedBy,
            "confirmedAt", confirmedAt
        );
        emailRequest.setTemplateData(templateData);

        sendTemplatedEmail(emailRequest);
    }

    /**
     * Send booking rejected email (admin rejection)
     */
    public void sendBookingRejected(String to, String customerName, String carDetails, 
                                  String bookingDates, String bookingId, String pickupLocation,
                                  String adminNotes) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject("Booking Update - WheelDeal Car Rental");
        emailRequest.setTemplateName("booking-rejected");
        
        Map<String, Object> templateData = Map.of(
            "customerName", customerName,
            "carDetails", carDetails,
            "bookingDates", bookingDates,
            "bookingId", bookingId,
            "pickupLocation", pickupLocation,
            "adminNotes", adminNotes != null ? adminNotes : "No additional information provided."
        );
        emailRequest.setTemplateData(templateData);

        sendTemplatedEmail(emailRequest);
    }
} 