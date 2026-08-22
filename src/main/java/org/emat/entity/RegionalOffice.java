package org.emat.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "REGIONAL_OFFICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RegionalOffice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REGIONAL_OFFICE")
    @SequenceGenerator(name = "SEQ_REGIONAL_OFFICE", sequenceName = "SEQ_REGIONAL_OFFICE", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "RO_ID", nullable = false, unique = true, length = 50)
    private String roId;

    @Column(name = "RO_NAME", nullable = false, length = 200)
    private String roName;

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

    @OneToMany(mappedBy = "regionalOffice")
    private List<Branch> branches;

    @OneToMany(mappedBy = "regionalOffice")
    private List<SidbiSde> sdeList;
}