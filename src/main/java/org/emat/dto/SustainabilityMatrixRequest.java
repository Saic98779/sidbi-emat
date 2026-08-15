package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SustainabilityMatrixRequest {

    private UUID appraisalUuid;

    private Boolean activeGoverningBody;
    private Boolean election;
    private Boolean committees;
    private Boolean documentedPolicies;
    private Boolean attendance;
    private Boolean agm;
    private Boolean activePayingMembers;
    private Boolean retentionRate;
    private Boolean ownSourceRevenueReserves;
    private Boolean annualRevenueThreshold;
    private Boolean programServiceOffered;
    private Boolean website;
    private Boolean crm;
    private Boolean digitalMemberDatabase;
    private Boolean socialMedia;
    private Boolean government;
    private Boolean banks;
    private Boolean sidbi;
    private Boolean academia;
    private Boolean corporates;
    private Boolean dedicatedStaff;
    private Boolean operationalProcesses;
    private Integer totalScore;
}