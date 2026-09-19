package org.emat.mapper;

import org.emat.dto.CreateElearningModuleContentRequest;
import org.emat.dto.ElearningModuleContentResponse;
import org.emat.dto.UpdateElearningModuleContentRequest;
import org.emat.entity.ElearningModuleContent;
import org.springframework.stereotype.Component;

@Component
public class ElearningModuleContentMapper {

    public ElearningModuleContent toEntity(CreateElearningModuleContentRequest request) {
        return ElearningModuleContent.builder()
                .topic(request.getTopic())
                .moduleName(request.getModuleName())
                .relevanceOfTopic(request.getRelevanceOfTopic())
                .briefOfContent(request.getBriefOfContent())
                .mainContent(request.getMainContent())
                .attachment(request.getAttachment())
                .link(request.getLink())
                .placementOfModule(request.getPlacementOfModule())
                .requestedBy(request.getRequestedBy())
                .requestDate(request.getRequestDate())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            ElearningModuleContent content, UpdateElearningModuleContentRequest request) {
        if (request.getTopic() != null) content.setTopic(request.getTopic());
        if (request.getModuleName() != null) content.setModuleName(request.getModuleName());
        if (request.getRelevanceOfTopic() != null)
            content.setRelevanceOfTopic(request.getRelevanceOfTopic());
        if (request.getBriefOfContent() != null)
            content.setBriefOfContent(request.getBriefOfContent());
        if (request.getMainContent() != null) content.setMainContent(request.getMainContent());
        if (request.getAttachment() != null) content.setAttachment(request.getAttachment());
        if (request.getLink() != null) content.setLink(request.getLink());
        if (request.getPlacementOfModule() != null)
            content.setPlacementOfModule(request.getPlacementOfModule());
        if (request.getRequestedBy() != null) content.setRequestedBy(request.getRequestedBy());
        if (request.getRequestDate() != null) content.setRequestDate(request.getRequestDate());
    }

    public ElearningModuleContentResponse toResponse(ElearningModuleContent content) {
        return ElearningModuleContentResponse.builder()
                .id(content.getId())
                .topic(content.getTopic())
                .moduleName(content.getModuleName())
                .relevanceOfTopic(content.getRelevanceOfTopic())
                .briefOfContent(content.getBriefOfContent())
                .mainContent(content.getMainContent())
                .attachment(content.getAttachment())
                .link(content.getLink())
                .placementOfModule(content.getPlacementOfModule())
                .requestedBy(content.getRequestedBy())
                .requestDate(content.getRequestDate())
                .createdAt(content.getCreatedAt())
                .updatedAt(content.getUpdatedAt())
                .createdBy(content.getCreatedBy())
                .updatedBy(content.getUpdatedBy())
                .isActive(content.getIsActive())
                .build();
    }
}