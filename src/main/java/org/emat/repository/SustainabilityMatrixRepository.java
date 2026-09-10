package org.emat.repository;

import org.emat.entity.SustainabilityMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SustainabilityMatrixRepository extends JpaRepository<SustainabilityMatrix, Long> {

    List<SustainabilityMatrix> findByIndustryAssociationAppraisal_Id(@Param("appraisalId") @NonNull Long appraisalId);

    boolean existsByIndustryAssociationAppraisal_Id(@Param("appraisalId") @NonNull Long appraisalId);
}