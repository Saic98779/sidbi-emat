package org.emat.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VendorResponseDTO {

    private UUID uuid;
    private String vendorId;
    private String vendorName;
    private String companyName;
    private String spocName;
    private String spocMobileNo;
    private String email;
    private String mobileNo;
    private String gstNo;
    private String panNo;
    private String address;
    private String district;
    private String state;
    private String pinCode;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branchName;
    private Boolean active;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}