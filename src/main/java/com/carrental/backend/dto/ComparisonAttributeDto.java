package com.carrental.backend.dto;

public class ComparisonAttributeDto {
    
    private String attributeName;
    private String attributeLabel;
    private String attributeType; // text, number, boolean, currency, percentage
    private String unit;
    private Object value;
    private boolean isHighlighted = false;
    private String highlightReason; // best, worst, difference
    
    public ComparisonAttributeDto() {}
    
    public ComparisonAttributeDto(String attributeName, String attributeLabel, String attributeType, Object value) {
        this.attributeName = attributeName;
        this.attributeLabel = attributeLabel;
        this.attributeType = attributeType;
        this.value = value;
    }
    
    public ComparisonAttributeDto(String attributeName, String attributeLabel, String attributeType, String unit, Object value) {
        this.attributeName = attributeName;
        this.attributeLabel = attributeLabel;
        this.attributeType = attributeType;
        this.unit = unit;
        this.value = value;
    }
    
    // Getters and Setters
    public String getAttributeName() {
        return attributeName;
    }
    
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    
    public String getAttributeLabel() {
        return attributeLabel;
    }
    
    public void setAttributeLabel(String attributeLabel) {
        this.attributeLabel = attributeLabel;
    }
    
    public String getAttributeType() {
        return attributeType;
    }
    
    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    public boolean isHighlighted() {
        return isHighlighted;
    }
    
    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }
    
    public String getHighlightReason() {
        return highlightReason;
    }
    
    public void setHighlightReason(String highlightReason) {
        this.highlightReason = highlightReason;
    }
} 