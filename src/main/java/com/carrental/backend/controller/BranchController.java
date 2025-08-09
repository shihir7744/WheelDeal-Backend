package com.carrental.backend.controller;

import com.carrental.backend.dto.BranchDto;
import com.carrental.backend.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranches() {
        List<BranchDto> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) {
        BranchDto branch = branchService.getBranchById(id);
        return ResponseEntity.ok(branch);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<BranchDto>> getBranchesByCity(@PathVariable String city) {
        List<BranchDto> branches = branchService.getBranchesByCity(city);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BranchDto>> searchBranches(@RequestParam String q) {
        List<BranchDto> branches = branchService.searchBranches(q);
        return ResponseEntity.ok(branches);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BranchDto> createBranch(@Valid @RequestBody BranchDto branchDto) {
        BranchDto createdBranch = branchService.createBranch(branchDto);
        return ResponseEntity.ok(createdBranch);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @Valid @RequestBody BranchDto branchDto) {
        BranchDto updatedBranch = branchService.updateBranch(id, branchDto);
        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.ok().build();
    }
}

