package com.carrental.backend.service;

import com.carrental.backend.dto.CarImageDto;
import com.carrental.backend.dto.ImageUploadRequest;
import com.carrental.backend.entities.Car;
import com.carrental.backend.entities.CarImage;
import com.carrental.backend.exception.BadRequestException;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.repository.CarImageRepository;
import com.carrental.backend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarImageService {

    @Autowired
    private CarImageRepository carImageRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public CarImageDto uploadImage(Long carId, MultipartFile file, ImageUploadRequest request) {
        // Validate car exists
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));

        // Store file
        String fileName = fileStorageService.storeFile(file);

        // Create car image entity
        CarImage carImage = new CarImage();
        carImage.setCar(car);
        carImage.setFileName(fileName);
        carImage.setOriginalFileName(file.getOriginalFilename());
        carImage.setFilePath(fileStorageService.getFileUrl(fileName));
        carImage.setFileSize(file.getSize());
        carImage.setContentType(file.getContentType());
        carImage.setImageType(request.getImageType() != null ? request.getImageType() : CarImage.ImageType.GALLERY);
        carImage.setIsPrimary(request.getIsPrimary() != null ? request.getIsPrimary() : false);
        carImage.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : getNextSortOrder(carId));
        carImage.setAltText(request.getAltText());
        carImage.setCaption(request.getCaption());

        // If this is primary, unset other primary images
        if (carImage.getIsPrimary()) {
            unsetOtherPrimaryImages(carId);
        }

        CarImage savedImage = carImageRepository.save(carImage);
        return convertToDto(savedImage);
    }

    public List<CarImageDto> getCarImages(Long carId) {
        List<CarImage> images = carImageRepository.findByCarIdOrderBySortOrderAscCreatedAtAsc(carId);
        return images.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CarImageDto getPrimaryImage(Long carId) {
        CarImage primaryImage = carImageRepository.findPrimaryImageByCarId(carId)
                .orElse(null);
        return primaryImage != null ? convertToDto(primaryImage) : null;
    }

    public List<CarImageDto> getGalleryImages(Long carId) {
        List<CarImage> images = carImageRepository.findGalleryImagesByCarId(carId);
        return images.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<CarImageDto> getInteriorExteriorImages(Long carId) {
        List<CarImage> images = carImageRepository.findInteriorExteriorImagesByCarId(carId);
        return images.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<CarImageDto> getFeatureImages(Long carId) {
        List<CarImage> images = carImageRepository.findFeatureImagesByCarId(carId);
        return images.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CarImageDto updateImage(Long carId, Long imageId, ImageUploadRequest request) {
        CarImage carImage = carImageRepository.findByCarIdAndImageId(carId, imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Car Image", "id", imageId));

        if (request.getImageType() != null) {
            carImage.setImageType(request.getImageType());
        }
        if (request.getIsPrimary() != null) {
            carImage.setIsPrimary(request.getIsPrimary());
            if (request.getIsPrimary()) {
                unsetOtherPrimaryImages(carId);
            }
        }
        if (request.getSortOrder() != null) {
            carImage.setSortOrder(request.getSortOrder());
        }
        if (request.getAltText() != null) {
            carImage.setAltText(request.getAltText());
        }
        if (request.getCaption() != null) {
            carImage.setCaption(request.getCaption());
        }

        CarImage updatedImage = carImageRepository.save(carImage);
        return convertToDto(updatedImage);
    }

    public void deleteImage(Long carId, Long imageId) {
        CarImage carImage = carImageRepository.findByCarIdAndImageId(carId, imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Car Image", "id", imageId));

        // Delete file from storage
        fileStorageService.deleteFile(carImage.getFileName());

        // Delete from database
        carImageRepository.delete(carImage);
    }

    public void setPrimaryImage(Long carId, Long imageId) {
        CarImage carImage = carImageRepository.findByCarIdAndImageId(carId, imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Car Image", "id", imageId));

        // Unset other primary images
        unsetOtherPrimaryImages(carId);

        // Set this as primary
        carImage.setIsPrimary(true);
        carImageRepository.save(carImage);
    }

    public void reorderImages(Long carId, List<Long> imageIds) {
        for (int i = 0; i < imageIds.size(); i++) {
            Long imageId = imageIds.get(i);
            CarImage carImage = carImageRepository.findByCarIdAndImageId(carId, imageId)
                    .orElseThrow(() -> new ResourceNotFoundException("Car Image", "id", imageId));
            carImage.setSortOrder(i + 1);
            carImageRepository.save(carImage);
        }
    }

    public long getImageCount(Long carId) {
        return carImageRepository.countByCarId(carId);
    }

    public long getPrimaryImageCount(Long carId) {
        return carImageRepository.countPrimaryImagesByCarId(carId);
    }

    private void unsetOtherPrimaryImages(Long carId) {
        List<CarImage> primaryImages = carImageRepository.findByCarIdOrderBySortOrderAscCreatedAtAsc(carId)
                .stream()
                .filter(CarImage::getIsPrimary)
                .collect(Collectors.toList());

        for (CarImage image : primaryImages) {
            image.setIsPrimary(false);
            carImageRepository.save(image);
        }
    }

    private Integer getNextSortOrder(Long carId) {
        Integer maxSortOrder = carImageRepository.findMaxSortOrderByCarId(carId);
        return maxSortOrder != null ? maxSortOrder + 1 : 1;
    }

    private CarImageDto convertToDto(CarImage carImage) {
        return new CarImageDto(
                carImage.getId(),
                carImage.getCar().getId(),
                carImage.getFileName(),
                carImage.getOriginalFileName(),
                carImage.getFilePath(),
                carImage.getFileSize(),
                carImage.getContentType(),
                carImage.getImageType(),
                carImage.getIsPrimary(),
                carImage.getSortOrder(),
                carImage.getAltText(),
                carImage.getCaption(),
                carImage.getCreatedAt(),
                carImage.getUpdatedAt()
        );
    }
} 