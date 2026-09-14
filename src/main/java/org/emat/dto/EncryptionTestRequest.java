package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** Request body for the /testing/encrypt and /testing/decrypt helper endpoints. */
@Schema(description = "Helper endpoint test request")
public class EncryptionTestRequest {

    @Schema(description = "The value to encrypt or decrypt", example = "jdoe@example.com")
    private String value;

    public EncryptionTestRequest() {}

    public EncryptionTestRequest(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}