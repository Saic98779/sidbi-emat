package org.emat.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateSidbiSdeRequest {

    private String name;

    @Email
    private String email;

    private String mobileNo;

    private String regionalOfficeUuid;
}