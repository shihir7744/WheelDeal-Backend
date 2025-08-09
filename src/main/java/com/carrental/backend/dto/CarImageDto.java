package com.carrental.backend.dto;

import com.carrental.backend.entities.CarImage;

import java.time.LocalDateTime;

public class CarImageDto {
    private Long id;
    private Long carId;
    private String fileName;
    private String originalFileName;
    private String filePath;
    private Long fileSize;
    private String contentType;
    private CarImage.ImageType imageType;
    private Boolean isPrimary;
    private Integer sortOrder;
    private String altText;
    private String caption;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CarImageDto() {}

    public CarImageDto(Long id, Long carId, String fileName, String originalFileName, 
                      String filePath, Long fileSize, String contentType, 
                      CarImage.ImageType imageType, Boolean isPrimary, Integer sortOrder,
                      String altText, String caption, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.carId = carId;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.imageType = imageType;
        this.isPrimary = isPrimary;
        this.sortOrder = sortOrder;
        this.altText = altText;
        this.caption = caption;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public CarImage.ImageType getImageType() {
        return imageType;
    }

    public void setImageType(CarImage.ImageType imageType) {
        this.imageType = imageType;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CarImageDto{" +
                "id=" + id +
                ", carId=" + carId +
                ", fileName='" + fileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", imageType=" + imageType +
                ", isPrimary=" + isPrimary +
                ", sortOrder=" + sortOrder +
                '}';
    }
} 