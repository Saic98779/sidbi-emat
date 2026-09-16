package org.emat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SustainabilityMatrixRequest {
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long appraisalId;

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

    private Long stageId;

    private String stageComments;

    private List<String> actionPlans;
    private String actionPlanClusterExpertComment;
}
