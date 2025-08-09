package com.carrental.backend.entities;

public enum TransactionType {
    RENTAL_INCOME("Rental Income"),
    DEPOSIT("Deposit"),
    LATE_FEE("Late Fee"),
    DAMAGE_FEE("Damage Fee"),
    FUEL_FEE("Fuel Fee"),
    INSURANCE_FEE("Insurance Fee"),
    ADDITIONAL_DRIVER_FEE("Additional Driver Fee"),
    MAINTENANCE_COST("Maintenance Cost"),
    REPAIR_COST("Repair Cost"),
    REFUND("Refund"),
    CANCELLATION_FEE("Cancellation Fee"),
    ADMINISTRATIVE_FEE("Administrative Fee"),
    TAX("Tax"),
    DISCOUNT("Discount"),
    OTHER_INCOME("Other Income"),
    OTHER_EXPENSE("Other Expense");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 