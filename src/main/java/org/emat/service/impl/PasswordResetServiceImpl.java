package org.emat.service.impl;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ForgotPasswordRequest;
import org.emat.dto.ResetPasswordRequest;
import org.emat.dto.VerifyOtpRequest;
import org.emat.dto.VerifyOtpResponse;
import org.emat.entity.PasswordOtp;
import org.emat.entity.User;
import org.emat.enums.OtpPurpose;
import org.emat.exception.PasswordResetException;
import org.emat.repository.UserRepository;
import org.emat.service.PasswordResetService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Forgot / reset password flow. See {@link PasswordResetService} for the steps.
 *
 * <p>Transactions do not roll back on {@link PasswordResetException}, so failed OTP attempts are
 * still counted when an error is returned. Email failures do roll back, so a user whose email
 * could not be sent can ask again straight away.
 */
@Service
@Slf4j
@Transactional(noRollbackFor = PasswordResetException.class)
public class PasswordResetServiceImpl implements PasswordResetService {

    private static final String GENERIC_OTP_ERROR = "Invalid or expired OTP";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordOtpManager otpManager;
    private final PasswordPolicy passwordPolicy;

    public PasswordResetServiceImpl(
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
    public void forgotPassword(ForgotPasswordRequest request) {
        String email = requireEmail(request == null ? null : request.getEmail());
        Optional<User> user = findActiveUser(email);
        if (user.isEmpty()) {
            // Same response as a real account, so the endpoint can't reveal which emails exist.
            log.info("Forgot-password requested for an unknown or inactive email; ignoring");
            return;
        }
        PasswordOtpManager.OtpIssue issue =
                otpManager.issueOtp(user.get(), OtpPurpose.FORGOT_PASSWORD);
        if (!issue.issued()) {
            // An error here would also reveal that the account exists, so ignore it quietly;
            // the frontend shows its own resend timer.
            log.info(
                    "Forgot-password OTP for user id {} requested again within the wait time",
                    user.get().getId());
            return;
        }
        otpManager.sendOtpEmail(user.get(), issue.otp(), OtpPurpose.FORGOT_PASSWORD);
    }

    @Override
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        if (request == null) {
            throw new PasswordResetException("Email and OTP are required");
        }
        String email = requireEmail(request.getEmail());
        if (isBlank(request.getOtp())) {
            throw new PasswordResetException("OTP is required");
        }
        User user =
                findActiveUser(email).orElseThrow(() -> new PasswordResetException(GENERIC_OTP_ERROR));
        PasswordOtp row = otpManager.consumeOtp(user, OtpPurpose.FORGOT_PASSWORD, request.getOtp());
        String token = otpManager.issueResetToken(row);
        return new VerifyOtpResponse(token, otpManager.resetTokenExpirySeconds());
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        if (request == null) {
            throw new PasswordResetException("Request body is required");
        }
        String email = requireEmail(request.getEmail());
        if (isBlank(request.getResetToken())) {
            throw new PasswordResetException("Reset token is required. Please verify the OTP first.");
        }
        // Format rules first: they say nothing about the account, and a bad password then
        // doesn't use up the token.
        passwordPolicy.validateFormat(request.getNewPassword(), request.getConfirmPassword());

        User user =
                findActiveUser(email)
                        .orElseThrow(
                                () ->
                                        new PasswordResetException(
                                                "Invalid or expired reset token. Please verify the"
                                                        + " OTP again."));

        otpManager.consumeResetToken(user, OtpPurpose.FORGOT_PASSWORD, request.getResetToken());

        if (passwordPolicy.isSameAsCurrent(request.getNewPassword(), user.getPassword())) {
            // Undo using up the token so the user can retry with a different password.
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new PasswordResetException(
                    "New password must be different from the current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        otpManager.clear(user, OtpPurpose.FORGOT_PASSWORD);
        otpManager.clear(user, OtpPurpose.CHANGE_PASSWORD);
        log.info("Password reset via forgot-password for user id {}", user.getId());
    }

    private Optional<User> findActiveUser(String email) {
        return userRepository.findFirstByEmailIgnoreCase(email).filter(User::isActive);
    }

    private static String requireEmail(String email) {
        if (isBlank(email)) {
            throw new PasswordResetException("Email is required");
        }
        return email.trim();
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
