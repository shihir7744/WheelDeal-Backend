package com.carrental.backend.dto;

import com.carrental.backend.entities.CarImage;

public class ImageUploadRequest {
    private Long carId;
    private CarImage.ImageType imageType;
    private Boolean isPrimary;
    private Integer sortOrder;
    private String altText;
    private String caption;

    public ImageUploadRequest() {}

    public ImageUploadRequest(Long carId, CarImage.ImageType imageType, Boolean isPrimary, 
                            Integer sortOrder, String altText, String caption) {
        this.carId = carId;
        this.imageType = imageType;
        this.isPrimary = isPrimary;
        this.sortOrder = sortOrder;
        this.altText = altText;
        this.caption = caption;
    }

    // Getters and Setters
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    @Override
    public String toString() {
        return "ImageUploadRequest{" +
                "carId=" + carId +
                ", imageType=" + imageType +
                ", isPrimary=" + isPrimary +
                ", sortOrder=" + sortOrder +
                ", altText='" + altText + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }
} 