package com.carrental.backend.entities;

public enum MaintenanceStatus {
    SCHEDULED("Scheduled"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    ON_HOLD("On Hold"),
    PENDING_PARTS("Pending Parts"),
    PENDING_APPROVAL("Pending Approval");

    private final String displayName;

    MaintenanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 