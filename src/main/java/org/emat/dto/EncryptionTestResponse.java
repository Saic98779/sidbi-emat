package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** Response body for the /testing/encrypt and /testing/decrypt helper endpoints. */
@Schema(description = "Helper endpoint test response")
public class EncryptionTestResponse {

    @Schema(description = "The transformed value (encrypted or decrypted)")
    private String value;

    public EncryptionTestResponse() {}

    public EncryptionTestResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}