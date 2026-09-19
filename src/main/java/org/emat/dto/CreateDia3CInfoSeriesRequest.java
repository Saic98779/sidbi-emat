package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for creating a new DIA 3C-Info-Series. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDia3CInfoSeriesRequest {

    private String topic;
    private String relevanceOfTopic;
    private String briefOfContent;
    private String chapterNo;
    private String subjectLine;
    private String mainContent;
    private String attachment;
    private String bulkMessaging;
    private LocalDate proposedPublishDate;
    private String requestedBy;
    private LocalDate requestDate;
}