package org.emat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BranchRequest {

    @NotBlank
    private String boId;

    @NotBlank
    private String branchName;

    @NotBlank
    private String city;

    @NotBlank
    private String district;

    @NotBlank
    private String state;

    private String address;

    private String contactNo;

    @NotBlank
    private String regionalOfficeUuid;
}