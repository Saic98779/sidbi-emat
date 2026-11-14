package org.emat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ChangePasswordRequest;
import org.emat.entity.User;
import org.emat.enums.OtpPurpose;
import org.emat.exception.PasswordResetException;
import org.emat.repository.UserRepository;
import org.emat.service.ChangePasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Change password for a logged-in user. See {@link ChangePasswordService}. */
@Service
@Slf4j
@Transactional
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordOtpManager otpManager;
    private final PasswordPolicy passwordPolicy;

    public ChangePasswordServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PasswordOtpManager otpManager,
            PasswordPolicy passwordPolicy) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpManager = otpManager;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public void changePassword(String username, ChangePasswordRequest request) {
        if (request == null) {
            throw new PasswordResetException("Request body is required");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new PasswordResetException("Email is required");
        }
        if (request.getCurrentPassword() == null || request.getCurrentPassword().isEmpty()) {
            throw new PasswordResetException("Current password is required");
        }
        User user = loadActiveUser(username);

        if (user.getEmail() == null || !user.getEmail().equalsIgnoreCase(request.getEmail().trim())) {
            log.warn("Change-password attempt with an email that is not the user's, user id {}", user.getId());
            throw new PasswordResetException("Email does not match your account");
        }
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            log.warn("Change-password attempt with wrong current password for user id {}", user.getId());
            throw new PasswordResetException("Current password is incorrect");
        }
        passwordPolicy.validateFormat(request.getNewPassword(), request.getConfirmPassword());
        passwordPolicy.ensureDifferent(request.getNewPassword(), user.getPassword());

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        // Any pending forgot-password OTP / reset token is now stale.
        otpManager.clear(user, OtpPurpose.FORGOT_PASSWORD);
        otpManager.clear(user, OtpPurpose.CHANGE_PASSWORD);
        log.info("Password changed for user id {}", user.getId());
    }

    private User loadActiveUser(String username) {
        if (username == null || username.isBlank()) {
            throw new PasswordResetException("You must be logged in to change your password");
        }
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new PasswordResetException("User not found"));
        if (!user.isActive()) {
            throw new PasswordResetException("This account is inactive");
        }
        return user;
    }
}
