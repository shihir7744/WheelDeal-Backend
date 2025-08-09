package com.carrental.backend.repository;

import com.carrental.backend.entities.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, Long> {
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.car.id = :carId ORDER BY ci.sortOrder ASC, ci.createdAt ASC")
    List<CarImage> findByCarIdOrderBySortOrderAscCreatedAtAsc(@Param("carId") Long carId);
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.car.id = :carId AND ci.imageType = :imageType ORDER BY ci.sortOrder ASC, ci.createdAt ASC")
    List<CarImage> findByCarIdAndImageTypeOrderBySortOrderAscCreatedAtAsc(@Param("carId") Long carId, @Param("imageType") CarImage.ImageType imageType);
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.car.id = :carId AND ci.isPrimary = true")
    Optional<CarImage> findPrimaryImageByCarId(@Param("carId") Long carId);
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.car.id = :carId AND ci.imageType = 'PRIMARY'")
    Optional<CarImage> findPrimaryImageTypeByCarId(@Param("carId") Long carId);
    
    @Query("SELECT COUNT(ci) FROM CarImage ci WHERE ci.car.id = :carId")
    long countByCarId(@Param("carId") Long carId);
    
    @Query("SELECT COUNT(ci) FROM CarImage ci WHERE ci.car.id = :carId AND ci.isPrimary = true")
    long countPrimaryImagesByCarId(@Param("carId") Long carId);
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.car.id = :carId AND ci.imageType = 'GALLERY' ORDER BY ci.sortOrder ASC, ci.createdAt ASC")
    List<CarImage> findGalleryImagesByCarId(@Param("carId") Long carId);
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.car.id = :carId AND ci.imageType IN ('INTERIOR', 'EXTERIOR') ORDER BY ci.imageType ASC, ci.sortOrder ASC")
    List<CarImage> findInteriorExteriorImagesByCarId(@Param("carId") Long carId);
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.car.id = :carId AND ci.imageType = 'FEATURE' ORDER BY ci.sortOrder ASC")
    List<CarImage> findFeatureImagesByCarId(@Param("carId") Long carId);
    
    @Query("SELECT MAX(ci.sortOrder) FROM CarImage ci WHERE ci.car.id = :carId")
    Integer findMaxSortOrderByCarId(@Param("carId") Long carId);
    
    @Query("DELETE FROM CarImage ci WHERE ci.car.id = :carId")
    void deleteByCarId(@Param("carId") Long carId);
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.fileName = :fileName")
    Optional<CarImage> findByFileName(@Param("fileName") String fileName);
    
    @Query("SELECT ci FROM CarImage ci WHERE ci.car.id = :carId AND ci.id = :imageId")
    Optional<CarImage> findByCarIdAndImageId(@Param("carId") Long carId, @Param("imageId") Long imageId);
} 