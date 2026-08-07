package org.emat.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "VENDOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "VENDOR_ID")
    private String vendorId;

    @Column(name = "VENDOR_NAME", nullable = false, length = 200)
    private String vendorName;

    @Column(name = "COMPANY_NAME", length = 250)
    private String companyName;

    @Column(name = "CONTACT_PERSON", length = 150)
    private String contactPerson;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "MOBILE_NO", length = 15)
    private String mobileNo;

    @Column(name = "GST_NO", length = 20)
    private String gstNo;

    @Column(name = "PAN_NO", length = 15)
    private String panNo;

    @Column(name = "ADDRESS", length = 250)
    private String address;

    @Column(name = "DISTRICT", length = 100)
    private String district;

    @Column(name = "STATE", length = 100)
    private String state;

    @Column(name = "PIN_CODE", length = 10)
    private String pinCode;

    @Column(name = "BANK_NAME", length = 150)
    private String bankName;

    @Column(name = "ACCOUNT_NUMBER", length = 30)
    private String accountNumber;

    @Column(name = "IFSC_CODE", length = 20)
    private String ifscCode;

    @Column(name = "BRANCH_NAME", length = 150)
    private String branchName;

    @Column(name = "CREATED_BY", length = 100)
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_BY", length = 100)
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Column(name = "IS_ACTIVE")
    private Boolean active = true;
}