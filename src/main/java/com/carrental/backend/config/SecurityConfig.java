package com.carrental.backend.config;

import com.carrental.backend.security.CustomUserDetailsService;
import com.carrental.backend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.and())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/uploads/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                // Public car browsing endpoints used on home page
                .requestMatchers("/api/cars").permitAll()
                .requestMatchers("/api/cars/*").permitAll()
                .requestMatchers("/api/cars/featured").permitAll()
                .requestMatchers("/api/cars/popular").permitAll()
                .requestMatchers("/api/cars/basic").permitAll()
                .requestMatchers("/api/cars/search").permitAll()
                .requestMatchers("/api/cars/search/simple").permitAll()
                .requestMatchers("/api/cars/available").permitAll()
                .requestMatchers("/api/cars/available/dates").permitAll()
                .requestMatchers("/api/cars/advanced-search").permitAll()
                .requestMatchers("/api/cars/filters/**").permitAll()
                // Branch lookups
                .requestMatchers("/api/branches").permitAll()
                .requestMatchers("/api/branches/*").permitAll()
                .requestMatchers("/api/branches/city/**").permitAll()
                .requestMatchers("/api/branches/search").permitAll()
                // Public dashboard (aggregated metrics) for home
                .requestMatchers("/api/dashboard/public").permitAll()
                // Admin / Manager protected areas
                .requestMatchers("/api/admin/setup").permitAll()
                .requestMatchers("/api/admin/check-admin").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().authenticated()
            );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

