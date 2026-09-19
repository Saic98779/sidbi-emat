package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for updating an existing Bulk Broadcast. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBulkBroadcastRequest {

    private String topic;
    private String relevanceOfTopic;
    private String sampleForBroadcast;
    private String subjectLine;
    private LocalDate dateOfBroadcast;
    private String mainContent;
    private String broadcastThrough;
    private String attachment;
    private String link;
    private String requestedBy;
    private LocalDate requestDate;
}