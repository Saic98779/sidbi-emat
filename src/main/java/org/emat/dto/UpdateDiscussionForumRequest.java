package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for updating an existing Discussion Forum. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDiscussionForumRequest {

    private String topic;
    private String theme;
    private String relevanceOfTopic;
    private LocalDate startDate;
    private LocalDate endDate;
    private String globalOrOnlyMembers;
}