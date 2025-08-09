package com.carrental.backend.service;

import com.carrental.backend.dto.UserDto;
import com.carrental.backend.entities.Role;
import com.carrental.backend.entities.User;
import com.carrental.backend.exception.ResourceNotFoundException;
import com.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get all users
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get user by ID
     */
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return convertToDto(user);
    }

    /**
     * Create new user
     */
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    /**
     * Update user
     */
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setRole(userDto.getRole());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    /**
     * Delete user
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Get users by role
     */
    public List<UserDto> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Search users
     */
    public List<UserDto> searchUsers(String query) {
        // For now, return all users since the repository method doesn't exist
        // TODO: Add the search method to UserRepository
        return userRepository.findAll().stream()
                .filter(user -> user.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                               user.getEmail().toLowerCase().contains(query.toLowerCase()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Update user role
     */
    public UserDto updateUserRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setRole(role);
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    /**
     * Get paginated users
     */
    public Page<UserDto> getUsersPage(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::convertToDto);
    }

    /**
     * Convert User entity to UserDto
     */
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setEnabled(true); // Default to true since User entity doesn't have enabled field
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
} 