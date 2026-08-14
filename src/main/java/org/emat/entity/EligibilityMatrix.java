package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "ELIGIBILITY_MATRIX")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EligibilityMatrix extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", nullable = false, updatable = false)
    private UUID uuid;

    /**
     * Approved Industry Association
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTRATION_UUID", nullable = false)
    private IndustryAssociationRegistration registration;


    // 1. Number of Active Members (IA's) - Ideal size: 200
    @Column(name = "ACTIVE_MEMBERS_200")
    private Boolean activeMembers200;

    // 2. Number of Active MSMEs within membership
    @Column(name = "ACTIVE_MSMES")
    private Boolean activeMsmes;

    // 3. Is membership fees below 30%
    @Column(name = "MEMBERSHIP_FEES_BELOW_30")
    private Boolean membershipFeesBelow30;

    // 4. Details of business within the identified cluster
    @Column(name = "BUSINESS_WITHIN_CLUSTER")
    private Boolean businessWithinCluster;

    // 5. 10% of IA's belong to handicraft/artisanal sector
    @Column(name = "HANDICRAFT_ARTISANAL_10_PERCENT")
    private Boolean handicraftArtisanal10Percent;

    // 6. Socio-economic and developmental impact envisaged
    @Column(name = "SOCIO_ECONOMIC_DEVELOPMENTAL_IMPACT")
    private Boolean socioEconomicDevelopmentalImpact;

    // 7. Associations are small, sector focussed and have sustainable growth potential
    @Column(name = "SMALL_SECTOR_FOCUSSED_SUSTAINABLE_GROWTH")
    private Boolean smallSectorFocussedSustainableGrowth;

    // 8. Organisation requires institutional support
    @Column(name = "REQUIRES_INSTITUTIONAL_SUPPORT")
    private Boolean requiresInstitutionalSupport;

    // 9. Adequate physical and organisational infrastructure
    @Column(name = "ADEQUATE_INFRASTRUCTURE")
    private Boolean adequateInfrastructure;

    // 10. Conduct exhibitions, trade fairs and machinery fairs in India
    @Column(name = "CONDUCTS_FAIRS_IN_INDIA")
    private Boolean conductsFairsInIndia;

    // 11. Partner with Govt institutions for EDP training and skill development
    @Column(name = "PARTNERS_GOVT_EDP_SKILL_DEVELOPMENT")
    private Boolean partnersGovtEdpSkillDevelopment;

    // 12. Paid staff available and verifiable via public domain
    @Column(name = "PAID_STAFF_AVAILABLE")
    private Boolean paidStaffAvailable;

    // 13. Conduct International trade fairs
    @Column(name = "CONDUCTS_INTERNATIONAL_TRADE_FAIRS")
    private Boolean conductsInternationalTradeFairs;

    // 14. Earn rentals from infrastructure owned
    @Column(name = "EARNS_RENTALS_FROM_INFRASTRUCTURE")
    private Boolean earnsRentalsFromInfrastructure;

    // 15. Confirmation of meeting IA office bearers in appraisal
    @Column(name = "IA_OFFICE_BEARERS_MEETING_CONFIRMED")
    private Boolean iaOfficeBearersMeetingConfirmed;

    // 16. Volume of applications that can be generated - Ideal 250 Cr
    @Column(name = "APPLICATION_VOLUME_250_CR")
    private Boolean applicationVolume250Cr;

    // 17. Provide credit facilities to member MSMEs
    @Column(name = "PROVIDES_CREDIT_FACILITIES")
    private Boolean providesCreditFacilities;

    // 18. MSMEs utilise State/Central Govt schemes via PPP mode
    @Column(name = "UTILISES_GOVT_SCHEMES_PPP")
    private Boolean utilisesGovtSchemesPpp;

    // 19. Support rationalisation of GST, capital goods and import duty
    @Column(name = "SUPPORTS_GST_CAPITAL_GOODS_DUTY")
    private Boolean supportsGstCapitalGoodsDuty;

    // 20. Support convergence with Govt and financial institutions
    @Column(name = "SUPPORTS_GOVT_FINANCIAL_CONVERGENCE")
    private Boolean supportsGovtFinancialConvergence;

    // 21. Directory of members advertised via magazines/bulletins
    @Column(name = "MEMBER_DIRECTORY_ADVERTISED")
    private Boolean memberDirectoryAdvertised;

    // 22. Help individuals register under GI Act and file litigation etc.
    @Column(name = "SUPPORTS_GI_ACT")
    private Boolean supportsGiAct;

    @Column(name = "TOTAL_SCORE")
    private Integer totalScore;
}