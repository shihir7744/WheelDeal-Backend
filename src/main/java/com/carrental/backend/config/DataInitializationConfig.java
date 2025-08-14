package com.carrental.backend.config;

import com.carrental.backend.entities.User;
import com.carrental.backend.entities.Role;
import com.carrental.backend.repository.UserRepository;
import com.carrental.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializationConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService;

    @Value("${app.initialize-demo-data:false}")
    private boolean initializeDemoData;

    /**
     * Initialize demo user accounts when application starts
     * Only runs if explicitly enabled AND no admin users exist
     */
    @Bean
    public CommandLineRunner initializeDemoData() {
        return args -> {
            // Check if demo data initialization is enabled
            if (!initializeDemoData) {
                System.out.println("Demo data initialization is disabled");
                return;
            }

            // Only initialize if no admin exists
            if (adminService.adminExists()) {
                System.out.println("Admin already exists, skipping demo data initialization");
                return;
            }

            System.out.println("🚀 Initializing demo user accounts for first deployment...");
            
            try {
                // Create demo users (admin, manager, customer)
                List<User> users = createDemoUsers();
                
                System.out.println("✅ Demo user accounts created successfully!");
                System.out.println("📋 Demo Accounts Created:");
                System.out.println("   👑 Admin: admin@wheeldeal.com / admin123");
                System.out.println("   👨‍💼 Manager: manager@wheeldeal.com / manager123");
                System.out.println("   👤 Customer: customer@wheeldeal.com / customer123");
                System.out.println("⚠️  IMPORTANT: Change these passwords after first login!");
                
            } catch (Exception e) {
                System.err.println("❌ Error initializing demo user accounts: " + e.getMessage());
                e.printStackTrace();
                System.err.println("Demo user creation failed, but application will continue");
            }
        };
    }

    private List<User> createDemoUsers() {
        // Create admin user
        User admin = new User();
        admin.setEmail("admin@wheeldeal.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("System Administrator");
        admin.setRole(Role.ADMIN);
        
        // Create manager user
        User manager = new User();
        manager.setEmail("manager@wheeldeal.com");
        manager.setPassword(passwordEncoder.encode("manager123"));
        manager.setFullName("Branch Manager");
        manager.setRole(Role.MANAGER);
        
        // Create demo customer
        User customer = new User();
        customer.setEmail("customer@wheeldeal.com");
        customer.setPassword(passwordEncoder.encode("customer123"));
        customer.setFullName("Demo Customer");
        customer.setRole(Role.CUSTOMER);
        
        List<User> users = Arrays.asList(admin, manager, customer);
        return userRepository.saveAll(users);
    }
}
