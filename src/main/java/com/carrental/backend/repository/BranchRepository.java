package com.carrental.backend.repository;

import com.carrental.backend.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    
    List<Branch> findByCity(String city);
    
    List<Branch> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT b FROM Branch b WHERE LOWER(b.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    List<Branch> findByCityContainingIgnoreCase(@Param("city") String city);
    
    @Query("SELECT b FROM Branch b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(b.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    List<Branch> findByNameOrCityContainingIgnoreCase(@Param("name") String name, @Param("city") String city);
}

