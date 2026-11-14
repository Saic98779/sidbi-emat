package org.emat.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.OtpPurpose;

/**
 * Active password OTP (and, after verification, reset token) for one user and purpose.
 *
 * <p>Only HMAC hashes of the OTP and reset token are stored, never the plain values. A user has at
 * most one row per {@link OtpPurpose}; requesting a new OTP overwrites the previous one.
 */
@Entity
@Table(
        name = "PASSWORD_OTP",
        uniqueConstraints =
                @UniqueConstraint(
                        name = "UK_PASSWORD_OTP_USER_PURPOSE",
                        columnNames = {"USER_ID", "PURPOSE"}))
@Data
@NoArgsConstructor
public class PasswordOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_otp_seq")
    @SequenceGenerator(
            name = "password_otp_seq",
            sequenceName = "PASSWORD_OTP_SEQ",
            allocationSize = 1)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PURPOSE", nullable = false, length = 30)
    private OtpPurpose purpose;

    /** HMAC of the OTP; {@code null} once the OTP has been used or invalidated. */
    @Column(name = "OTP_HASH", length = 128)
    private String otpHash;

    @Column(name = "OTP_EXPIRES_AT")
    private LocalDateTime otpExpiresAt;

    @Column(name = "FAILED_ATTEMPTS", nullable = false)
    private int failedAttempts;

    /** HMAC of the reset token issued after OTP verification; {@code null} when none is active. */
    @Column(name = "RESET_TOKEN_HASH", length = 128)
    private String resetTokenHash;

    @Column(name = "RESET_TOKEN_EXPIRES_AT")
    private LocalDateTime resetTokenExpiresAt;

    /** When the current OTP was issued; used for the resend wait time. */
    @Column(name = "ISSUED_AT", nullable = false)
    private LocalDateTime issuedAt;
}
