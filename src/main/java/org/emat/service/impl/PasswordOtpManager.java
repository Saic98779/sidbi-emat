package org.emat.service.impl;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.emat.entity.PasswordOtp;
import org.emat.entity.User;
import org.emat.enums.OtpPurpose;
import org.emat.exception.EmailDeliveryException;
import org.emat.exception.PasswordResetException;
import org.emat.repository.PasswordOtpRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * OTP and reset-token handling shared by the forgot-password and change-password flows.
 *
 * <ul>
 *   <li>OTPs are 6 digits from {@link SecureRandom}; reset tokens are 32 random bytes (base64url).
 *   <li>Only HMAC-SHA256 hashes are stored, keyed with a server secret, so a database dump cannot
 *       be used to guess OTPs offline.
 *   <li>Wrong OTPs are counted; after {@code max-attempts} the OTP stops working.
 *   <li>A new OTP can only be requested after {@code resend-cooldown-seconds}.
 * </ul>
 *
 * <p>Callers must run inside a transaction that does <strong>not</strong> roll back on {@link
 * PasswordResetException}; otherwise the failed-attempt count would be lost.
 */
@Component
@Slf4j
public class PasswordOtpManager {

    private static final String GENERIC_OTP_ERROR = "Invalid or expired OTP";
    private static final String GENERIC_TOKEN_ERROR =
            "Invalid or expired reset token. Please verify the OTP again.";
    private static final int OTP_LENGTH = 6;
    private static final int RESET_TOKEN_BYTES = 32;
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final SecureRandom random = new SecureRandom();
    private final PasswordOtpRepository otpRepository;
    private final JavaMailSender mailSender;

    @Value("${security.password-reset.mail-from:}")
    private String mailFrom;

    @Value("${security.password-reset.mail-subject:EMAT - Password Reset Verification Code}")
    private String forgotMailSubject;

    @Value(
            "${security.password-reset.change-mail-subject:EMAT - Change Password Verification"
                    + " Code}")
    private String changeMailSubject;

    @Value("${security.password-reset.otp-expiry-minutes:5}")
    private long otpExpiryMinutes;

    @Value("${security.password-reset.max-attempts:5}")
    private int maxAttempts;

    @Value("${security.password-reset.resend-cooldown-seconds:60}")
    private long resendCooldownSeconds;

    @Value("${security.password-reset.reset-token-expiry-minutes:10}")
    private long resetTokenExpiryMinutes;

    @Value("${security.password-reset.hash-secret:${jwt.secret}}")
    private String hashSecret;

    private SecretKeySpec hmacKey;

    public PasswordOtpManager(PasswordOtpRepository otpRepository, JavaMailSender mailSender) {
        this.otpRepository = otpRepository;
        this.mailSender = mailSender;
    }

    @PostConstruct
    void init() {
        if (hashSecret == null || hashSecret.length() < 32) {
            throw new IllegalStateException(
                    "security.password-reset.hash-secret must be at least 32 characters");
        }
        hmacKey = new SecretKeySpec(hashSecret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
        otpExpiryMinutes = Math.max(otpExpiryMinutes, 1);
        maxAttempts = Math.max(maxAttempts, 1);
        resendCooldownSeconds = Math.max(resendCooldownSeconds, 0);
        resetTokenExpiryMinutes = Math.max(resetTokenExpiryMinutes, 1);
    }

    /** Result of {@link #issueOtp}: either the new OTP, or how long to wait before asking again. */
    public record OtpIssue(String otp, long retryAfterSeconds) {
        public boolean issued() {
            return otp != null;
        }
    }

    /**
     * Creates a new OTP for the user, replacing any previous OTP or reset token for the same
     * purpose. Returns {@code retryAfterSeconds > 0} (and no OTP) if the last one was issued less
     * than the resend wait time ago.
     */
    public OtpIssue issueOtp(User user, OtpPurpose purpose) {
        LocalDateTime now = LocalDateTime.now();
        PasswordOtp row =
                otpRepository
                        .findByUserIdAndPurpose(user.getId(), purpose)
                        .orElseGet(
                                () -> {
                                    PasswordOtp created = new PasswordOtp();
                                    created.setUserId(user.getId());
                                    created.setPurpose(purpose);
                                    return created;
                                });

        if (row.getIssuedAt() != null) {
            long elapsed = Duration.between(row.getIssuedAt(), now).getSeconds();
            if (elapsed < resendCooldownSeconds) {
                return new OtpIssue(null, resendCooldownSeconds - elapsed);
            }
        }

        String otp = randomOtp();
        row.setOtpHash(hash(purpose, user.getId(), "otp", otp));
        row.setOtpExpiresAt(now.plusMinutes(otpExpiryMinutes));
        row.setFailedAttempts(0);
        row.setResetTokenHash(null);
        row.setResetTokenExpiresAt(null);
        row.setIssuedAt(now);
        otpRepository.save(row);
        return new OtpIssue(otp, 0);
    }

    /**
     * Checks the OTP and uses it up on success. A wrong OTP counts as a failed attempt; when the
     * limit is reached the OTP is cancelled.
     *
     * @return the (locked) row, so the caller can go on to issue a reset token
     * @throws PasswordResetException if the OTP is missing, wrong, expired or used up
     */
    public PasswordOtp consumeOtp(User user, OtpPurpose purpose, String otp) {
        PasswordOtp row =
                otpRepository
                        .findByUserIdAndPurpose(user.getId(), purpose)
                        .orElseThrow(() -> new PasswordResetException(GENERIC_OTP_ERROR));

        if (row.getOtpHash() == null
                || row.getOtpExpiresAt() == null
                || LocalDateTime.now().isAfter(row.getOtpExpiresAt())) {
            throw new PasswordResetException(GENERIC_OTP_ERROR);
        }

        String given = otp == null ? "" : otp.trim();
        if (!given.matches("\\d{" + OTP_LENGTH + "}")
                || !constantTimeEquals(row.getOtpHash(), hash(purpose, user.getId(), "otp", given))) {
            int attempts = row.getFailedAttempts() + 1;
            row.setFailedAttempts(attempts);
            if (attempts >= maxAttempts) {
                row.setOtpHash(null);
                row.setOtpExpiresAt(null);
                otpRepository.save(row);
                log.warn("OTP for user id {} ({}) cancelled after {} failed attempts",
                        user.getId(), purpose, attempts);
                throw new PasswordResetException(
                        "Too many incorrect attempts. Please request a new OTP.");
            }
            otpRepository.save(row);
            throw new PasswordResetException(
                    GENERIC_OTP_ERROR + ". " + (maxAttempts - attempts) + " attempt(s) left.");
        }

        row.setOtpHash(null);
        row.setOtpExpiresAt(null);
        row.setFailedAttempts(0);
        otpRepository.save(row);
        return row;
    }

    /** Issues a single-use reset token on a row whose OTP was just used. Returns the plain token. */
    public String issueResetToken(PasswordOtp row) {
        byte[] bytes = new byte[RESET_TOKEN_BYTES];
        random.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        row.setResetTokenHash(hash(row.getPurpose(), row.getUserId(), "token", token));
        row.setResetTokenExpiresAt(LocalDateTime.now().plusMinutes(resetTokenExpiryMinutes));
        otpRepository.save(row);
        return token;
    }

    /**
     * Checks the reset token and uses it up on success.
     *
     * @throws PasswordResetException if the token is missing, wrong or expired
     */
    public void consumeResetToken(User user, OtpPurpose purpose, String token) {
        PasswordOtp row =
                otpRepository
                        .findByUserIdAndPurpose(user.getId(), purpose)
                        .orElseThrow(() -> new PasswordResetException(GENERIC_TOKEN_ERROR));
        if (row.getResetTokenHash() == null
                || row.getResetTokenExpiresAt() == null
                || LocalDateTime.now().isAfter(row.getResetTokenExpiresAt())
                || token == null
                || !constantTimeEquals(
                        row.getResetTokenHash(),
                        hash(purpose, user.getId(), "token", token.trim()))) {
            throw new PasswordResetException(GENERIC_TOKEN_ERROR);
        }
        row.setResetTokenHash(null);
        row.setResetTokenExpiresAt(null);
        otpRepository.save(row);
    }

    /** Removes any OTP / reset token the user holds for the purpose (e.g. after a password change). */
    public void clear(User user, OtpPurpose purpose) {
        otpRepository.findByUserIdAndPurpose(user.getId(), purpose).ifPresent(otpRepository::delete);
    }

    public long resetTokenExpirySeconds() {
        return resetTokenExpiryMinutes * 60;
    }

    public long otpExpirySeconds() {
        return otpExpiryMinutes * 60;
    }

    /**
     * Emails the OTP to the user's registered address.
     *
     * @throws EmailDeliveryException if the mail server rejects or cannot be reached
     */
    public void sendOtpEmail(User user, String otp, OtpPurpose purpose) {
        boolean forgot = purpose == OtpPurpose.FORGOT_PASSWORD;
        String name =
                user.getFirstName() != null && !user.getFirstName().isBlank()
                        ? user.getFirstName().trim()
                        : user.getUsername();
        String action = forgot ? "reset your EMAT password" : "change your EMAT password";

        SimpleMailMessage message = new SimpleMailMessage();
        if (mailFrom != null && !mailFrom.isBlank()) {
            message.setFrom(mailFrom);
        }
        message.setTo(user.getEmail());
        message.setSubject(forgot ? forgotMailSubject : changeMailSubject);
        message.setText(
                "Dear "
                        + name
                        + ",\n\n"
                        + "Your verification code to "
                        + action
                        + " is: "
                        + otp
                        + "\n\n"
                        + "This code expires in "
                        + otpExpiryMinutes
                        + " minute(s). Do not share it with anyone.\n\n"
                        + "If you did not request this, you can ignore this email; your password"
                        + " will not change.\n\n"
                        + "Regards,\nEMAT Team");
        try {
            mailSender.send(message);
            log.info("{} OTP email sent to user id {}", purpose, user.getId());
        } catch (MailException ex) {
            log.error("Failed to send {} OTP email to user id {}", purpose, user.getId(), ex);
            throw new EmailDeliveryException(
                    "Unable to send the OTP email right now. Please try again later.", ex);
        }
    }

    private String randomOtp() {
        int bound = (int) Math.pow(10, OTP_LENGTH);
        return String.format("%0" + OTP_LENGTH + "d", random.nextInt(bound));
    }

    private String hash(OtpPurpose purpose, Long userId, String kind, String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(hmacKey);
            String input = purpose.name() + "|" + userId + "|" + kind + "|" + value;
            return HexFormat.of().formatHex(mac.doFinal(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new IllegalStateException("HMAC-SHA256 not available", ex);
        }
    }

    private static boolean constantTimeEquals(String a, String b) {
        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
    }
}
