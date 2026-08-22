package org.emat.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "BRANCH")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Branch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BRANCH")
    @SequenceGenerator(name = "SEQ_BRANCH", sequenceName = "SEQ_BRANCH", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "BO_ID", nullable = false, unique = true, length = 50)
    private String boId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RO_ID", nullable = false)
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

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", boId='" + boId + '\'' +
                ", regionalOffice=" + regionalOffice +
                ", branchName='" + branchName + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}