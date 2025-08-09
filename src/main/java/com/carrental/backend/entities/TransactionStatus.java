package com.carrental.backend.entities;

public enum TransactionStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    CANCELLED("Cancelled"),
    REFUNDED("Refunded"),
    PARTIALLY_REFUNDED("Partially Refunded"),
    DISPUTED("Disputed"),
    PROCESSING("Processing");

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 