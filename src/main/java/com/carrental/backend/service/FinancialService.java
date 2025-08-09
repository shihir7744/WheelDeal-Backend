package com.carrental.backend.service;

import com.carrental.backend.dto.FinancialReportDto;
import com.carrental.backend.dto.FinancialTransactionDto;
import com.carrental.backend.entities.*;
import com.carrental.backend.repository.FinancialTransactionRepository;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinancialService {

    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private UserRepository userRepository;

    public FinancialTransactionDto createTransaction(FinancialTransactionDto transactionDto) {
        FinancialTransaction transaction = convertToEntity(transactionDto);
        FinancialTransaction savedTransaction = financialTransactionRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    public FinancialTransactionDto getTransactionById(Long id) {
        FinancialTransaction transaction = financialTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return convertToDto(transaction);
    }

    public List<FinancialTransactionDto> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        
        List<FinancialTransaction> transactions = financialTransactionRepository
                .findByTransactionDateBetweenOrderByTransactionDateDesc(startDateTime, endDateTime);
        
        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Page<FinancialTransactionDto> searchTransactions(
            TransactionType transactionType,
            TransactionStatus status,
            Long carId,
            Long userId,
            Long branchId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {
        
        Page<FinancialTransaction> transactions = financialTransactionRepository.searchTransactions(
                transactionType, status, carId, userId, branchId, startDate, endDate, pageable);
        
        return transactions.map(this::convertToDto);
    }

    public FinancialReportDto generateFinancialReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        
        FinancialReportDto report = new FinancialReportDto(startDate, endDate);
        
        // Calculate total revenue (income transactions)
        BigDecimal totalRevenue = calculateTotalRevenue(startDateTime, endDateTime);
        report.setTotalRevenue(totalRevenue);
        
        // Calculate total expenses (expense transactions)
        BigDecimal totalExpenses = calculateTotalExpenses(startDateTime, endDateTime);
        report.setTotalExpenses(totalExpenses);
        
        // Calculate net profit
        BigDecimal netProfit = totalRevenue.subtract(totalExpenses);
        report.setNetProfit(netProfit);
        
        // Calculate profit margin
        if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal profitMargin = netProfit.divide(totalRevenue, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            report.setProfitMargin(profitMargin);
        } else {
            report.setProfitMargin(BigDecimal.ZERO);
        }
        
        // Transaction counts
        long totalTransactions = financialTransactionRepository.countByTransactionDateBetween(startDateTime, endDateTime);
        long completedTransactions = financialTransactionRepository.countByStatusAndTransactionDateBetween(
                TransactionStatus.COMPLETED, startDateTime, endDateTime);
        long pendingTransactions = financialTransactionRepository.countByStatusAndTransactionDateBetween(
                TransactionStatus.PENDING, startDateTime, endDateTime);
        
        report.setTotalTransactions((int) totalTransactions);
        report.setCompletedTransactions((int) completedTransactions);
        report.setPendingTransactions((int) pendingTransactions);
        
        // Average transaction value
        if (totalTransactions > 0) {
            BigDecimal totalAmount = financialTransactionRepository.sumAmountByDateRange(startDateTime, endDateTime);
            if (totalAmount != null) {
                BigDecimal averageValue = totalAmount.divide(BigDecimal.valueOf(totalTransactions), 2, RoundingMode.HALF_UP);
                report.setAverageTransactionValue(averageValue);
            }
        }
        
        // Revenue by type
        Map<String, BigDecimal> revenueByType = getRevenueByType(startDateTime, endDateTime);
        report.setRevenueByType(revenueByType);
        
        // Expenses by type
        Map<String, BigDecimal> expensesByType = getExpensesByType(startDateTime, endDateTime);
        report.setExpensesByType(expensesByType);
        
        // Revenue by branch
        Map<String, BigDecimal> revenueByBranch = getRevenueByBranch(startDateTime, endDateTime);
        report.setRevenueByBranch(revenueByBranch);
        
        // Revenue by month
        Map<String, BigDecimal> revenueByMonth = getRevenueByMonth(startDateTime, endDateTime);
        report.setRevenueByMonth(revenueByMonth);
        
        return report;
    }

    private BigDecimal calculateTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> revenueData = financialTransactionRepository.getRevenueByTypeForDateRange(startDate, endDate);
        return revenueData.stream()
                .map(row -> (BigDecimal) row[1])
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalExpenses(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> expenseData = financialTransactionRepository.getExpensesByTypeForDateRange(startDate, endDate);
        return expenseData.stream()
                .map(row -> (BigDecimal) row[1])
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, BigDecimal> getRevenueByType(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> revenueData = financialTransactionRepository.getRevenueByTypeForDateRange(startDate, endDate);
        Map<String, BigDecimal> revenueByType = new HashMap<>();
        
        for (Object[] row : revenueData) {
            TransactionType type = (TransactionType) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            revenueByType.put(type.getDisplayName(), amount);
        }
        
        return revenueByType;
    }

    private Map<String, BigDecimal> getExpensesByType(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> expenseData = financialTransactionRepository.getExpensesByTypeForDateRange(startDate, endDate);
        Map<String, BigDecimal> expensesByType = new HashMap<>();
        
        for (Object[] row : expenseData) {
            TransactionType type = (TransactionType) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            expensesByType.put(type.getDisplayName(), amount);
        }
        
        return expensesByType;
    }

    private Map<String, BigDecimal> getRevenueByBranch(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> branchData = financialTransactionRepository.getRevenueByBranchForDateRange(startDate, endDate);
        Map<String, BigDecimal> revenueByBranch = new HashMap<>();
        
        for (Object[] row : branchData) {
            String branchName = (String) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            revenueByBranch.put(branchName, amount);
        }
        
        return revenueByBranch;
    }

    private Map<String, BigDecimal> getRevenueByMonth(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> monthData = financialTransactionRepository.getRevenueByMonthForDateRange(startDate, endDate);
        Map<String, BigDecimal> revenueByMonth = new HashMap<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        
        for (Object[] row : monthData) {
            Integer year = (Integer) row[0];
            Integer month = (Integer) row[1];
            BigDecimal amount = (BigDecimal) row[2];
            
            String monthKey = String.format("%04d-%02d", year, month);
            revenueByMonth.put(monthKey, amount);
        }
        
        return revenueByMonth;
    }

    private FinancialTransaction convertToEntity(FinancialTransactionDto dto) {
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setId(dto.getId());
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setAmount(dto.getAmount());
        transaction.setCurrency(dto.getCurrency());
        transaction.setDescription(dto.getDescription());
        transaction.setReferenceNumber(dto.getReferenceNumber());
        transaction.setPaymentMethod(dto.getPaymentMethod());
        transaction.setStatus(dto.getStatus());
        transaction.setTransactionDate(dto.getTransactionDate());
        
        // Set relationships if IDs are provided
        if (dto.getCarId() != null) {
            // Note: We'll need to get the Car entity directly from repository
            // For now, we'll skip this relationship setting
        }
        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            transaction.setUser(user);
        }
        
        if (dto.getBranchId() != null) {
            // Note: We'll need to get the Branch entity directly from repository
            // For now, we'll skip this relationship setting
        }
        
        return transaction;
    }

    private FinancialTransactionDto convertToDto(FinancialTransaction transaction) {
        FinancialTransactionDto dto = new FinancialTransactionDto();
        dto.setId(transaction.getId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setCurrency(transaction.getCurrency());
        dto.setDescription(transaction.getDescription());
        dto.setReferenceNumber(transaction.getReferenceNumber());
        dto.setPaymentMethod(transaction.getPaymentMethod());
        dto.setStatus(transaction.getStatus());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());
        
        // Set related entity information
        if (transaction.getCar() != null) {
            dto.setCarId(transaction.getCar().getId());
            dto.setCarBrand(transaction.getCar().getBrand());
            dto.setCarModel(transaction.getCar().getModel());
        }
        
        if (transaction.getUser() != null) {
            dto.setUserId(transaction.getUser().getId());
            dto.setUserFullName(transaction.getUser().getFullName());
        }
        
        if (transaction.getBranch() != null) {
            dto.setBranchId(transaction.getBranch().getId());
            dto.setBranchName(transaction.getBranch().getName());
        }
        
        if (transaction.getBooking() != null) {
            dto.setBookingId(transaction.getBooking().getId());
        }
        
        return dto;
    }
} 