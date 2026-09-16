package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import org.emat.dto.serializer.RsaStringDecryptDeserializer;

/** Request DTO for login API. */
public class LoginRequest {

    private String username;

    @JsonDeserialize(using = RsaStringDecryptDeserializer.class)
    private String password;

    /** Id of the CAPTCHA obtained from GET /captcha. */
    private String captchaId;

    /** User-entered CAPTCHA answer shown in the CAPTCHA image. */
    private String captchaAnswer;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest(String username, String password, String captchaId, String captchaAnswer) {
        this.username = username;
        this.password = password;
        this.captchaId = captchaId;
        this.captchaAnswer = captchaAnswer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaAnswer() {
        return captchaAnswer;
    }

    public void setCaptchaAnswer(String captchaAnswer) {
        this.captchaAnswer = captchaAnswer;
    }
}
