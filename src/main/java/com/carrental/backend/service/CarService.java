package com.carrental.backend.service;

import com.carrental.backend.dto.CarDto;
import com.carrental.backend.dto.CarImageDto;
import com.carrental.backend.dto.AdvancedCarSearchDto;
import com.carrental.backend.dto.CarSpecificationsDto;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.entities.Branch;
import com.carrental.backend.entities.Car;
import com.carrental.backend.entities.CarImage;
import com.carrental.backend.repository.BranchRepository;
import com.carrental.backend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    public List<CarDto> getAllCars() {
        return carRepository.findAllWithImagesAndReviews().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CarDto getCarById(Long id) {
        Car car = carRepository.findByIdWithImagesAndReviews(id);
        if (car == null) {
            throw new ResourceNotFoundException("Car", "id", id);
        }
        return convertToDto(car);
    }

    public List<CarDto> searchCars(String brand, String model, String type, 
                                   BigDecimal minPrice, BigDecimal maxPrice, 
                                   Long branchId, String city) {
        try {
            List<Car> cars;
            
            if (city != null && !city.isEmpty()) {
                cars = carRepository.findAvailableCarsByCity(city);
            } else {
                cars = carRepository.findAvailableCarsWithFilters(brand, model, type, minPrice, maxPrice, branchId);
            }
            
            return cars.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Fallback to simple query if complex query fails
            List<Car> simpleCars = carRepository.findByAvailability(true);
            return simpleCars.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
    }

    public List<CarDto> searchCarsSimple() {
        try {
            List<Car> cars = carRepository.findByAvailability(true);
            return cars.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<CarDto> getAvailableCars() {
        try {
            return carRepository.findAvailableCarsWithFilters(null, null, null, null, null, null).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            List<Car> simple = carRepository.findByAvailability(true);
            return simple.stream().map(this::convertToDto).collect(Collectors.toList());
        }
    }

    public List<CarDto> getAvailableCarsForDateRange(LocalDate startDate, LocalDate endDate) {
        return carRepository.findAvailableCarsForDateRange(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CarDto createCar(CarDto carDto) {
        Branch branch = branchRepository.findById(carDto.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", carDto.getBranchId()));

        Car car = new Car();
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setType(carDto.getType());
        car.setPrice(carDto.getPrice());
        car.setAvailability(true);
        car.setBranch(branch);
        
        // Set enhanced specifications
        car.setFuelType(carDto.getFuelType());
        car.setTransmission(carDto.getTransmission());
        car.setSeatingCapacity(carDto.getSeatingCapacity());
        car.setMileage(carDto.getMileage());
        car.setColor(carDto.getColor());
        car.setEngineSize(carDto.getEngineSize());
        car.setHorsepower(carDto.getHorsepower());
        car.setTorque(carDto.getTorque());
        car.setAcceleration0To60(carDto.getAcceleration0To60());
        car.setTopSpeed(carDto.getTopSpeed());
        car.setFuelEfficiencyCity(carDto.getFuelEfficiencyCity());
        car.setFuelEfficiencyHighway(carDto.getFuelEfficiencyHighway());
        car.setFuelTankCapacity(carDto.getFuelTankCapacity());
        car.setTrunkCapacity(carDto.getTrunkCapacity());
        car.setGroundClearance(carDto.getGroundClearance());
        car.setWheelbase(carDto.getWheelbase());
        car.setLength(carDto.getLength());
        car.setWidth(carDto.getWidth());
        car.setHeight(carDto.getHeight());
        car.setCurbWeight(carDto.getCurbWeight());
        
        // Set features
        car.setFeatures(carDto.getFeatures());
        car.setSafetyFeatures(carDto.getSafetyFeatures());
        car.setEntertainmentFeatures(carDto.getEntertainmentFeatures());
        car.setComfortFeatures(carDto.getComfortFeatures());
        car.setTechnologyFeatures(carDto.getTechnologyFeatures());
        
        // Set rental information
        car.setIsFeatured(carDto.getIsFeatured());
        car.setIsNewArrival(carDto.getIsNewArrival());
        car.setIsPopular(carDto.getIsPopular());
        car.setDailyRate(carDto.getDailyRate());
        car.setWeeklyRate(carDto.getWeeklyRate());
        car.setMonthlyRate(carDto.getMonthlyRate());
        car.setDepositAmount(carDto.getDepositAmount());
        car.setInsuranceIncluded(carDto.getInsuranceIncluded());
        car.setMaintenanceIncluded(carDto.getMaintenanceIncluded());
        car.setUnlimitedMileage(carDto.getUnlimitedMileage());
        car.setMinimumRentalDays(carDto.getMinimumRentalDays());
        car.setMaximumRentalDays(carDto.getMaximumRentalDays());
        car.setAgeRequirement(carDto.getAgeRequirement());
        car.setLicenseRequirement(carDto.getLicenseRequirement());
        car.setAdditionalDriverFee(carDto.getAdditionalDriverFee());
        car.setLateReturnFee(carDto.getLateReturnFee());
        car.setEarlyReturnDiscount(carDto.getEarlyReturnDiscount());

        Car savedCar = carRepository.save(car);
        return convertToDto(savedCar);
    }

    public CarDto updateCar(Long id, CarDto carDto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));

        if (carDto.getBranchId() != null && !carDto.getBranchId().equals(car.getBranch().getId())) {
            Branch branch = branchRepository.findById(carDto.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", carDto.getBranchId()));
            car.setBranch(branch);
        }

        if (carDto.getBrand() != null) car.setBrand(carDto.getBrand());
        if (carDto.getModel() != null) car.setModel(carDto.getModel());
        if (carDto.getYear() != null) car.setYear(carDto.getYear());
        if (carDto.getType() != null) car.setType(carDto.getType());
        if (carDto.getPrice() != null) car.setPrice(carDto.getPrice());
        if (carDto.getAvailability() != null) car.setAvailability(carDto.getAvailability());
        
        // Update enhanced specifications
        if (carDto.getFuelType() != null) car.setFuelType(carDto.getFuelType());
        if (carDto.getTransmission() != null) car.setTransmission(carDto.getTransmission());
        if (carDto.getSeatingCapacity() != null) car.setSeatingCapacity(carDto.getSeatingCapacity());
        if (carDto.getMileage() != null) car.setMileage(carDto.getMileage());
        if (carDto.getColor() != null) car.setColor(carDto.getColor());
        if (carDto.getEngineSize() != null) car.setEngineSize(carDto.getEngineSize());
        if (carDto.getHorsepower() != null) car.setHorsepower(carDto.getHorsepower());
        if (carDto.getTorque() != null) car.setTorque(carDto.getTorque());
        if (carDto.getAcceleration0To60() != null) car.setAcceleration0To60(carDto.getAcceleration0To60());
        if (carDto.getTopSpeed() != null) car.setTopSpeed(carDto.getTopSpeed());
        if (carDto.getFuelEfficiencyCity() != null) car.setFuelEfficiencyCity(carDto.getFuelEfficiencyCity());
        if (carDto.getFuelEfficiencyHighway() != null) car.setFuelEfficiencyHighway(carDto.getFuelEfficiencyHighway());
        if (carDto.getFuelTankCapacity() != null) car.setFuelTankCapacity(carDto.getFuelTankCapacity());
        if (carDto.getTrunkCapacity() != null) car.setTrunkCapacity(carDto.getTrunkCapacity());
        if (carDto.getGroundClearance() != null) car.setGroundClearance(carDto.getGroundClearance());
        if (carDto.getWheelbase() != null) car.setWheelbase(carDto.getWheelbase());
        if (carDto.getLength() != null) car.setLength(carDto.getLength());
        if (carDto.getWidth() != null) car.setWidth(carDto.getWidth());
        if (carDto.getHeight() != null) car.setHeight(carDto.getHeight());
        if (carDto.getCurbWeight() != null) car.setCurbWeight(carDto.getCurbWeight());
        
        // Update features
        if (carDto.getFeatures() != null) car.setFeatures(carDto.getFeatures());
        if (carDto.getSafetyFeatures() != null) car.setSafetyFeatures(carDto.getSafetyFeatures());
        if (carDto.getEntertainmentFeatures() != null) car.setEntertainmentFeatures(carDto.getEntertainmentFeatures());
        if (carDto.getComfortFeatures() != null) car.setComfortFeatures(carDto.getComfortFeatures());
        if (carDto.getTechnologyFeatures() != null) car.setTechnologyFeatures(carDto.getTechnologyFeatures());
        
        // Update rental information
        if (carDto.getIsFeatured() != null) car.setIsFeatured(carDto.getIsFeatured());
        if (carDto.getIsNewArrival() != null) car.setIsNewArrival(carDto.getIsNewArrival());
        if (carDto.getIsPopular() != null) car.setIsPopular(carDto.getIsPopular());
        if (carDto.getDailyRate() != null) car.setDailyRate(carDto.getDailyRate());
        if (carDto.getWeeklyRate() != null) car.setWeeklyRate(carDto.getWeeklyRate());
        if (carDto.getMonthlyRate() != null) car.setMonthlyRate(carDto.getMonthlyRate());
        if (carDto.getDepositAmount() != null) car.setDepositAmount(carDto.getDepositAmount());
        if (carDto.getInsuranceIncluded() != null) car.setInsuranceIncluded(carDto.getInsuranceIncluded());
        if (carDto.getMaintenanceIncluded() != null) car.setMaintenanceIncluded(carDto.getMaintenanceIncluded());
        if (carDto.getUnlimitedMileage() != null) car.setUnlimitedMileage(carDto.getUnlimitedMileage());
        if (carDto.getMinimumRentalDays() != null) car.setMinimumRentalDays(carDto.getMinimumRentalDays());
        if (carDto.getMaximumRentalDays() != null) car.setMaximumRentalDays(carDto.getMaximumRentalDays());
        if (carDto.getAgeRequirement() != null) car.setAgeRequirement(carDto.getAgeRequirement());
        if (carDto.getLicenseRequirement() != null) car.setLicenseRequirement(carDto.getLicenseRequirement());
        if (carDto.getAdditionalDriverFee() != null) car.setAdditionalDriverFee(carDto.getAdditionalDriverFee());
        if (carDto.getLateReturnFee() != null) car.setLateReturnFee(carDto.getLateReturnFee());
        if (carDto.getEarlyReturnDiscount() != null) car.setEarlyReturnDiscount(carDto.getEarlyReturnDiscount());

        Car updatedCar = carRepository.save(car);
        return convertToDto(updatedCar);
    }

    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));
        carRepository.delete(car);
    }

    public List<CarDto> getCarsByBranch(Long branchId) {
        return carRepository.findByBranchId(branchId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<CarDto> advancedSearch(AdvancedCarSearchDto searchDto) {
        List<Car> cars = carRepository.findCarsWithAdvancedFilters(
            searchDto.getAvailableOnly(),
            searchDto.getBrand(),
            searchDto.getModel(),
            searchDto.getType(),
            searchDto.getFuelType(),
            searchDto.getTransmission(),
            searchDto.getColor(),
            searchDto.getEngineSize(),
            searchDto.getMinPrice(),
            searchDto.getMaxPrice(),
            searchDto.getMinYear(),
            searchDto.getMaxYear(),
            searchDto.getMinSeatingCapacity(),
            searchDto.getMaxSeatingCapacity(),
            searchDto.getMinRating(),
            searchDto.getMinRatingCount(),
            searchDto.getBranchId(),
            searchDto.getCity()
        );
        
        List<CarDto> carDtos = cars.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        // Apply sorting if specified
        if (searchDto.hasSorting()) {
            carDtos = applySorting(carDtos, searchDto.getSortBy(), searchDto.getSortOrder());
        }
        
        return carDtos;
    }
    
    private List<CarDto> applySorting(List<CarDto> cars, String sortBy, String sortOrder) {
        boolean ascending = "asc".equalsIgnoreCase(sortOrder);
        
        switch (sortBy.toLowerCase()) {
            case "price":
                cars.sort((c1, c2) -> ascending ? 
                    c1.getPrice().compareTo(c2.getPrice()) : 
                    c2.getPrice().compareTo(c1.getPrice()));
                break;
            case "rating":
                cars.sort((c1, c2) -> {
                    Double rating1 = c1.getAverageRating() != null ? c1.getAverageRating() : 0.0;
                    Double rating2 = c2.getAverageRating() != null ? c2.getAverageRating() : 0.0;
                    return ascending ? rating1.compareTo(rating2) : rating2.compareTo(rating1);
                });
                break;
            case "year":
                cars.sort((c1, c2) -> ascending ? 
                    c1.getYear().compareTo(c2.getYear()) : 
                    c2.getYear().compareTo(c1.getYear()));
                break;
            case "mileage":
                cars.sort((c1, c2) -> {
                    Integer mileage1 = c1.getMileage() != null ? c1.getMileage() : 0;
                    Integer mileage2 = c2.getMileage() != null ? c2.getMileage() : 0;
                    return ascending ? mileage1.compareTo(mileage2) : mileage2.compareTo(mileage1);
                });
                break;
        }
        
        return cars;
    }
    
    // Methods to get distinct values for filters
    public List<String> getDistinctBrands() {
        return carRepository.findDistinctBrands();
    }
    
    public List<String> getDistinctTypes() {
        return carRepository.findDistinctTypes();
    }
    
    public List<String> getDistinctFuelTypes() {
        return carRepository.findDistinctFuelTypes();
    }
    
    public List<String> getDistinctTransmissions() {
        return carRepository.findDistinctTransmissions();
    }
    
    public List<String> getDistinctColors() {
        return carRepository.findDistinctColors();
    }
    
    public List<String> getDistinctEngineSizes() {
        return carRepository.findDistinctEngineSizes();
    }
    
    // Enhanced car methods
    public List<CarDto> getFeaturedCars() {
        return carRepository.findByIsFeaturedTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<CarDto> getNewArrivals() {
        return carRepository.findByIsNewArrivalTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<CarDto> getPopularCars() {
        return carRepository.findByIsPopularTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public CarSpecificationsDto getCarSpecifications(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));
        
        return convertToSpecificationsDto(car);
    }
    
    private CarSpecificationsDto convertToSpecificationsDto(Car car) {
        CarSpecificationsDto dto = new CarSpecificationsDto();
        dto.setCarId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setEngineSize(car.getEngineSize());
        dto.setHorsepower(car.getHorsepower());
        dto.setTorque(car.getTorque());
        dto.setFuelType(car.getFuelType());
        dto.setTransmission(car.getTransmission());
        dto.setAcceleration0To60(car.getAcceleration0To60());
        dto.setTopSpeed(car.getTopSpeed());
        dto.setFuelEfficiencyCity(car.getFuelEfficiencyCity());
        dto.setFuelEfficiencyHighway(car.getFuelEfficiencyHighway());
        dto.setFuelTankCapacity(car.getFuelTankCapacity());
        dto.setSeatingCapacity(car.getSeatingCapacity());
        dto.setTrunkCapacity(car.getTrunkCapacity());
        dto.setGroundClearance(car.getGroundClearance());
        dto.setWheelbase(car.getWheelbase());
        dto.setLength(car.getLength());
        dto.setWidth(car.getWidth());
        dto.setHeight(car.getHeight());
        dto.setCurbWeight(car.getCurbWeight());
        dto.setFeatures(car.getFeatures());
        dto.setSafetyFeatures(car.getSafetyFeatures());
        dto.setEntertainmentFeatures(car.getEntertainmentFeatures());
        dto.setComfortFeatures(car.getComfortFeatures());
        dto.setTechnologyFeatures(car.getTechnologyFeatures());
        dto.setDailyRate(car.getDailyRate());
        dto.setWeeklyRate(car.getWeeklyRate());
        dto.setMonthlyRate(car.getMonthlyRate());
        dto.setDepositAmount(car.getDepositAmount());
        dto.setInsuranceIncluded(car.getInsuranceIncluded());
        dto.setMaintenanceIncluded(car.getMaintenanceIncluded());
        dto.setUnlimitedMileage(car.getUnlimitedMileage());
        dto.setMinimumRentalDays(car.getMinimumRentalDays());
        dto.setMaximumRentalDays(car.getMaximumRentalDays());
        dto.setAgeRequirement(car.getAgeRequirement());
        dto.setLicenseRequirement(car.getLicenseRequirement());
        dto.setAdditionalDriverFee(car.getAdditionalDriverFee());
        dto.setLateReturnFee(car.getLateReturnFee());
        dto.setEarlyReturnDiscount(car.getEarlyReturnDiscount());
        
        return dto;
    }

    private CarDto convertToDto(Car car) {
        CarDto dto = new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getType(),
                car.getPrice(),
                car.getAvailability(),
                car.getBranch().getId(),
                car.getBranch().getName(),
                car.getBranch().getCity(),
                car.getAverageRating(),
                car.getRatingCount(),
                (long) (car.getReviews() != null ? car.getReviews().size() : 0),
                car.getFuelType(),
                car.getTransmission(),
                car.getSeatingCapacity(),
                car.getMileage(),
                car.getColor(),
                car.getEngineSize(),
                car.getHorsepower(),
                car.getTorque(),
                car.getAcceleration0To60(),
                car.getTopSpeed(),
                car.getFuelEfficiencyCity(),
                car.getFuelEfficiencyHighway(),
                car.getFuelTankCapacity(),
                car.getTrunkCapacity(),
                car.getGroundClearance(),
                car.getWheelbase(),
                car.getLength(),
                car.getWidth(),
                car.getHeight(),
                car.getCurbWeight(),
                car.getFeatures(),
                car.getSafetyFeatures(),
                car.getEntertainmentFeatures(),
                car.getComfortFeatures(),
                car.getTechnologyFeatures(),
                car.getIsFeatured(),
                car.getIsNewArrival(),
                car.getIsPopular(),
                car.getDailyRate(),
                car.getWeeklyRate(),
                car.getMonthlyRate(),
                car.getDepositAmount(),
                car.getInsuranceIncluded(),
                car.getMaintenanceIncluded(),
                car.getUnlimitedMileage(),
                car.getMinimumRentalDays(),
                car.getMaximumRentalDays(),
                car.getAgeRequirement(),
                car.getLicenseRequirement(),
                car.getAdditionalDriverFee(),
                car.getLateReturnFee(),
                car.getEarlyReturnDiscount()
        );
        
        // Convert car images to DTOs
        List<CarImageDto> imageDtos = (car.getImages() != null) ? 
            car.getImages().stream()
                .map(this::convertCarImageToDto)
                .collect(Collectors.toList()) : 
            new ArrayList<>();
        dto.setImages(imageDtos);
        
        return dto;
    }
    
    private CarImageDto convertCarImageToDto(CarImage carImage) {
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

