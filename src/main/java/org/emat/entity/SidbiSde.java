package org.emat.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SIDBI_SDE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SidbiSde extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SIDBI_SDE")
    @SequenceGenerator(name = "SEQ_SIDBI_SDE", sequenceName = "SEQ_SIDBI_SDE", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "SDE_ID", nullable = false, unique = true, length = 50)
    private String sdeId;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "MOBILE_NO", length = 20)
    private String mobileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RO_ID")
    private RegionalOffice regionalOffice;
}