package org.emat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configurable role lists for endpoint authorization.
 * Update these values in application.properties to change access rules without code changes.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.roles")
public class RoleAccessProperties {

    private List<String> usersRead = List.of("SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "SIDBI_RO");
    private List<String> sidbiSde = List.of("SIDBI_SDE", "SIDBI_RO", "SIDBI_HO_MAKER", "SIDBI_HO_CHECKER");
    private List<String> bseRecommendationWrite = List.of("BSE", "GT_FIELD_TEAM", "GT_PMU", "MANPOWER_AGENCY", "SIDBI_HO_MAKER", "SIDBI_RO");
    private List<String> bseRecommendationRead = List.of("BSE", "GT_FIELD_TEAM", "GT_PMU", "MANPOWER_AGENCY", "SIDBI_SDE", "SIDBI_RO", "SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "CLUSTER_EXPERT");
    private List<String> bseRecommendationHo = List.of("SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "SIDBI_RO", "GT_PMU");
    private List<String> industryAssociationWrite = List.of("GT_FIELD_TEAM", "BSE", "MANPOWER_AGENCY", "SIDBI_HO_MAKER", "SIDBI_RO", "SIDBI_SDE", "SIDBI_HO_CHECKER");
    private List<String> industryAssociationRead = List.of("GT_FIELD_TEAM", "GT_PMU", "BSE", "MANPOWER_AGENCY", "SIDBI_SDE", "SIDBI_RO", "SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "CLUSTER_EXPERT");
}

