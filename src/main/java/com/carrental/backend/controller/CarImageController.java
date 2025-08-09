package com.carrental.backend.controller;

import com.carrental.backend.dto.CarImageDto;
import com.carrental.backend.dto.ImageUploadRequest;
import com.carrental.backend.service.CarImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/car-images")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CarImageController {

    @Autowired
    private CarImageService carImageService;

    @PostMapping("/{carId}/upload")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CarImageDto> uploadImage(
            @PathVariable Long carId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "imageType", required = false) String imageType,
            @RequestParam(value = "isPrimary", required = false) Boolean isPrimary,
            @RequestParam(value = "sortOrder", required = false) Integer sortOrder,
            @RequestParam(value = "altText", required = false) String altText,
            @RequestParam(value = "caption", required = false) String caption) {

        ImageUploadRequest request = new ImageUploadRequest();
        request.setCarId(carId);
        if (imageType != null) {
            request.setImageType(com.carrental.backend.entities.CarImage.ImageType.valueOf(imageType.toUpperCase()));
        }
        request.setIsPrimary(isPrimary);
        request.setSortOrder(sortOrder);
        request.setAltText(altText);
        request.setCaption(caption);

        CarImageDto uploadedImage = carImageService.uploadImage(carId, file, request);
        return ResponseEntity.ok(uploadedImage);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<List<CarImageDto>> getCarImages(@PathVariable Long carId) {
        List<CarImageDto> images = carImageService.getCarImages(carId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{carId}/primary")
    public ResponseEntity<CarImageDto> getPrimaryImage(@PathVariable Long carId) {
        CarImageDto primaryImage = carImageService.getPrimaryImage(carId);
        return ResponseEntity.ok(primaryImage);
    }

    @GetMapping("/{carId}/gallery")
    public ResponseEntity<List<CarImageDto>> getGalleryImages(@PathVariable Long carId) {
        List<CarImageDto> images = carImageService.getGalleryImages(carId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{carId}/interior-exterior")
    public ResponseEntity<List<CarImageDto>> getInteriorExteriorImages(@PathVariable Long carId) {
        List<CarImageDto> images = carImageService.getInteriorExteriorImages(carId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{carId}/features")
    public ResponseEntity<List<CarImageDto>> getFeatureImages(@PathVariable Long carId) {
        List<CarImageDto> images = carImageService.getFeatureImages(carId);
        return ResponseEntity.ok(images);
    }

    @PutMapping("/{carId}/{imageId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CarImageDto> updateImage(
            @PathVariable Long carId,
            @PathVariable Long imageId,
            @RequestBody ImageUploadRequest request) {
        CarImageDto updatedImage = carImageService.updateImage(carId, imageId, request);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{carId}/{imageId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteImage(@PathVariable Long carId, @PathVariable Long imageId) {
        carImageService.deleteImage(carId, imageId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{carId}/{imageId}/primary")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> setPrimaryImage(@PathVariable Long carId, @PathVariable Long imageId) {
        carImageService.setPrimaryImage(carId, imageId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{carId}/reorder")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> reorderImages(
            @PathVariable Long carId,
            @RequestBody List<Long> imageIds) {
        carImageService.reorderImages(carId, imageIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{carId}/count")
    public ResponseEntity<Long> getImageCount(@PathVariable Long carId) {
        long count = carImageService.getImageCount(carId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{carId}/primary-count")
    public ResponseEntity<Long> getPrimaryImageCount(@PathVariable Long carId) {
        long count = carImageService.getPrimaryImageCount(carId);
        return ResponseEntity.ok(count);
    }
} 