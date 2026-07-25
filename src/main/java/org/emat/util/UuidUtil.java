package org.emat.util;

import java.util.UUID;

/**
 * Utility class for UUID operations.
 * Provides helper methods for UUID conversion and validation.
 */
public class UuidUtil {

    private UuidUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Convert a RAW UUID hex string from Oracle database to a Java UUID.
     * Oracle stores UUIDs as RAW(16) which returns as 32-character hex string without dashes.
     * This method formats it back to standard UUID format with dashes.
     *
     * @param hex the 32-character hex string from Oracle RAW UUID
     * @return the Java UUID object
     * @throws IllegalArgumentException if the hex string is null or not 32 characters
     */
    public static UUID rawHexToUuid(String hex) {
        if (hex == null || hex.length() != 32) {
            throw new IllegalArgumentException("Invalid RAW UUID: " + hex);
        }

        String uuid = hex.replaceFirst(
                "([0-9A-Fa-f]{8})([0-9A-Fa-f]{4})([0-9A-Fa-f]{4})([0-9A-Fa-f]{4})([0-9A-Fa-f]{12})",
                "$1-$2-$3-$4-$5");

        return UUID.fromString(uuid);
    }

    /**
     * Safely convert a string to UUID, handling both standard UUID format and RAW hex format.
     *
     * @param uuidString the UUID string (either with dashes or 32-char hex)
     * @return the Java UUID object
     * @throws IllegalArgumentException if the string is invalid
     */
    public static UUID toUuid(String uuidString) {
        if (uuidString == null) {
            throw new IllegalArgumentException("UUID string cannot be null");
        }

        // If it contains dashes, it's already in standard format
        if (uuidString.contains("-")) {
            return UUID.fromString(uuidString);
        }

        // Otherwise, treat it as RAW hex format
        return rawHexToUuid(uuidString);
    }
}

