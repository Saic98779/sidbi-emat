package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "SUSTAINABILITY_MATRIX")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SustainabilityMatrix extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUSTAINABILITY_MATRIX")
    @SequenceGenerator(name = "SEQ_SUSTAINABILITY_MATRIX", sequenceName = "SEQ_SUSTAINABILITY_MATRIX", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPRAISAL_ID", nullable = false)
    private IndustryAssociationAppraisal industryAssociationAppraisal;

    // 1. Active governing body
    @Column(name = "ACTIVE_GOVERNING_BODY")
    private Boolean activeGoverningBody;

    // 2. Election
    @Column(name = "ELECTION")
    private Boolean election;

    // 3. Committees
    @Column(name = "COMMITTEES")
    private Boolean committees;

    // 4. Documented policies
    @Column(name = "DOCUMENTED_POLICIES")
    private Boolean documentedPolicies;

    // 5. Attendance
    @Column(name = "ATTENDANCE")
    private Boolean attendance;

    // 6. AGM
    @Column(name = "AGM")
    private Boolean agm;

    // 7. Number of Active Paying Members
    @Column(name = "ACTIVE_PAYING_MEMBERS")
    private Boolean activePayingMembers;

    // 8. Retention Rate
    @Column(name = "RETENTION_RATE")
    private Boolean retentionRate;

    // 9. Own-Source revenue and reserves
    @Column(name = "OWN_SOURCE_REVENUE_RESERVES")
    private Boolean ownSourceRevenueReserves;

    // 10. Annual revenue Threshold
    @Column(name = "ANNUAL_REVENUE_THRESHOLD")
    private Boolean annualRevenueThreshold;

    // 11. Program/Service offered
    @Column(name = "PROGRAM_SERVICE_OFFERED")
    private Boolean programServiceOffered;

    // 12. Website
    @Column(name = "WEBSITE")
    private Boolean website;

    // 13. CRM
    @Column(name = "CRM")
    private Boolean crm;

    // 14. Digital Member Database
    @Column(name = "DIGITAL_MEMBER_DATABASE")
    private Boolean digitalMemberDatabase;

    // 15. Social Media
    @Column(name = "SOCIAL_MEDIA")
    private Boolean socialMedia;

    // 16. Government
    @Column(name = "GOVERNMENT")
    private Boolean government;

    // 17. Banks
    @Column(name = "BANKS")
    private Boolean banks;

    // 18. SIDBI
    @Column(name = "SIDBI")
    private Boolean sidbi;

    // 19. Academia
    @Column(name = "ACADEMIA")
    private Boolean academia;

    // 20. Corporates
    @Column(name = "CORPORATES")
    private Boolean corporates;

    // 21. Dedicated Staff
    @Column(name = "DEDICATED_STAFF")
    private Boolean dedicatedStaff;

    // 22. Operational Processes
    @Column(name = "OPERATIONAL_PROCESSES")
    private Boolean operationalProcesses;

    @Column(name = "TOTAL_SCORE")
    private Integer totalScore;
}