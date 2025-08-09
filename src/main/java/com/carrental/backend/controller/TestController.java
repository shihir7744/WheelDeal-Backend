package com.carrental.backend.controller;

import com.carrental.backend.repository.UserRepository;
import com.carrental.backend.entities.User;
import com.carrental.backend.dto.CarDto;
import com.carrental.backend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CarService carService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is running");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> databaseCheck() {
        Map<String, Object> response = new HashMap<>();
        try {
            long userCount = userRepository.count();
            response.put("status", "SUCCESS");
            response.put("message", "Database connection is working");
            response.put("userCount", userCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Database connection failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> listUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            var users = userRepository.findAll();
            response.put("status", "SUCCESS");
            response.put("userCount", users.size());
            response.put("users", users.stream().map(user -> {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", user.getId());
                userMap.put("email", user.getEmail());
                userMap.put("fullName", user.getFullName());
                userMap.put("role", user.getRole());
                return userMap;
            }).toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Failed to fetch users: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @GetMapping("/password-test")
    public ResponseEntity<Map<String, Object>> passwordTest() {
        Map<String, Object> response = new HashMap<>();
        try {
            // Test password encoding
            String testPassword = "admin123";
            String encodedPassword = passwordEncoder.encode(testPassword);
            
            // Get admin user
            var adminUser = userRepository.findByEmail("admin@wheeldeal.com");
            
            response.put("status", "SUCCESS");
            response.put("testPassword", testPassword);
            response.put("encodedTestPassword", encodedPassword);
            response.put("adminExists", adminUser.isPresent());
            
            if (adminUser.isPresent()) {
                User admin = adminUser.get();
                response.put("adminPassword", admin.getPassword());
                response.put("passwordMatches", passwordEncoder.matches(testPassword, admin.getPassword()));
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Password test failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/cars/simple")
    public ResponseEntity<List<CarDto>> getSimpleCars() {
        try {
            // Try to get all cars without any filters
            List<CarDto> cars = carService.getAllCars();
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(List.of()); // Return empty list on error
        }
    }

    @GetMapping("/cars/count")
    public ResponseEntity<String> getCarCount() {
        try {
            List<CarDto> cars = carService.getAllCars();
            return ResponseEntity.ok("Car count: " + cars.size());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error: " + e.getMessage());
        }
    }
} 