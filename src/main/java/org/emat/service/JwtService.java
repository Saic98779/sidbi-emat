package org.emat.service;

import java.time.Instant;

/** Service for generating JWT tokens. */
public interface JwtService {

    String generateToken(String username);

    Instant getExpiryFromNow();
}
