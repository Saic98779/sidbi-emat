package org.emat.service;

import org.emat.dto.CaptchaResponse;

/**
 * Backend-managed CAPTCHA service. Generation and validation of the CAPTCHA are handled entirely on
 * the server side: the expected answer is stored in-memory keyed by a random id and is never sent
 * to the client.
 */
public interface CaptchaService {

    /** Returns true if CAPTCHA enforcement is enabled ({@code security.captcha.enabled}). */
    boolean isEnabled();

    /** Generates a new CAPTCHA (id + rendered image) and stores its expected answer server-side. */
    CaptchaResponse generate();

    /**
     * Consumes (invalidates) the CAPTCHA with the given id, then validates it against the supplied
     * answer. The CAPTCHA can be used at most once regardless of the outcome.
     */
    boolean consumeAndValidate(String captchaId, String answer);
}
