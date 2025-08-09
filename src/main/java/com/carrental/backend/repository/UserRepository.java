package com.carrental.backend.repository;

import com.carrental.backend.entities.User;
import com.carrental.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Boolean existsByEmail(String email);
    
    List<User> findByRole(Role role);
    
    @Query("SELECT u FROM User u WHERE u.branch.id = :branchId")
    List<User> findByBranchId(@Param("branchId") Long branchId);
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.branch.id = :branchId")
    List<User> findByRoleAndBranchId(@Param("role") Role role, @Param("branchId") Long branchId);
    
    Boolean existsByRole(Role role);
    
    Optional<User> findByEmailAndRole(String email, Role role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :dateTime")
    long countByCreatedAtAfter(@Param("dateTime") LocalDateTime dateTime);
}

