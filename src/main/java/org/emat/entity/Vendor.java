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
    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "VENDOR_ID")
    private String vendorId;

    @Column(name = "VENDOR_NAME")
    private String vendorName;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "SPOC_NAME")
    private String spocName;

    @Column(name = "SPOC_MOBILE_NO")
    private String spocMobileNo;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CONTACT_NO")
    private String contactNo;

    @Column(name = "GST_NO")
    private String gstNo;

    @Column(name = "PAN_NO")
    private String panNo;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "STATE")
    private String state;

    @Column(name = "PIN_CODE")
    private String pinCode;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "IFSC_CODE")
    private String ifscCode;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Column(name = "IS_ACTIVE")
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;
}