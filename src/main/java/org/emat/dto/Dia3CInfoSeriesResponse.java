package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.enums.BulkMessaging;
import org.emat.enums.Status;

/** DTO for DIA 3C-Info-Series response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dia3CInfoSeriesResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String topic;
    private String relevanceOfTopic;
    private String briefOfContent;
    private String chapterNo;
    private String subjectLine;
    private String mainContent;
    private String attachment;
    private List<BulkMessaging> bulkMessaging;
    private LocalDate proposedPublishDate;

    private Status makerStatus;
    private Status checkerStatus;
    private String remark;
    private LocalDate approvedDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}