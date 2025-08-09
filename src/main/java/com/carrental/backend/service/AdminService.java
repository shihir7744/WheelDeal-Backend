package com.carrental.backend.service;

import com.carrental.backend.entities.Role;
import com.carrental.backend.entities.User;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create an admin user if it doesn't exist
     */
    public User createAdminUser(String email, String password, String fullName) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Admin user with this email already exists");
        }

        User adminUser = new User();
        adminUser.setEmail(email);
        adminUser.setPassword(passwordEncoder.encode(password));
        adminUser.setFullName(fullName);
        adminUser.setRole(Role.ADMIN);

        return userRepository.save(adminUser);
    }

    /**
     * Create a manager user if it doesn't exist
     */
    public User createManagerUser(String email, String password, String fullName) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Manager user with this email already exists");
        }

        User managerUser = new User();
        managerUser.setEmail(email);
        managerUser.setPassword(passwordEncoder.encode(password));
        managerUser.setFullName(fullName);
        managerUser.setRole(Role.MANAGER);

        return userRepository.save(managerUser);
    }

    /**
     * Check if admin user exists
     */
    public boolean adminExists() {
        return userRepository.existsByRole(Role.ADMIN);
    }

    /**
     * Get admin user by email
     */
    public Optional<User> getAdminByEmail(String email) {
        return userRepository.findByEmailAndRole(email, Role.ADMIN);
    }

    /**
     * Update admin password
     */
    public User updateAdminPassword(String email, String newPassword) {
        Optional<User> adminOpt = userRepository.findByEmailAndRole(email, Role.ADMIN);
        if (adminOpt.isPresent()) {
            User admin = adminOpt.get();
            admin.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(admin);
        }
        throw new RuntimeException("Admin user not found");
    }
} 