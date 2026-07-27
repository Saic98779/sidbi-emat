package org.emat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SidbiSdeRequest {

    @NotBlank
    private String sdeId;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String mobileNo;

    @NotBlank
    private String regionalOfficeUuid;
}