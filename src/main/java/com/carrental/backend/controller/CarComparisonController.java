package com.carrental.backend.controller;

import com.carrental.backend.dto.CarComparisonDto;
import com.carrental.backend.dto.CarComparisonResultDto;
import com.carrental.backend.service.CarComparisonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car-comparison")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CarComparisonController {

    @Autowired
    private CarComparisonService carComparisonService;

    @PostMapping("/compare")
    public ResponseEntity<CarComparisonResultDto> compareCars(@Valid @RequestBody CarComparisonDto comparisonDto) {
        try {
            CarComparisonResultDto result = carComparisonService.compareCars(comparisonDto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/compare/specifications")
    public ResponseEntity<CarComparisonResultDto> compareSpecifications(@Valid @RequestBody CarComparisonDto comparisonDto) {
        comparisonDto.setComparisonType("specifications");
        return compareCars(comparisonDto);
    }

    @PostMapping("/compare/features")
    public ResponseEntity<CarComparisonResultDto> compareFeatures(@Valid @RequestBody CarComparisonDto comparisonDto) {
        comparisonDto.setComparisonType("features");
        return compareCars(comparisonDto);
    }

    @PostMapping("/compare/pricing")
    public ResponseEntity<CarComparisonResultDto> comparePricing(@Valid @RequestBody CarComparisonDto comparisonDto) {
        comparisonDto.setComparisonType("pricing");
        return compareCars(comparisonDto);
    }

    @PostMapping("/compare/rental")
    public ResponseEntity<CarComparisonResultDto> compareRental(@Valid @RequestBody CarComparisonDto comparisonDto) {
        comparisonDto.setComparisonType("rental");
        return compareCars(comparisonDto);
    }
} 