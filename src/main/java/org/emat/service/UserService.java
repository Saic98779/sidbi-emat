package org.emat.service;

import java.util.List;
import org.emat.dto.CreateUserRequest;
import org.emat.dto.UserResponse;
import org.emat.enums.Role;

/** Service class for User operations. */
public interface UserService {

    /** Create a new user. */
    UserResponse createUser(CreateUserRequest request);

    /** Get a user by ID. */
    UserResponse getUserById(Long id);

    /** Get a user by username. */
    UserResponse getUserByUsername(String username);

    /** Get all users. */
    List<UserResponse> getAllUsers();

    /** Get users by district. */
    List<UserResponse> getUsersByDistrict(String district);

    /** Get users by state. */
    List<UserResponse> getUsersByState(String state);

    /** Get users by district and state. */
    List<UserResponse> getUsersByDistrictAndState(String district, String state);

    /** Get users by role. */
    List<UserResponse> getUsersByRole(Role role);

    /** Get users by district and role. */
    List<UserResponse> getUsersByDistrictAndRole(String district, Role role);

    /** Get users by state and role. */
    List<UserResponse> getUsersByStateAndRole(String state, Role role);

    /** Get users by district, state, and role. */
    List<UserResponse> getUsersByDistrictAndStateAndRole(String district, String state, Role role);
}
