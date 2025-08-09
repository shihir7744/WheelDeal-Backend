package com.carrental.backend.service;

import com.carrental.backend.dto.AuthRequest;
import com.carrental.backend.dto.AuthResponse;
import com.carrental.backend.dto.RegisterRequest;
import com.carrental.backend.exception.BadRequestException;
import com.carrental.backend.entities.AuthProvider;
import com.carrental.backend.entities.Role;
import com.carrental.backend.entities.User;
import com.carrental.backend.repository.UserRepository;
import com.carrental.backend.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    public AuthResponse authenticateUser(AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(jwt, user.getId(), user.getEmail(), user.getFullName(), user.getRole());
    }

    public AuthResponse registerUser(RegisterRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use!");
        }

        // Creating user's account
        User user = new User();
        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setProvider(AuthProvider.LOCAL);
        user.setRole(Role.CUSTOMER);

        User result = userRepository.save(user);

        // Send welcome email
        try {
            emailService.sendWelcomeEmail(result.getEmail(), result.getFullName());
        } catch (Exception e) {
            // Log error but don't fail the registration
            System.err.println("Failed to send welcome email: " + e.getMessage());
        }

        // Generate JWT token
        String jwt = jwtUtils.generateTokenFromEmail(result.getEmail());

        return new AuthResponse(jwt, result.getId(), result.getEmail(), result.getFullName(), result.getRole());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

