package com.carrental.backend.repository;

import com.carrental.backend.entities.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
    
    // Find active rules
    List<PricingRule> findByIsActiveTrue();
    
    // Find rules by car
    List<PricingRule> findByCarIdAndIsActiveTrue(Long carId);
    
    // Find rules by branch
    List<PricingRule> findByBranchIdAndIsActiveTrue(Long branchId);
    
    // Find rules by type
    List<PricingRule> findByRuleTypeAndIsActiveTrue(PricingRule.PricingRuleType ruleType);
    
    // Find rules by car and type
    List<PricingRule> findByCarIdAndRuleTypeAndIsActiveTrue(Long carId, PricingRule.PricingRuleType ruleType);
    
    // Find rules by branch and type
    List<PricingRule> findByBranchIdAndRuleTypeAndIsActiveTrue(Long branchId, PricingRule.PricingRuleType ruleType);
    
    // Find rules applicable for a specific date range
    @Query("SELECT pr FROM PricingRule pr WHERE pr.isActive = true AND " +
           "((pr.startDate IS NULL OR pr.startDate <= :endDate) AND " +
           "(pr.endDate IS NULL OR pr.endDate >= :startDate)) AND " +
           "(pr.car.id = :carId OR pr.branch.id = :branchId OR (pr.car.id IS NULL AND pr.branch.id IS NULL)) " +
           "ORDER BY pr.priority DESC")
    List<PricingRule> findApplicableRules(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate,
                                         @Param("carId") Long carId, 
                                         @Param("branchId") Long branchId);
    
    // Find weekend rules
    @Query("SELECT pr FROM PricingRule pr WHERE pr.isActive = true AND pr.isWeekend = true AND " +
           "((pr.car.id = :carId) OR (pr.branch.id = :branchId) OR (pr.car.id IS NULL AND pr.branch.id IS NULL)) " +
           "ORDER BY pr.priority DESC")
    List<PricingRule> findWeekendRules(@Param("carId") Long carId, @Param("branchId") Long branchId);
    
    // Find holiday rules
    @Query("SELECT pr FROM PricingRule pr WHERE pr.isActive = true AND pr.isHoliday = true AND " +
           "((pr.car.id = :carId) OR (pr.branch.id = :branchId) OR (pr.car.id IS NULL AND pr.branch.id IS NULL)) " +
           "ORDER BY pr.priority DESC")
    List<PricingRule> findHolidayRules(@Param("carId") Long carId, @Param("branchId") Long branchId);
    
    // Find occupancy-based rules
    @Query("SELECT pr FROM PricingRule pr WHERE pr.isActive = true AND " +
           "pr.minOccupancyPercentage IS NOT NULL AND pr.maxOccupancyPercentage IS NOT NULL AND " +
           "((pr.car.id = :carId) OR (pr.branch.id = :branchId) OR (pr.car.id IS NULL AND pr.branch.id IS NULL)) " +
           "ORDER BY pr.priority DESC")
    List<PricingRule> findOccupancyRules(@Param("carId") Long carId, @Param("branchId") Long branchId);
    
    // Find weather-based rules
    @Query("SELECT pr FROM PricingRule pr WHERE pr.isActive = true AND pr.weatherCondition IS NOT NULL AND " +
           "((pr.car.id = :carId) OR (pr.branch.id = :branchId) OR (pr.car.id IS NULL AND pr.branch.id IS NULL)) " +
           "ORDER BY pr.priority DESC")
    List<PricingRule> findWeatherRules(@Param("carId") Long carId, @Param("branchId") Long branchId);
    
    // Find event-based rules
    @Query("SELECT pr FROM PricingRule pr WHERE pr.isActive = true AND pr.eventType IS NOT NULL AND " +
           "((pr.car.id = :carId) OR (pr.branch.id = :branchId) OR (pr.car.id IS NULL AND pr.branch.id IS NULL)) " +
           "ORDER BY pr.priority DESC")
    List<PricingRule> findEventRules(@Param("carId") Long carId, @Param("branchId") Long branchId);
    
    // Find rules by priority range
    List<PricingRule> findByPriorityBetweenAndIsActiveTrue(Integer minPriority, Integer maxPriority);
    
    // Find rules created by specific user
    List<PricingRule> findByCreatedByIdAndIsActiveTrue(Long createdById);
    
    // Count active rules by type
    @Query("SELECT COUNT(pr) FROM PricingRule pr WHERE pr.ruleType = :ruleType AND pr.isActive = true")
    long countByRuleTypeAndIsActiveTrue(@Param("ruleType") PricingRule.PricingRuleType ruleType);
    
    // Count active rules by car
    @Query("SELECT COUNT(pr) FROM PricingRule pr WHERE pr.car.id = :carId AND pr.isActive = true")
    long countByCarIdAndIsActiveTrue(@Param("carId") Long carId);
    
    // Count active rules by branch
    @Query("SELECT COUNT(pr) FROM PricingRule pr WHERE pr.branch.id = :branchId AND pr.isActive = true")
    long countByBranchIdAndIsActiveTrue(@Param("branchId") Long branchId);
} 