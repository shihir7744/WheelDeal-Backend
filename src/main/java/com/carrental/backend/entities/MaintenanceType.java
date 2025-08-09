package com.carrental.backend.entities;

public enum MaintenanceType {
    OIL_CHANGE("Oil Change"),
    TIRE_ROTATION("Tire Rotation"),
    BRAKE_SERVICE("Brake Service"),
    AIR_FILTER_REPLACEMENT("Air Filter Replacement"),
    FUEL_FILTER_REPLACEMENT("Fuel Filter Replacement"),
    TRANSMISSION_SERVICE("Transmission Service"),
    ENGINE_TUNE_UP("Engine Tune-up"),
    COOLANT_FLUSH("Coolant Flush"),
    BATTERY_REPLACEMENT("Battery Replacement"),
    ALIGNMENT("Wheel Alignment"),
    SUSPENSION_SERVICE("Suspension Service"),
    EXHAUST_SERVICE("Exhaust Service"),
    ELECTRICAL_SERVICE("Electrical Service"),
    AC_SERVICE("Air Conditioning Service"),
    HEATING_SERVICE("Heating Service"),
    BODY_REPAIR("Body Repair"),
    PAINT_JOB("Paint Job"),
    INTERIOR_REPAIR("Interior Repair"),
    GLASS_REPLACEMENT("Glass Replacement"),
    EMERGENCY_REPAIR("Emergency Repair"),
    PREVENTIVE_MAINTENANCE("Preventive Maintenance"),
    INSPECTION("Inspection"),
    OTHER("Other");

    private final String displayName;

    MaintenanceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 