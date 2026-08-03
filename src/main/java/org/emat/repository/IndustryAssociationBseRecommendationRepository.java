package org.emat.repository;

import org.emat.entity.IndustryAssociationBseRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndustryAssociationBseRecommendationRepository extends JpaRepository<IndustryAssociationBseRecommendation, UUID> {

    /**
     * Find all active BSE recommendations
     */
    List<IndustryAssociationBseRecommendation> findByIsActiveTrue();

    /**
     * Find BSE recommendation by UUID and active status
     */
    Optional<IndustryAssociationBseRecommendation> findByUuidAndIsActiveTrue(UUID uuid);

    /**
     * Find all BSE recommendations for a specific registration
     */
    @Query("SELECT b FROM IndustryAssociationBseRecommendation b WHERE b.registration.uuid = :registrationUuid AND b.isActive = true")
    List<IndustryAssociationBseRecommendation> findByRegistrationUuid(@Param("registrationUuid") UUID registrationUuid);

    /**
     * Find BSE recommendations by BSE name
     */
    List<IndustryAssociationBseRecommendation> findByBseNameContainingIgnoreCaseAndIsActiveTrue(String bseName);

    /**
     * Find BSE recommendations by email
     */
    Optional<IndustryAssociationBseRecommendation> findByEmailIdAndIsActiveTrue(String emailId);

    /**
     * Find BSE recommendations by mobile number
     */
    Optional<IndustryAssociationBseRecommendation> findByMobileNumberAndIsActiveTrue(String mobileNumber);

    /**
     * Find BSE recommendations by GT recommendation status
     */
    List<IndustryAssociationBseRecommendation> findByGtRecommendationAndIsActiveTrue(String gtRecommendation);

    /**
     * Find BSE recommendations by GT recommendation set
     */
    List<IndustryAssociationBseRecommendation> findByGtRecommendationIsNotNullAndIsActiveTrue();

    List<IndustryAssociationBseRecommendation> findByGtRecommendationIsNullAndIsActiveTrue();

    /**
     * Find BSE recommendations by PMU recommendation status
     */
    List<IndustryAssociationBseRecommendation> findByPmuRecommendationAndIsActiveTrue(String pmuRecommendation);

    /**
     * Find BSE recommendations by PMU recommendation set
     */
    List<IndustryAssociationBseRecommendation> findByPmuRecommendationIsNotNullAndIsActiveTrue();

    List<IndustryAssociationBseRecommendation> findByPmuRecommendationIsNullAndIsActiveTrue();

    /**
     * Find BSE recommendations by HO recommendation status
     */
    List<IndustryAssociationBseRecommendation> findByHoRecommendationAndIsActiveTrue(String hoRecommendation);

    /**
     * Find BSE recommendations by committee recommendation status
     */
    List<IndustryAssociationBseRecommendation> findByCommitteeRecommendationAndIsActiveTrue(String committeeRecommendation);

    /**
     * Find mapped BSE recommendations
     */
    List<IndustryAssociationBseRecommendation> findByIaMappedAndIsActiveTrue(Boolean iaMapped);

    /**
     * Find BSE recommendations with HO recommendation set
     */
    List<IndustryAssociationBseRecommendation> findByHoRecommendationIsNotNullAndIsActiveTrue();

    List<IndustryAssociationBseRecommendation> findByHoRecommendationIsNullAndIsActiveTrue();
}
