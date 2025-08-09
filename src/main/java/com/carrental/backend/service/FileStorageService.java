package com.carrental.backend.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    private com.carrental.backend.config.FileStorageConfig fileStorageConfig;

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(com.carrental.backend.config.FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = fileStorageConfig.uploadPath();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Validate file
        validateFile(file);

        // Generate unique filename
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        String fileName = generateUniqueFileName(fileExtension);

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            logger.info("File stored successfully: {}", fileName);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    public boolean deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            logger.error("Error deleting file: {}", fileName, ex);
            return false;
        }
    }

    public void validateFile(MultipartFile file) {
        // Check if file is empty
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        // Check file size
        if (file.getSize() > fileStorageConfig.getMaxFileSize()) {
            throw new RuntimeException("File size exceeds maximum allowed size of " + 
                (fileStorageConfig.getMaxFileSize() / 1024 / 1024) + "MB");
        }

        // Check file type
        String contentType = file.getContentType();
        if (contentType == null || !Arrays.asList(fileStorageConfig.getAllowedTypes()).contains(contentType)) {
            throw new RuntimeException("File type not allowed. Allowed types: " + 
                String.join(", ", fileStorageConfig.getAllowedTypes()));
        }

        // Check file extension
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = FilenameUtils.getExtension(originalFileName).toLowerCase();
        String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "webp"};
        
        if (!Arrays.asList(allowedExtensions).contains(fileExtension)) {
            throw new RuntimeException("File extension not allowed. Allowed extensions: " + 
                String.join(", ", allowedExtensions));
        }
    }

    private String generateUniqueFileName(String fileExtension) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomString = RandomStringUtils.randomAlphanumeric(8);
        return timestamp + "_" + randomString + "." + fileExtension.toLowerCase();
    }

    public String getFileUrl(String fileName) {
        return "/uploads/" + fileName;
    }

    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }

    public long getFileSize(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.size(filePath);
        } catch (IOException ex) {
            logger.error("Error getting file size: {}", fileName, ex);
            return 0;
        }
    }

    public boolean fileExists(String fileName) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return Files.exists(filePath);
    }
} 