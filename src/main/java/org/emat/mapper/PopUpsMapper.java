package org.emat.mapper;

import org.emat.dto.CreatePopUpsRequest;
import org.emat.dto.PopUpsResponse;
import org.emat.dto.UpdatePopUpsRequest;
import org.emat.entity.PopUps;
import org.springframework.stereotype.Component;

@Component
public class PopUpsMapper {

    public PopUps toEntity(CreatePopUpsRequest request) {
        return PopUps.builder()
                .topic(request.getTopic())
                .relevanceOfPopUp(request.getRelevanceOfPopUp())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .attachments(request.getAttachments())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(PopUps popUps, UpdatePopUpsRequest request) {
        if (request.getTopic() != null) popUps.setTopic(request.getTopic());
        if (request.getRelevanceOfPopUp() != null)
            popUps.setRelevanceOfPopUp(request.getRelevanceOfPopUp());
        if (request.getStartDate() != null) popUps.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) popUps.setEndDate(request.getEndDate());
        if (request.getAttachments() != null) popUps.setAttachments(request.getAttachments());
    }

    public PopUpsResponse toResponse(PopUps popUps) {
        return PopUpsResponse.builder()
                .id(popUps.getId())
                .topic(popUps.getTopic())
                .relevanceOfPopUp(popUps.getRelevanceOfPopUp())
                .startDate(popUps.getStartDate())
                .endDate(popUps.getEndDate())
                .attachments(popUps.getAttachments())
                .status(popUps.getStatus())
                .remark(popUps.getRemark())
                .approvedDate(popUps.getApprovedDate())
                .createdAt(popUps.getCreatedAt())
                .updatedAt(popUps.getUpdatedAt())
                .createdBy(popUps.getCreatedBy())
                .updatedBy(popUps.getUpdatedBy())
                .isActive(popUps.getIsActive())
                .build();
    }
}