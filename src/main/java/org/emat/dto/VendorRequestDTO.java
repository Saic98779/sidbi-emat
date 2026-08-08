package org.emat.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VendorRequestDTO {

    // Optional field, but if provided, maximum 50 characters
    @Size(max = 50, message = "Vendor ID must not exceed 50 characters")
    private String vendorId;

    @NotBlank(message = "Vendor name is required")
    @Size(max = 200, message = "Vendor name must not exceed 200 characters")
    private String vendorName;

    @Size(max = 250, message = "Company name must not exceed 250 characters")
    private String companyName;

    @Size(max = 150, message = "Contact person must not exceed 150 characters")
    private String contactPerson;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 200, message = "Email must not exceed 200 characters")
    private String email;

    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Mobile number must be a valid 10-digit Indian mobile number"
    )
    private String mobileNo;

    @Pattern(
            regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][A-Z0-9]Z[A-Z0-9]$",
            message = "GST number must be a valid 15-character GSTIN"
    )
    private String gstNo;

    @Pattern(
            regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$",
            message = "PAN number must be a valid 10-character PAN"
    )
    private String panNo;

    @Size(max = 250, message = "Address must not exceed 250 characters")
    private String address;

    @Size(max = 100, message = "District must not exceed 100 characters")
    private String district;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Pattern(
            regexp = "^[0-9]{6}$",
            message = "PIN code must be a valid 6-digit PIN code"
    )
    private String pinCode;

    @Size(max = 150, message = "Bank name must not exceed 150 characters")
    private String bankName;

    @Size(max = 30, message = "Account number must not exceed 30 characters")
    private String accountNumber;

    @Pattern(
            regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
            message = "IFSC code must be a valid 11-character IFSC code"
    )
    private String ifscCode;

    @Size(max = 150, message = "Branch name must not exceed 150 characters")
    private String branchName;

    private Boolean active;
}