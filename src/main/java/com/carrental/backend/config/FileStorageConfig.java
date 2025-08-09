package com.carrental.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Value("${app.file.upload-dir:uploads}")
    private String uploadDir;

    @Value("${app.file.max-size:10485760}") // 10MB default
    private long maxFileSize;

    @Value("${app.file.allowed-types:image/jpeg,image/png,image/gif,image/webp}")
    private String allowedTypes;

    @Bean
    public Path uploadPath() {
        return Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath() + "/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
        
        registry.addMapping("/uploads/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public String[] getAllowedTypes() {
        return allowedTypes.split(",");
    }
} 