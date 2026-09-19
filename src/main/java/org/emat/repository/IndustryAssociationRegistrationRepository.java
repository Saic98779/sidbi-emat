package org.emat.repository;

import java.util.List;
import org.emat.dto.RegistrationDropdownDto;
import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for IndustryAssociationRegistration entity. Provides database access operations for
 * Industry Association Registration records.
 */
@Repository
public interface IndustryAssociationRegistrationRepository
        extends JpaRepository<IndustryAssociationRegistration, Long> {

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
    boolean existsByIndustryAssociationNameAndStateAndIsActiveTrue(
            String industryAssociationName, String state);

    List<IndustryAssociationRegistration> findAllByIsActiveTrueAndCurrentStageId(long stageId);

    /**
     * Find active registrations for the dropdown, optionally filtered by current stage, state,
     * created-by user and district. All criteria are optional and combined with AND.
     *
     * @param stageId current stage identifier filter
     * @param state state filter
     * @param createdBy created-by user filter
     * @param district district filter (typically used along with state)
     * @return list of registration dropdown entries
     */
    @Query(
            """
            SELECT new org.emat.dto.RegistrationDropdownDto(
                r.id,
                r.industryAssociationName
            )
            FROM IndustryAssociationRegistration r
            WHERE r.isActive = true
              AND (:stageId IS NULL OR r.currentStage.id = :stageId)
              AND (:state IS NULL OR LOWER(r.state) = LOWER(:state))
              AND (:createdBy IS NULL OR LOWER(r.createdBy) = LOWER(:createdBy))
              AND (:district IS NULL OR LOWER(r.district) = LOWER(:district))
            ORDER BY r.industryAssociationName
            """)
    List<RegistrationDropdownDto> findDropdown(
            @Param("stageId") Long stageId,
            @Param("state") String state,
            @Param("createdBy") String createdBy,
            @Param("district") String district);
}
