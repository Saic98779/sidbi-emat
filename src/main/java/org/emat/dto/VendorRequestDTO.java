package org.emat.dto;

import lombok.Data;

@Data
public class VendorRequestDTO {

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
}