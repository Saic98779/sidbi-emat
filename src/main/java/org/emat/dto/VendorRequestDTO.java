package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

@Data
public class VendorRequestDTO {

    private String vendorId;
    private String vendorName;
    private String companyName;
    private String spocName;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String spocMobileNo;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
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
