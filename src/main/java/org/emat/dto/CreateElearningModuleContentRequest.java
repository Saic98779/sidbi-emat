package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for creating a new E-learning Module Content. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateElearningModuleContentRequest {

    private String topic;
    private String moduleName;
    private String relevanceRationaleOfTopic;
    private String briefOfContent;
    private String mainContent;
    private String attachment;
    private String link;
    private String placementOfModule;
    private String requestedBy;
    private LocalDate requestDate;
}