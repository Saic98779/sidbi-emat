package org.emat.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "UPLOADED_FILES")
@Data
@SuperBuilder
@NoArgsConstructor
public class UploadedFile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "REGISTRATION_UUID", nullable = false)
    private String registrationUuid;

    @Column(name = "FILENAME", nullable = false)
    private String filename;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String contentType;

    @Column(name = "FILE_SIZE", nullable = false)
    private Long size;

    @Column(name = "RELATIVE_PATH", nullable = false)
    private String relativePath; // relative to storage base dir

    // createdAt is inherited from BaseEntity; do not redeclare here
}
