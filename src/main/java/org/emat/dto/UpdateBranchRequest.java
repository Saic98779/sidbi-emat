package org.emat.dto;

import lombok.Data;

@Data
public class UpdateBranchRequest {

    private String branchName;

    private String city;

    private String district;

    private String state;

    private String address;

    private String contactNo;

    private String regionalOfficeUuid;
}