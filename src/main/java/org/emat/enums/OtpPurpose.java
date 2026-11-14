package org.emat.enums;

/** What a password OTP was issued for. A user can hold one active OTP per purpose. */
public enum OtpPurpose {
    /** Unauthenticated forgot / reset password flow ({@code /auth/...}). */
    FORGOT_PASSWORD,
    /**
     * No longer issued: change password no longer uses an OTP. Kept so existing rows still load
     * and can be cleared.
     */
    CHANGE_PASSWORD
}
