package com.carrental.backend.repository;

import com.carrental.backend.entities.FinancialTransaction;
import com.carrental.backend.entities.TransactionStatus;
import com.carrental.backend.entities.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

    // Find by transaction type
    List<FinancialTransaction> findByTransactionTypeOrderByTransactionDateDesc(TransactionType transactionType);
    
    Page<FinancialTransaction> findByTransactionTypeOrderByTransactionDateDesc(TransactionType transactionType, Pageable pageable);

    // Find by status
    List<FinancialTransaction> findByStatusOrderByTransactionDateDesc(TransactionStatus status);
    
    Page<FinancialTransaction> findByStatusOrderByTransactionDateDesc(TransactionStatus status, Pageable pageable);

    // Find by date range
    List<FinancialTransaction> findByTransactionDateBetweenOrderByTransactionDateDesc(LocalDateTime startDate, LocalDateTime endDate);
    
    Page<FinancialTransaction> findByTransactionDateBetweenOrderByTransactionDateDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Find by car
    List<FinancialTransaction> findByCarIdOrderByTransactionDateDesc(Long carId);
    
    Page<FinancialTransaction> findByCarIdOrderByTransactionDateDesc(Long carId, Pageable pageable);

    // Find by user
    List<FinancialTransaction> findByUserIdOrderByTransactionDateDesc(Long userId);
    
    Page<FinancialTransaction> findByUserIdOrderByTransactionDateDesc(Long userId, Pageable pageable);

    // Find by branch
    List<FinancialTransaction> findByBranchIdOrderByTransactionDateDesc(Long branchId);
    
    Page<FinancialTransaction> findByBranchIdOrderByTransactionDateDesc(Long branchId, Pageable pageable);

    // Find by booking
    List<FinancialTransaction> findByBookingIdOrderByTransactionDateDesc(Long bookingId);

    // Find by payment method
    List<FinancialTransaction> findByPaymentMethodOrderByTransactionDateDesc(String paymentMethod);

    // Count by status
    long countByStatus(TransactionStatus status);

    // Count by transaction type
    long countByTransactionType(TransactionType transactionType);

    // Count by date range
    long countByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Count by status and date range
    long countByStatusAndTransactionDateBetween(TransactionStatus status, LocalDateTime startDate, LocalDateTime endDate);

    // Sum amounts by transaction type
    @Query("SELECT SUM(t.amount) FROM FinancialTransaction t WHERE t.transactionType = :transactionType")
    BigDecimal sumAmountByTransactionType(@Param("transactionType") TransactionType transactionType);

    // Sum amounts by status
    @Query("SELECT SUM(t.amount) FROM FinancialTransaction t WHERE t.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") TransactionStatus status);

    // Sum amounts by date range
    @Query("SELECT SUM(t.amount) FROM FinancialTransaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Sum amounts by car
    @Query("SELECT SUM(t.amount) FROM FinancialTransaction t WHERE t.car.id = :carId")
    BigDecimal sumAmountByCarId(@Param("carId") Long carId);

    // Sum amounts by branch
    @Query("SELECT SUM(t.amount) FROM FinancialTransaction t WHERE t.branch.id = :branchId")
    BigDecimal sumAmountByBranchId(@Param("branchId") Long branchId);

    // Get revenue by transaction type for date range
    @Query("SELECT t.transactionType, SUM(t.amount) FROM FinancialTransaction t " +
           "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
           "AND t.transactionType IN ('RENTAL_INCOME', 'LATE_FEE', 'DAMAGE_FEE', 'FUEL_FEE', 'INSURANCE_FEE', 'ADDITIONAL_DRIVER_FEE', 'OTHER_INCOME') " +
           "GROUP BY t.transactionType")
    List<Object[]> getRevenueByTypeForDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Get expenses by transaction type for date range
    @Query("SELECT t.transactionType, SUM(t.amount) FROM FinancialTransaction t " +
           "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
           "AND t.transactionType IN ('MAINTENANCE_COST', 'REPAIR_COST', 'REFUND', 'OTHER_EXPENSE') " +
           "GROUP BY t.transactionType")
    List<Object[]> getExpensesByTypeForDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Get revenue by branch for date range
    @Query("SELECT t.branch.name, SUM(t.amount) FROM FinancialTransaction t " +
           "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
           "AND t.transactionType IN ('RENTAL_INCOME', 'LATE_FEE', 'DAMAGE_FEE', 'FUEL_FEE', 'INSURANCE_FEE', 'ADDITIONAL_DRIVER_FEE', 'OTHER_INCOME') " +
           "GROUP BY t.branch.name")
    List<Object[]> getRevenueByBranchForDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Get revenue by month for date range
    @Query("SELECT FUNCTION('YEAR', t.transactionDate), FUNCTION('MONTH', t.transactionDate), SUM(t.amount) FROM FinancialTransaction t " +
           "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
           "AND t.transactionType IN ('RENTAL_INCOME', 'LATE_FEE', 'DAMAGE_FEE', 'FUEL_FEE', 'INSURANCE_FEE', 'ADDITIONAL_DRIVER_FEE', 'OTHER_INCOME') " +
           "GROUP BY FUNCTION('YEAR', t.transactionDate), FUNCTION('MONTH', t.transactionDate) " +
           "ORDER BY FUNCTION('YEAR', t.transactionDate), FUNCTION('MONTH', t.transactionDate)")
    List<Object[]> getRevenueByMonthForDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Complex search query
    @Query("SELECT t FROM FinancialTransaction t WHERE " +
           "(:transactionType IS NULL OR t.transactionType = :transactionType) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:carId IS NULL OR t.car.id = :carId) AND " +
           "(:userId IS NULL OR t.user.id = :userId) AND " +
           "(:branchId IS NULL OR t.branch.id = :branchId) AND " +
           "(:startDate IS NULL OR t.transactionDate >= :startDate) AND " +
           "(:endDate IS NULL OR t.transactionDate <= :endDate) " +
           "ORDER BY t.transactionDate DESC")
    Page<FinancialTransaction> searchTransactions(
            @Param("transactionType") TransactionType transactionType,
            @Param("status") TransactionStatus status,
            @Param("carId") Long carId,
            @Param("userId") Long userId,
            @Param("branchId") Long branchId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
} 