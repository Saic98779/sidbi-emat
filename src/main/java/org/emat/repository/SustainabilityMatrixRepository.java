package org.emat.repository;

import org.emat.entity.SustainabilityMatrix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SustainabilityMatrixRepository
        extends JpaRepository<SustainabilityMatrix, UUID> {

    List<SustainabilityMatrix> findByIndustryAssociationAppraisalUuid(
            UUID appraisalUuid
    );

    boolean existsByIndustryAssociationAppraisalUuid(
            UUID appraisalUuid
    );
}