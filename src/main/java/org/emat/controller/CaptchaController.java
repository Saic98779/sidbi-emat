package org.emat.controller;

import org.emat.dto.ApiResponse;
import org.emat.dto.CaptchaResponse;
import org.emat.service.CaptchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for generating backend-managed CAPTCHAs used by the login page. */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CaptchaResponse>> generateCaptcha() {
        return ResponseEntity.ok(
                ApiResponse.success("Captcha generated successfully", captchaService.generate()));
    }
}
