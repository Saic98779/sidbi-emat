package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** Payload returned by the CAPTCHA generation endpoint. */
@Schema(description = "Generated CAPTCHA with its id and image data")
public class CaptchaResponse {

    @Schema(
            description = "Unique id of the generated CAPTCHA, must be echoed back on login",
            example = "0b9a1f2e-....")
    private String captchaId;

    @Schema(
            description = "CAPTCHA image as a PNG data URI (data:image/png;base64,...)",
            example = "data:image/png;base64,iVBORw0KGgo...")
    private String image;

    @Schema(description = "Seconds before the CAPTCHA expires", example = "300")
    private long expiresInSeconds;

    public CaptchaResponse() {}

    public CaptchaResponse(String captchaId, String image, long expiresInSeconds) {
        this.captchaId = captchaId;
        this.image = image;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }
}
