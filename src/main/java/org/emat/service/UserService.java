package org.emat.service;

import org.emat.dto.CreateUserRequest;
import org.emat.dto.UserResponse;
import org.emat.entity.User;
import org.emat.entity.Vendor;
import org.emat.enums.Role;
import org.emat.repository.UserRepository;
import org.emat.repository.VendorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for User operations.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VendorRepository vendorRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,VendorRepository vendorRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.vendorRepository = vendorRepository;
    }

    /**
     * Create a new user.
     */
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDistrict(request.getDistrict());
        user.setState(request.getState());
        user.setRole(request.getRole());
        user.setContactNo(request.getContactNo());
        user.setActive(true);

        User savedUser = userRepository.save(user);

        if (Role.MANPOWER_AGENCY.equals(request.getRole())) {

            Vendor vendor = new Vendor();
            vendor.setVendorName(request.getFirstName()+" "+request.getLastName());
            vendor.setEmail(request.getEmail());
            vendor.setUser(savedUser);
            vendor.setDistrict(request.getDistrict());
            vendor.setState(request.getState());
            vendor.setContactNo(request.getContactNo());
            vendorRepository.save(vendor);
        }

        return convertToResponse(savedUser);
    }

    /**
     * Get a user by ID.
     */
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return convertToResponse(user);
    }

    /**
     * Get a user by username.
     */
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return convertToResponse(user);
    }

    /**
     * Get all users.
     */
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponse).toList();
    }

    /**
     * Get users by district.
     */
    public List<UserResponse> getUsersByDistrict(String district) {
        return userRepository.findByDistrict(district)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Get users by state.
     */
    public List<UserResponse> getUsersByState(String state) {
        return userRepository.findByState(state)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Get users by district and state.
     */
    public List<UserResponse> getUsersByDistrictAndState(String district, String state) {
        return userRepository.findByDistrictAndState(district, state)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Get users by role.
     */
    public List<UserResponse> getUsersByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Get users by district and role.
     */
    public List<UserResponse> getUsersByDistrictAndRole(String district, Role role) {
        return userRepository.findByDistrictAndRole(district, role)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Get users by state and role.
     */
    public List<UserResponse> getUsersByStateAndRole(String state, Role role) {
        return userRepository.findByStateAndRole(state, role)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Get users by district, state, and role.
     */
    public List<UserResponse> getUsersByDistrictAndStateAndRole(String district, String state, Role role) {
        return userRepository.findByDistrictAndStateAndRole(district, state, role)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Convert User entity to UserResponse DTO.
     */
    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDistrict(),
                user.getState(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getContactNo()
        );
    }
}

