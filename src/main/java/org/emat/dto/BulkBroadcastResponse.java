package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

/** DTO for Bulk Broadcast response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkBroadcastResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}