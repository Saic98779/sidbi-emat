package org.emat.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.BulkMessaging;

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
    private List<BulkMessaging> bulkMessaging;
    private LocalDate proposedPublishDate;
}