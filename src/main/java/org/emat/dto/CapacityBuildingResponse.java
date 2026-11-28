package org.emat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * One row of the Capacity Building-P sheet (Annexure XVI), built from PROMO_EVENT_DETAILS and
 * PROMO_WEBINAR_DTLS. Fields the portal does not capture are returned as null for manual entry.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapacityBuildingResponse {

    /** EVENT or WEBINAR. */
    private String source;

    private Long sourceId;

    // 1
    private Long sn;
    // 2 - derived from PED_STATE / PED_DISTRICT via REGIONAL_OFFICE
    private String nameOfRo;
    // 3 - not captured on the portal
    private String industryAssociationName;
    // 4 - not captured on the portal
    private String forIaMembersOrBses;
    // 5
    private String eventName;
    // 6
    private LocalDateTime eventDate;

    private LocalDateTime eventEndDate;
    // 7 - END - START
    private Long durationMinutes;

    private String duration;
    // 8 - Virtual for webinars; Hybrid/Physical derived from the event description
    private String nature;
    // 9 - Webinar for webinars; derived from the event name
    private String eventType;
    // 10 - not captured on the portal
    private String theme;
    // 11
    private String description;
    // 12
    private String partOfDia;
    // 13-15 - not captured on the portal
    private String costIntervention;
    private String iaContribution;
    private String costBreakup;
    // 16 - SPEAKERS_EXPERT, webinars only
    private String trainerOrBdsProvider;
    // 17
    private Long attendees;
    // 18-19 - not captured on the portal
    private String trainingReportPrepared;
    private String feedbackFormShared;
    // 20 - implied by the row existing on the portal
    private String uploadedOnDiaPortal;

    private String state;
    private String district;
}
