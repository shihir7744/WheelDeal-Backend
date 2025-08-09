package com.carrental.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class FinancialReportDto {
    
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpenses;
    private BigDecimal netProfit;
    private BigDecimal profitMargin;
    private int totalTransactions;
    private int completedTransactions;
    private int pendingTransactions;
    private BigDecimal averageTransactionValue;
    private Map<String, BigDecimal> revenueByType;
    private Map<String, BigDecimal> expensesByType;
    private Map<String, BigDecimal> revenueByBranch;
    private Map<String, BigDecimal> revenueByMonth;

    // Constructors
    public FinancialReportDto() {}

    public FinancialReportDto(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public int getCompletedTransactions() {
        return completedTransactions;
    }

    public void setCompletedTransactions(int completedTransactions) {
        this.completedTransactions = completedTransactions;
    }

    public int getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(int pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public BigDecimal getAverageTransactionValue() {
        return averageTransactionValue;
    }

    public void setAverageTransactionValue(BigDecimal averageTransactionValue) {
        this.averageTransactionValue = averageTransactionValue;
    }

    public Map<String, BigDecimal> getRevenueByType() {
        return revenueByType;
    }

    public void setRevenueByType(Map<String, BigDecimal> revenueByType) {
        this.revenueByType = revenueByType;
    }

    public Map<String, BigDecimal> getExpensesByType() {
        return expensesByType;
    }

    public void setExpensesByType(Map<String, BigDecimal> expensesByType) {
        this.expensesByType = expensesByType;
    }

    public Map<String, BigDecimal> getRevenueByBranch() {
        return revenueByBranch;
    }

    public void setRevenueByBranch(Map<String, BigDecimal> revenueByBranch) {
        this.revenueByBranch = revenueByBranch;
    }

    public Map<String, BigDecimal> getRevenueByMonth() {
        return revenueByMonth;
    }

    public void setRevenueByMonth(Map<String, BigDecimal> revenueByMonth) {
        this.revenueByMonth = revenueByMonth;
    }
} 