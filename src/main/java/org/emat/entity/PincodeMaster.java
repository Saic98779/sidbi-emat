package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Reference master for State -> District(Localbody) -> Pincode mapping. Seeded from the
 * "Pincodeto_Urban" Excel. The source file has no District column, so LOCALBODY_NAME (Localbody
 * Name) is stored in the DISTRICT column.
 */
@Entity
@Table(
        name = "PINCODE_MASTER",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "UK_PINCODE_STATE_DISTRICT_PIN",
                    columnNames = {"STATE_NAME", "DISTRICT", "PINCODE"})
        },
        indexes = {
            @Index(name = "IDX_PINCODE_STATE", columnList = "STATE_NAME"),
            @Index(name = "IDX_PINCODE_STATE_DISTRICT", columnList = "STATE_NAME,DISTRICT")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PincodeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PINCODE_MASTER")
    @SequenceGenerator(
            name = "SEQ_PINCODE_MASTER",
            sequenceName = "SEQ_PINCODE_MASTER",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "STATE_CODE", length = 10)
    private String stateCode;

    @Column(name = "STATE_NAME", nullable = false, length = 150)
    private String stateName;

    @Column(name = "DISTRICT", nullable = false, length = 200)
    private String district;

    @Column(name = "LOCALBODY_TYPE", length = 100)
    private String localbodyType;

    @Column(name = "PINCODE", nullable = false, length = 10)
    private String pincode;
}
