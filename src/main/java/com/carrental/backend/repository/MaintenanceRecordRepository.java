package com.carrental.backend.repository;

import com.carrental.backend.entities.MaintenanceRecord;
import com.carrental.backend.entities.MaintenanceStatus;
import com.carrental.backend.entities.MaintenanceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {

    // Find by car ID
    List<MaintenanceRecord> findByCarIdOrderByServiceDateDesc(Long carId);
    
    Page<MaintenanceRecord> findByCarIdOrderByServiceDateDesc(Long carId, Pageable pageable);

    // Find by maintenance type
    List<MaintenanceRecord> findByMaintenanceTypeOrderByServiceDateDesc(MaintenanceType maintenanceType);
    
    Page<MaintenanceRecord> findByMaintenanceTypeOrderByServiceDateDesc(MaintenanceType maintenanceType, Pageable pageable);

    // Find by status
    List<MaintenanceRecord> findByStatusOrderByServiceDateDesc(MaintenanceStatus status);
    
    Page<MaintenanceRecord> findByStatusOrderByServiceDateDesc(MaintenanceStatus status, Pageable pageable);

    // Find by service provider
    List<MaintenanceRecord> findByServiceProviderOrderByServiceDateDesc(String serviceProvider);
    
    Page<MaintenanceRecord> findByServiceProviderOrderByServiceDateDesc(String serviceProvider, Pageable pageable);

    // Find by date range
    List<MaintenanceRecord> findByServiceDateBetweenOrderByServiceDateDesc(LocalDate startDate, LocalDate endDate);
    
    Page<MaintenanceRecord> findByServiceDateBetweenOrderByServiceDateDesc(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Find upcoming maintenance (next service date is approaching)
    @Query("SELECT m FROM MaintenanceRecord m WHERE m.nextServiceDate IS NOT NULL AND m.nextServiceDate BETWEEN :startDate AND :endDate ORDER BY m.nextServiceDate ASC")
    List<MaintenanceRecord> findUpcomingMaintenance(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Find overdue maintenance
    @Query("SELECT m FROM MaintenanceRecord m WHERE m.nextServiceDate IS NOT NULL AND m.nextServiceDate < :currentDate ORDER BY m.nextServiceDate ASC")
    List<MaintenanceRecord> findOverdueMaintenance(@Param("currentDate") LocalDate currentDate);

    // Find by car and date range
    List<MaintenanceRecord> findByCarIdAndServiceDateBetweenOrderByServiceDateDesc(Long carId, LocalDate startDate, LocalDate endDate);

    // Find by car and status
    List<MaintenanceRecord> findByCarIdAndStatusOrderByServiceDateDesc(Long carId, MaintenanceStatus status);

    // Find by car and maintenance type
    List<MaintenanceRecord> findByCarIdAndMaintenanceTypeOrderByServiceDateDesc(Long carId, MaintenanceType maintenanceType);

    // Count by status
    long countByStatus(MaintenanceStatus status);

    // Count by car
    long countByCarId(Long carId);

    // Count by maintenance type
    long countByMaintenanceType(MaintenanceType maintenanceType);

    // Find most recent maintenance for a car
    @Query("SELECT m FROM MaintenanceRecord m WHERE m.car.id = :carId ORDER BY m.serviceDate DESC")
    List<MaintenanceRecord> findMostRecentByCarId(@Param("carId") Long carId, Pageable pageable);

    // Find maintenance records with cost above threshold
    @Query("SELECT m FROM MaintenanceRecord m WHERE m.cost > :threshold ORDER BY m.cost DESC")
    List<MaintenanceRecord> findByCostAboveThreshold(@Param("threshold") Double threshold);

    // Find warranty-covered maintenance
    List<MaintenanceRecord> findByWarrantyCoveredTrueOrderByServiceDateDesc();

    // Find by performed by user
    List<MaintenanceRecord> findByPerformedByIdOrderByServiceDateDesc(Long performedById);

    // Complex search query
    @Query("SELECT m FROM MaintenanceRecord m WHERE " +
           "(:carId IS NULL OR m.car.id = :carId) AND " +
           "(:maintenanceType IS NULL OR m.maintenanceType = :maintenanceType) AND " +
           "(:status IS NULL OR m.status = :status) AND " +
           "(:serviceProvider IS NULL OR m.serviceProvider LIKE %:serviceProvider%) AND " +
           "(:startDate IS NULL OR m.serviceDate >= :startDate) AND " +
           "(:endDate IS NULL OR m.serviceDate <= :endDate) " +
           "ORDER BY m.serviceDate DESC")
    Page<MaintenanceRecord> searchMaintenanceRecords(
            @Param("carId") Long carId,
            @Param("maintenanceType") MaintenanceType maintenanceType,
            @Param("status") MaintenanceStatus status,
            @Param("serviceProvider") String serviceProvider,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
} 