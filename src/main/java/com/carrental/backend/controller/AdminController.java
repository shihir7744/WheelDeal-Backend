package com.carrental.backend.controller;

import com.carrental.backend.entities.User;
import com.carrental.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Public endpoint for initial admin setup (no authentication required)
     * Only works if no admin exists in the system
     */
    @PostMapping("/setup")
    public ResponseEntity<?> initialAdminSetup(@RequestBody Map<String, String> request) {
        try {
            // Check if any admin already exists
            if (adminService.adminExists()) {
                return ResponseEntity.badRequest().body("Admin already exists. Use authenticated endpoints.");
            }

            String email = request.get("email");
            String password = request.get("password");
            String fullName = request.get("fullName");

            if (email == null || password == null || fullName == null) {
                return ResponseEntity.badRequest().body("Email, password, and fullName are required");
            }

            User adminUser = adminService.createAdminUser(email, password, fullName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Initial admin user created successfully");
            response.put("email", adminUser.getEmail());
            response.put("fullName", adminUser.getFullName());
            response.put("note", "You can now login with these credentials");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating admin user: " + e.getMessage());
        }
    }

    /**
     * Create a new admin user (only accessible by existing admins)
     */
    @PostMapping("/users/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAdminUser(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            String fullName = request.get("fullName");

            if (email == null || password == null || fullName == null) {
                return ResponseEntity.badRequest().body("Email, password, and fullName are required");
            }

            User adminUser = adminService.createAdminUser(email, password, fullName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Admin user created successfully");
            response.put("email", adminUser.getEmail());
            response.put("fullName", adminUser.getFullName());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating admin user: " + e.getMessage());
        }
    }

    /**
     * Create a new manager user
     */
    @PostMapping("/users/manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createManagerUser(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            String fullName = request.get("fullName");

            if (email == null || password == null || fullName == null) {
                return ResponseEntity.badRequest().body("Email, password, and fullName are required");
            }

            User managerUser = adminService.createManagerUser(email, password, fullName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Manager user created successfully");
            response.put("email", managerUser.getEmail());
            response.put("fullName", managerUser.getFullName());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating manager user: " + e.getMessage());
        }
    }

    /**
     * Check if admin user exists
     */
    @GetMapping("/check-admin")
    public ResponseEntity<?> checkAdminExists() {
        boolean exists = adminService.adminExists();
        Map<String, Object> response = new HashMap<>();
        response.put("adminExists", exists);
        return ResponseEntity.ok(response);
    }

    /**
     * Update admin password
     */
    @PutMapping("/users/admin/password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAdminPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String newPassword = request.get("newPassword");

            if (email == null || newPassword == null) {
                return ResponseEntity.badRequest().body("Email and newPassword are required");
            }

            User updatedAdmin = adminService.updateAdminPassword(email, newPassword);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Admin password updated successfully");
            response.put("email", updatedAdmin.getEmail());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating admin password: " + e.getMessage());
        }
    }
} 