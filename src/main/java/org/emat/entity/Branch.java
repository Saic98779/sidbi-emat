package org.emat.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "BRANCH")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Branch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "BO_ID", nullable = false, unique = true, length = 50)
    private String boId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RO_UUID", nullable = false)
    private RegionalOffice regionalOffice;

    @Column(name = "BRANCH_NAME", nullable = false, length = 200)
    private String branchName;

    @Column(name = "CITY", length = 100)
    private String city;

    @Column(name = "DISTRICT", length = 100)
    private String district;

    @Column(name = "STATE", length = 100)
    private String state;

    @Column(name = "ADDRESS", length = 500)
    private String address;

    @Column(name = "CONTACT_NO", length = 20)
    private String contactNo;
}