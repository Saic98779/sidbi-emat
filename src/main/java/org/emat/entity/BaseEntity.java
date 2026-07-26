package org.emat.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Base entity class containing common audit fields.
 * All entities should extend this class to inherit audit tracking capabilities.
 */
@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_BY", length = 100)
    private String createdBy;

    @Column(name = "UPDATED_BY", length = 100)
    private String updatedBy;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

