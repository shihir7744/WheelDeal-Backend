package com.carrental.backend.service;

import com.carrental.backend.dto.BranchDto;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.entities.Branch;
import com.carrental.backend.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public List<BranchDto> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BranchDto getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", id));
        return convertToDto(branch);
    }

    public List<BranchDto> getBranchesByCity(String city) {
        return branchRepository.findByCityContainingIgnoreCase(city).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BranchDto> searchBranches(String searchTerm) {
        return branchRepository.findByNameOrCityContainingIgnoreCase(searchTerm, searchTerm).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = new Branch();
        branch.setName(branchDto.getName());
        branch.setCity(branchDto.getCity());
        branch.setAddress(branchDto.getAddress());

        Branch savedBranch = branchRepository.save(branch);
        return convertToDto(savedBranch);
    }

    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", id));

        if (branchDto.getName() != null) branch.setName(branchDto.getName());
        if (branchDto.getCity() != null) branch.setCity(branchDto.getCity());
        if (branchDto.getAddress() != null) branch.setAddress(branchDto.getAddress());

        Branch updatedBranch = branchRepository.save(branch);
        return convertToDto(updatedBranch);
    }

    public void deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", id));
        branchRepository.delete(branch);
    }

    private BranchDto convertToDto(Branch branch) {
        return new BranchDto(
                branch.getId(),
                branch.getName(),
                branch.getCity(),
                branch.getAddress(),
                branch.getCars().size()
        );
    }
}

