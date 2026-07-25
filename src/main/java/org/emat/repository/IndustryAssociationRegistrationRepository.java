package org.emat.repository;

import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for IndustryAssociationRegistration entity.
 * Provides database access operations for Industry Association Registration records.
 */
@Repository
public interface IndustryAssociationRegistrationRepository extends JpaRepository<IndustryAssociationRegistration, String> {

    /**
     * Find all active registrations.
     *
     * @return List of active IndustryAssociationRegistration records
     */
    List<IndustryAssociationRegistration> findAllByIsActiveTrue();

    /**
     * Find registration by UUID.
     *
     * @param uuid the unique identifier
     * @return Optional containing the registration if found
     */
    Optional<IndustryAssociationRegistration> findByUuid(UUID uuid);

    /**
     * Check if registration exists by industry association name.
     *
     * @param industryAssociationName the industry association name
     * @return true if exists, false otherwise
     */
    boolean existsByIndustryAssociationNameAndStateAndIsActiveTrue(String industryAssociationName, String state);
}
