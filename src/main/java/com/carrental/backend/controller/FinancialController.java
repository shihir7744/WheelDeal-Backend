package com.carrental.backend.controller;

import com.carrental.backend.dto.FinancialReportDto;
import com.carrental.backend.dto.FinancialTransactionDto;
import com.carrental.backend.entities.TransactionStatus;
import com.carrental.backend.entities.TransactionType;
import com.carrental.backend.service.FinancialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/financial")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    @PostMapping("/transactions")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<FinancialTransactionDto> createTransaction(@Valid @RequestBody FinancialTransactionDto transactionDto) {
        FinancialTransactionDto createdTransaction = financialService.createTransaction(transactionDto);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/transactions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<FinancialTransactionDto> getTransactionById(@PathVariable Long id) {
        FinancialTransactionDto transaction = financialService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Page<FinancialTransactionDto>> searchTransactions(
            @RequestParam(required = false) TransactionType transactionType,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) Long carId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<FinancialTransactionDto> transactions = financialService.searchTransactions(
                transactionType, status, carId, userId, branchId, startDate, endDate, pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<FinancialTransactionDto>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<FinancialTransactionDto> transactions = financialService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<FinancialReportDto> generateFinancialReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        FinancialReportDto report = financialService.generateFinancialReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/current-month")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<FinancialReportDto> getCurrentMonthReport() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        
        FinancialReportDto report = financialService.generateFinancialReport(startOfMonth, endOfMonth);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/current-year")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<FinancialReportDto> getCurrentYearReport() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());
        
        FinancialReportDto report = financialService.generateFinancialReport(startOfYear, endOfYear);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/last-30-days")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<FinancialReportDto> getLast30DaysReport() {
        LocalDate now = LocalDate.now();
        LocalDate thirtyDaysAgo = now.minusDays(30);
        
        FinancialReportDto report = financialService.generateFinancialReport(thirtyDaysAgo, now);
        return ResponseEntity.ok(report);
    }
} 