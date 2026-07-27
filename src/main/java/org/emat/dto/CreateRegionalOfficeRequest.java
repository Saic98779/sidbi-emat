package org.emat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRegionalOfficeRequest {

    @NotBlank
    private String roId;

    @NotBlank
    private String roName;

    @NotBlank
    private String city;

    @NotBlank
    private String district;

    @NotBlank
    private String state;

    private String address;

    private String contactNo;
}