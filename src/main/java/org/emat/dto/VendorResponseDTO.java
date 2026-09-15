package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Data;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

@Data
public class VendorResponseDTO {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private String vendorId;
    private String vendorName;
    private String companyName;
    private String spocName;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String spocMobileNo;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String email;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
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
