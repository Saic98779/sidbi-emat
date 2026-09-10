package org.emat.repository;

import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Repository for IndustryAssociationRegistration entity.
 * Provides database access operations for Industry Association Registration records.
 */
@Repository
public interface IndustryAssociationRegistrationRepository extends JpaRepository<IndustryAssociationRegistration, Long> {

    /**
     * Find all active registrations.
     *
     * @return List of active IndustryAssociationRegistration records
     */
    List<IndustryAssociationRegistration> findAllByIsActiveTrue();

    /**
     * Check if registration exists by industry association name.
     *
     * @param industryAssociationName the industry association name
     * @return true if exists, false otherwise
     */
    boolean existsByIndustryAssociationNameAndStateAndIsActiveTrue(String industryAssociationName, String state);


    List<IndustryAssociationRegistration> findAllByIsActiveTrueAndCurrentStageId(long stageId);
}
