package org.emat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BranchResponse {

    private UUID uuid;

    private String boId;

    private String branchName;

    private String city;

    private String district;

    private String state;

    private String address;

    private String contactNo;

    private String regionalOfficeUuid;

    private String roId;

    private String roName;

    private Boolean isActive;
}