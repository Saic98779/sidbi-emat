package org.emat.mapper;

import org.emat.dto.CreateDiscussionForumRequest;
import org.emat.dto.DiscussionForumResponse;
import org.emat.dto.UpdateDiscussionForumRequest;
import org.emat.entity.DiscussionForum;
import org.springframework.stereotype.Component;

@Component
public class DiscussionForumMapper {

    public DiscussionForum toEntity(CreateDiscussionForumRequest request) {
        return DiscussionForum.builder()
                .topic(request.getTopic())
                .theme(request.getTheme())
                .relevanceOfTopic(request.getRelevanceOfTopic())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .globalOrOnlyMembers(request.getGlobalOrOnlyMembers())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(DiscussionForum forum, UpdateDiscussionForumRequest request) {
        if (request.getTopic() != null) forum.setTopic(request.getTopic());
        if (request.getTheme() != null) forum.setTheme(request.getTheme());
        if (request.getRelevanceOfTopic() != null)
            forum.setRelevanceOfTopic(request.getRelevanceOfTopic());
        if (request.getStartDate() != null) forum.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) forum.setEndDate(request.getEndDate());
        if (request.getGlobalOrOnlyMembers() != null)
            forum.setGlobalOrOnlyMembers(request.getGlobalOrOnlyMembers());
    }

    public DiscussionForumResponse toResponse(DiscussionForum forum) {
        return DiscussionForumResponse.builder()
                .id(forum.getId())
                .topic(forum.getTopic())
                .theme(forum.getTheme())
                .relevanceOfTopic(forum.getRelevanceOfTopic())
                .startDate(forum.getStartDate())
                .endDate(forum.getEndDate())
                .globalOrOnlyMembers(forum.getGlobalOrOnlyMembers())
                .status(forum.getStatus())
                .approvedDate(forum.getApprovedDate())
                .createdAt(forum.getCreatedAt())
                .updatedAt(forum.getUpdatedAt())
                .createdBy(forum.getCreatedBy())
                .updatedBy(forum.getUpdatedBy())
                .isActive(forum.getIsActive())
                .build();
    }
}