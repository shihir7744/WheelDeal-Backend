package com.carrental.backend.controller;

import com.carrental.backend.dto.CarDto;
import com.carrental.backend.dto.AdvancedCarSearchDto;
import com.carrental.backend.dto.CarSpecificationsDto;
import com.carrental.backend.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.carrental.backend.entities.Car;
import com.carrental.backend.repository.CarRepository;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        CarDto car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @GetMapping("/basic")
    public ResponseEntity<Map<String, Object>> getBasicCarInfo() {
        try {
            List<Car> cars = carRepository.findByAvailability(true);
            Map<String, Object> response = new HashMap<>();
            response.put("count", cars.size());
            response.put("carIds", cars.stream().map(Car::getId).collect(Collectors.toList()));
            response.put("brands", cars.stream().map(Car::getBrand).distinct().collect(Collectors.toList()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("stackTrace", e.getStackTrace());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/search/simple")
    public ResponseEntity<List<CarDto>> searchCarsSimple() {
        List<CarDto> cars = carService.searchCarsSimple();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarDto>> searchCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String city) {
        
        List<CarDto> cars = carService.searchCars(brand, model, type, minPrice, maxPrice, branchId, city);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarDto>> getAvailableCars() {
        List<CarDto> cars = carService.getAvailableCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/available/dates")
    public ResponseEntity<List<CarDto>> getAvailableCarsForDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<CarDto> cars = carService.getAvailableCarsForDateRange(startDate, endDate);
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CarDto carDto) {
        CarDto createdCar = carService.createCar(carDto);
        return ResponseEntity.ok(createdCar);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @Valid @RequestBody CarDto carDto) {
        CarDto updatedCar = carService.updateCar(id, carDto);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<CarDto>> getCarsByBranch(@PathVariable Long branchId) {
        List<CarDto> cars = carService.getCarsByBranch(branchId);
        return ResponseEntity.ok(cars);
    }
    
    @PostMapping("/advanced-search")
    public ResponseEntity<List<CarDto>> advancedSearch(@RequestBody AdvancedCarSearchDto searchDto) {
        List<CarDto> cars = carService.advancedSearch(searchDto);
        return ResponseEntity.ok(cars);
    }
    
    @GetMapping("/filters/brands")
    public ResponseEntity<List<String>> getDistinctBrands() {
        List<String> brands = carService.getDistinctBrands();
        return ResponseEntity.ok(brands);
    }
    
    @GetMapping("/filters/types")
    public ResponseEntity<List<String>> getDistinctTypes() {
        List<String> types = carService.getDistinctTypes();
        return ResponseEntity.ok(types);
    }
    
    @GetMapping("/filters/fuel-types")
    public ResponseEntity<List<String>> getDistinctFuelTypes() {
        List<String> fuelTypes = carService.getDistinctFuelTypes();
        return ResponseEntity.ok(fuelTypes);
    }
    
    @GetMapping("/filters/transmissions")
    public ResponseEntity<List<String>> getDistinctTransmissions() {
        List<String> transmissions = carService.getDistinctTransmissions();
        return ResponseEntity.ok(transmissions);
    }
    
    @GetMapping("/filters/colors")
    public ResponseEntity<List<String>> getDistinctColors() {
        List<String> colors = carService.getDistinctColors();
        return ResponseEntity.ok(colors);
    }
    
    @GetMapping("/filters/engine-sizes")
    public ResponseEntity<List<String>> getDistinctEngineSizes() {
        List<String> engineSizes = carService.getDistinctEngineSizes();
        return ResponseEntity.ok(engineSizes);
    }
    
    // Enhanced car endpoints
    @GetMapping("/featured")
    public ResponseEntity<List<CarDto>> getFeaturedCars() {
        List<CarDto> cars = carService.getFeaturedCars();
        return ResponseEntity.ok(cars);
    }
    
    @GetMapping("/new-arrivals")
    public ResponseEntity<List<CarDto>> getNewArrivals() {
        List<CarDto> cars = carService.getNewArrivals();
        return ResponseEntity.ok(cars);
    }
    
    @GetMapping("/popular")
    public ResponseEntity<List<CarDto>> getPopularCars() {
        List<CarDto> cars = carService.getPopularCars();
        return ResponseEntity.ok(cars);
    }
    
    @GetMapping("/{id}/specifications")
    public ResponseEntity<CarSpecificationsDto> getCarSpecifications(@PathVariable Long id) {
        CarSpecificationsDto specifications = carService.getCarSpecifications(id);
        return ResponseEntity.ok(specifications);
    }
}

