package org.emat.repository;

import org.emat.entity.IndustryAssociationAppraisal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for IndustryAssociationAppraisal entity.
 * Provides database access operations for Industry Association Appraisal records.
 */
@Repository
public interface IndustryAssociationAppraisalRepository extends JpaRepository<IndustryAssociationAppraisal, String> {

    /**
     * Find all active appraisals.
     *
     * @return List of active IndustryAssociationAppraisal records
     */
    List<IndustryAssociationAppraisal> findAllByIsActiveTrue();

    /**
     * Find appraisal by UUID.
     *
     * @param uuid the unique identifier
     * @return Optional containing the appraisal if found
     */
    Optional<IndustryAssociationAppraisal> findByUuid(UUID uuid);

    /**
     * Find appraisal by registration UUID.
     *
     * @param registrationUuid the registration unique identifier
     * @return Optional containing the appraisal if found
     */
    Optional<IndustryAssociationAppraisal> findByRegistrationUuid(UUID registrationUuid);

    /**
     * Check if appraisal exists for a registration.
     *
     * @param registrationUuid the registration unique identifier
     * @return true if exists, false otherwise
     */
    boolean existsByRegistrationUuid(UUID registrationUuid);
}

