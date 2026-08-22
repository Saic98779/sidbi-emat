package org.emat.repository;

import org.emat.entity.IndustryAssociationAppraisal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for IndustryAssociationAppraisal entity.
 * Provides database access operations for Industry Association Appraisal records.
 */
@Repository
public interface IndustryAssociationAppraisalRepository extends JpaRepository<IndustryAssociationAppraisal, Long> {

    /**
     * Find all active appraisals.
     *
     * @return List of active IndustryAssociationAppraisal records
     */
    List<IndustryAssociationAppraisal> findAllByIsActiveTrue();

    /**
     * Find all active appraisals by state, district and SIDBI approval status.
     *
     * @param state state name
     * @param district district name
     * @param isSidbeApproved SIDBI approval status
     * @return list of matching appraisals
     */
    List<IndustryAssociationAppraisal> findAllByIsActiveTrueAndRegistrationStateAndRegistrationDistrictAndIsSidbeApproved(
            String state,
            String district,
            Boolean isSidbeApproved
    );

    Optional<IndustryAssociationAppraisal> findByRegistrationId(Long registrationId);

    boolean existsByRegistrationId(Long registrationId);
}
