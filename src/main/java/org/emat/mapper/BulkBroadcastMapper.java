package org.emat.mapper;

import org.emat.dto.BulkBroadcastResponse;
import org.emat.dto.CreateBulkBroadcastRequest;
import org.emat.dto.UpdateBulkBroadcastRequest;
import org.emat.entity.BulkBroadcast;
import org.springframework.stereotype.Component;

@Component
public class BulkBroadcastMapper {

    public BulkBroadcast toEntity(CreateBulkBroadcastRequest request) {
        return BulkBroadcast.builder()
                .topic(request.getTopic())
                .relevanceOfTopic(request.getRelevanceOfTopic())
                .sampleForBroadcast(request.getSampleForBroadcast())
                .subjectLine(request.getSubjectLine())
                .dateOfBroadcast(request.getDateOfBroadcast())
                .mainContent(request.getMainContent())
                .broadcastThrough(request.getBroadcastThrough())
                .attachment(request.getAttachment())
                .link(request.getLink())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(BulkBroadcast broadcast, UpdateBulkBroadcastRequest request) {
        if (request.getTopic() != null) broadcast.setTopic(request.getTopic());
        if (request.getRelevanceOfTopic() != null)
            broadcast.setRelevanceOfTopic(request.getRelevanceOfTopic());
        if (request.getSampleForBroadcast() != null)
            broadcast.setSampleForBroadcast(request.getSampleForBroadcast());
        if (request.getSubjectLine() != null) broadcast.setSubjectLine(request.getSubjectLine());
        if (request.getDateOfBroadcast() != null)
            broadcast.setDateOfBroadcast(request.getDateOfBroadcast());
        if (request.getMainContent() != null) broadcast.setMainContent(request.getMainContent());
        if (request.getBroadcastThrough() != null)
            broadcast.setBroadcastThrough(request.getBroadcastThrough());
        if (request.getAttachment() != null) broadcast.setAttachment(request.getAttachment());
        if (request.getLink() != null) broadcast.setLink(request.getLink());
    }

    public BulkBroadcastResponse toResponse(BulkBroadcast broadcast) {
        return BulkBroadcastResponse.builder()
                .id(broadcast.getId())
                .topic(broadcast.getTopic())
                .relevanceOfTopic(broadcast.getRelevanceOfTopic())
                .sampleForBroadcast(broadcast.getSampleForBroadcast())
                .subjectLine(broadcast.getSubjectLine())
                .dateOfBroadcast(broadcast.getDateOfBroadcast())
                .mainContent(broadcast.getMainContent())
                .broadcastThrough(broadcast.getBroadcastThrough())
                .attachment(broadcast.getAttachment())
                .link(broadcast.getLink())
                .createdAt(broadcast.getCreatedAt())
                .updatedAt(broadcast.getUpdatedAt())
                .createdBy(broadcast.getCreatedBy())
                .updatedBy(broadcast.getUpdatedBy())
                .isActive(broadcast.getIsActive())
                .build();
    }
}