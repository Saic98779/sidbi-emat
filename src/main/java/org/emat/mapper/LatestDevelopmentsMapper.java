package org.emat.mapper;

import org.emat.dto.CreateLatestDevelopmentsRequest;
import org.emat.dto.LatestDevelopmentsResponse;
import org.emat.dto.UpdateLatestDevelopmentsRequest;
import org.emat.entity.LatestDevelopments;
import org.springframework.stereotype.Component;

@Component
public class LatestDevelopmentsMapper {

    public LatestDevelopments toEntity(CreateLatestDevelopmentsRequest request) {
        return LatestDevelopments.builder()
                .topic(request.getTopic())
                .relevanceOfTopic(request.getRelevanceOfTopic())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            LatestDevelopments developments, UpdateLatestDevelopmentsRequest request) {
        if (request.getTopic() != null) developments.setTopic(request.getTopic());
        if (request.getRelevanceOfTopic() != null)
            developments.setRelevanceOfTopic(request.getRelevanceOfTopic());
        if (request.getStartDate() != null) developments.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) developments.setEndDate(request.getEndDate());
    }

    public LatestDevelopmentsResponse toResponse(LatestDevelopments developments) {
        return LatestDevelopmentsResponse.builder()
                .id(developments.getId())
                .topic(developments.getTopic())
                .relevanceOfTopic(developments.getRelevanceOfTopic())
                .startDate(developments.getStartDate())
                .endDate(developments.getEndDate())
                .createdAt(developments.getCreatedAt())
                .updatedAt(developments.getUpdatedAt())
                .createdBy(developments.getCreatedBy())
                .updatedBy(developments.getUpdatedBy())
                .isActive(developments.getIsActive())
                .build();
    }
}