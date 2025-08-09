package com.carrental.backend.service;

import com.carrental.backend.dto.CarComparisonDto;
import com.carrental.backend.dto.CarComparisonResultDto;
import com.carrental.backend.dto.CarDto;
import com.carrental.backend.dto.ComparisonAttributeDto;
import com.carrental.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarComparisonService {

    @Autowired
    private CarService carService;

    public CarComparisonResultDto compareCars(CarComparisonDto comparisonDto) {
        // Validate car IDs
        List<Long> carIds = comparisonDto.getCarIds();
        if (carIds.size() < 2 || carIds.size() > 5) {
            throw new IllegalArgumentException("Comparison must include between 2 and 5 cars");
        }

        // Get car details
        List<CarDto> cars = carIds.stream()
                .map(id -> {
                    try {
                        return carService.getCarById(id);
                    } catch (ResourceNotFoundException e) {
                        throw new IllegalArgumentException("Car with ID " + id + " not found");
                    }
                })
                .collect(Collectors.toList());

        CarComparisonResultDto result = new CarComparisonResultDto(cars, comparisonDto.getComparisonType());

        // Generate comparison data based on type
        String comparisonType = comparisonDto.getComparisonType();
        switch (comparisonType.toLowerCase()) {
            case "specifications":
                result.setSpecifications(generateSpecificationsComparison(cars));
                break;
            case "features":
                result.setFeatures(generateFeaturesComparison(cars));
                break;
            case "pricing":
                result.setPricing(generatePricingComparison(cars));
                break;
            case "rental":
                result.setRental(generateRentalComparison(cars));
                break;
            case "all":
            default:
                result.setSpecifications(generateSpecificationsComparison(cars));
                result.setFeatures(generateFeaturesComparison(cars));
                result.setPricing(generatePricingComparison(cars));
                result.setRental(generateRentalComparison(cars));
                break;
        }

        return result;
    }

    private Map<String, List<ComparisonAttributeDto>> generateSpecificationsComparison(List<CarDto> cars) {
        Map<String, List<ComparisonAttributeDto>> specifications = new LinkedHashMap<>();

        // Basic specs
        specifications.put("Basic Information", Arrays.asList(
            createAttribute("brand", "Brand", "text", cars),
            createAttribute("model", "Model", "text", cars),
            createAttribute("year", "Year", "number", cars),
            createAttribute("type", "Type", "text", cars),
            createAttribute("color", "Color", "text", cars)
        ));

        // Engine specs
        specifications.put("Engine & Performance", Arrays.asList(
            createAttribute("engineSize", "Engine Size", "text", cars),
            createAttribute("horsepower", "Horsepower", "number", "hp", cars),
            createAttribute("torque", "Torque", "number", "lb-ft", cars),
            createAttribute("fuelType", "Fuel Type", "text", cars),
            createAttribute("transmission", "Transmission", "text", cars),
            createAttribute("acceleration0To60", "0-60 mph", "number", "sec", cars),
            createAttribute("topSpeed", "Top Speed", "number", "mph", cars)
        ));

        // Dimensions
        specifications.put("Dimensions & Capacity", Arrays.asList(
            createAttribute("seatingCapacity", "Seating Capacity", "number", "persons", cars),
            createAttribute("trunkCapacity", "Trunk Capacity", "number", "cu ft", cars),
            createAttribute("length", "Length", "number", "in", cars),
            createAttribute("width", "Width", "number", "in", cars),
            createAttribute("height", "Height", "number", "in", cars),
            createAttribute("wheelbase", "Wheelbase", "number", "in", cars),
            createAttribute("curbWeight", "Curb Weight", "number", "lbs", cars)
        ));

        // Fuel efficiency
        specifications.put("Fuel Efficiency", Arrays.asList(
            createAttribute("fuelEfficiencyCity", "City MPG", "number", "mpg", cars),
            createAttribute("fuelEfficiencyHighway", "Highway MPG", "number", "mpg", cars),
            createAttribute("fuelTankCapacity", "Fuel Tank", "number", "gal", cars)
        ));

        return specifications;
    }

    private Map<String, List<ComparisonAttributeDto>> generateFeaturesComparison(List<CarDto> cars) {
        Map<String, List<ComparisonAttributeDto>> features = new LinkedHashMap<>();

        features.put("General Features", Arrays.asList(
            createAttribute("features", "General Features", "text", cars)
        ));

        features.put("Safety Features", Arrays.asList(
            createAttribute("safetyFeatures", "Safety Features", "text", cars)
        ));

        features.put("Entertainment Features", Arrays.asList(
            createAttribute("entertainmentFeatures", "Entertainment", "text", cars)
        ));

        features.put("Comfort Features", Arrays.asList(
            createAttribute("comfortFeatures", "Comfort", "text", cars)
        ));

        features.put("Technology Features", Arrays.asList(
            createAttribute("technologyFeatures", "Technology", "text", cars)
        ));

        return features;
    }

    private Map<String, List<ComparisonAttributeDto>> generatePricingComparison(List<CarDto> cars) {
        Map<String, List<ComparisonAttributeDto>> pricing = new LinkedHashMap<>();

        pricing.put("Rental Rates", Arrays.asList(
            createAttribute("dailyRate", "Daily Rate", "currency", cars),
            createAttribute("weeklyRate", "Weekly Rate", "currency", cars),
            createAttribute("monthlyRate", "Monthly Rate", "currency", cars),
            createAttribute("depositAmount", "Deposit", "currency", cars)
        ));

        pricing.put("Additional Costs", Arrays.asList(
            createAttribute("additionalDriverFee", "Additional Driver", "currency", cars),
            createAttribute("lateReturnFee", "Late Return Fee", "currency", cars),
            createAttribute("earlyReturnDiscount", "Early Return Discount", "currency", cars)
        ));

        return pricing;
    }

    private Map<String, List<ComparisonAttributeDto>> generateRentalComparison(List<CarDto> cars) {
        Map<String, List<ComparisonAttributeDto>> rental = new LinkedHashMap<>();

        rental.put("Rental Policies", Arrays.asList(
            createAttribute("insuranceIncluded", "Insurance Included", "boolean", cars),
            createAttribute("maintenanceIncluded", "Maintenance Included", "boolean", cars),
            createAttribute("unlimitedMileage", "Unlimited Mileage", "boolean", cars)
        ));

        rental.put("Requirements", Arrays.asList(
            createAttribute("minimumRentalDays", "Min Rental Days", "number", "days", cars),
            createAttribute("maximumRentalDays", "Max Rental Days", "number", "days", cars),
            createAttribute("ageRequirement", "Age Requirement", "number", "years", cars),
            createAttribute("licenseRequirement", "License Requirement", "text", cars)
        ));

        rental.put("Status", Arrays.asList(
            createAttribute("availability", "Available", "boolean", cars),
            createAttribute("isFeatured", "Featured", "boolean", cars),
            createAttribute("isNewArrival", "New Arrival", "boolean", cars),
            createAttribute("isPopular", "Popular", "boolean", cars)
        ));

        return rental;
    }

    private ComparisonAttributeDto createAttribute(String attributeName, String label, String type, List<CarDto> cars) {
        return createAttribute(attributeName, label, type, null, cars);
    }

    private ComparisonAttributeDto createAttribute(String attributeName, String label, String type, String unit, List<CarDto> cars) {
        List<Object> values = cars.stream()
                .map(car -> getCarValue(car, attributeName))
                .collect(Collectors.toList());

        // Find best/worst values for highlighting
        ComparisonAttributeDto attribute = new ComparisonAttributeDto(attributeName, label, type, unit, values);
        
        if (type.equals("number") || type.equals("currency")) {
            highlightBestWorst(attribute, values);
        }

        return attribute;
    }

    private Object getCarValue(CarDto car, String attributeName) {
        switch (attributeName) {
            case "brand": return car.getBrand();
            case "model": return car.getModel();
            case "year": return car.getYear();
            case "type": return car.getType();
            case "color": return car.getColor();
            case "engineSize": return car.getEngineSize();
            case "horsepower": return car.getHorsepower();
            case "torque": return car.getTorque();
            case "fuelType": return car.getFuelType();
            case "transmission": return car.getTransmission();
            case "acceleration0To60": return car.getAcceleration0To60();
            case "topSpeed": return car.getTopSpeed();
            case "seatingCapacity": return car.getSeatingCapacity();
            case "trunkCapacity": return car.getTrunkCapacity();
            case "length": return car.getLength();
            case "width": return car.getWidth();
            case "height": return car.getHeight();
            case "wheelbase": return car.getWheelbase();
            case "curbWeight": return car.getCurbWeight();
            case "fuelEfficiencyCity": return car.getFuelEfficiencyCity();
            case "fuelEfficiencyHighway": return car.getFuelEfficiencyHighway();
            case "fuelTankCapacity": return car.getFuelTankCapacity();
            case "features": return car.getFeatures();
            case "safetyFeatures": return car.getSafetyFeatures();
            case "entertainmentFeatures": return car.getEntertainmentFeatures();
            case "comfortFeatures": return car.getComfortFeatures();
            case "technologyFeatures": return car.getTechnologyFeatures();
            case "dailyRate": return car.getDailyRate();
            case "weeklyRate": return car.getWeeklyRate();
            case "monthlyRate": return car.getMonthlyRate();
            case "depositAmount": return car.getDepositAmount();
            case "additionalDriverFee": return car.getAdditionalDriverFee();
            case "lateReturnFee": return car.getLateReturnFee();
            case "earlyReturnDiscount": return car.getEarlyReturnDiscount();
            case "insuranceIncluded": return car.getInsuranceIncluded();
            case "maintenanceIncluded": return car.getMaintenanceIncluded();
            case "unlimitedMileage": return car.getUnlimitedMileage();
            case "minimumRentalDays": return car.getMinimumRentalDays();
            case "maximumRentalDays": return car.getMaximumRentalDays();
            case "ageRequirement": return car.getAgeRequirement();
            case "licenseRequirement": return car.getLicenseRequirement();
            case "availability": return car.getAvailability();
            case "isFeatured": return car.getIsFeatured();
            case "isNewArrival": return car.getIsNewArrival();
            case "isPopular": return car.getIsPopular();
            default: return null;
        }
    }

    private void highlightBestWorst(ComparisonAttributeDto attribute, List<Object> values) {
        List<Number> numericValues = values.stream()
                .filter(v -> v instanceof Number)
                .map(v -> (Number) v)
                .collect(Collectors.toList());

        if (numericValues.isEmpty()) return;

        // Find min and max values
        Number min = numericValues.stream().min(Comparator.comparingDouble(Number::doubleValue)).orElse(null);
        Number max = numericValues.stream().max(Comparator.comparingDouble(Number::doubleValue)).orElse(null);

        if (min != null && max != null) {
            // Determine if higher or lower is better based on attribute
            boolean higherIsBetter = isHigherBetter(attribute.getAttributeName());
            
            if (higherIsBetter) {
                attribute.setHighlighted(true);
                attribute.setHighlightReason("best");
            } else {
                attribute.setHighlighted(true);
                attribute.setHighlightReason("best");
            }
        }
    }

    private boolean isHigherBetter(String attributeName) {
        // Attributes where higher values are better
        Set<String> higherIsBetter = Set.of(
            "horsepower", "torque", "topSpeed", "seatingCapacity", "trunkCapacity",
            "fuelEfficiencyCity", "fuelEfficiencyHighway", "fuelTankCapacity",
            "maximumRentalDays", "ageRequirement"
        );

        // Attributes where lower values are better
        Set<String> lowerIsBetter = Set.of(
            "acceleration0To60", "curbWeight", "length", "width", "height",
            "dailyRate", "weeklyRate", "monthlyRate", "depositAmount",
            "additionalDriverFee", "lateReturnFee", "minimumRentalDays"
        );

        if (higherIsBetter.contains(attributeName)) return true;
        if (lowerIsBetter.contains(attributeName)) return false;
        
        // Default: no preference
        return false;
    }
} 