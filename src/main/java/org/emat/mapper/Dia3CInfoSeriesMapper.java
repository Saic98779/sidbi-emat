package org.emat.mapper;

import org.emat.dto.CreateDia3CInfoSeriesRequest;
import org.emat.dto.Dia3CInfoSeriesResponse;
import org.emat.dto.UpdateDia3CInfoSeriesRequest;
import org.emat.entity.Dia3CInfoSeries;
import org.springframework.stereotype.Component;

@Component
public class Dia3CInfoSeriesMapper {

    public Dia3CInfoSeries toEntity(CreateDia3CInfoSeriesRequest request) {
        return Dia3CInfoSeries.builder()
                .topic(request.getTopic())
                .relevanceOfTopic(request.getRelevanceOfTopic())
                .briefOfContent(request.getBriefOfContent())
                .chapterNo(request.getChapterNo())
                .subjectLine(request.getSubjectLine())
                .mainContent(request.getMainContent())
                .attachment(request.getAttachment())
                .bulkMessaging(request.getBulkMessaging())
                .proposedPublishDate(request.getProposedPublishDate())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            Dia3CInfoSeries series, UpdateDia3CInfoSeriesRequest request) {
        if (request.getTopic() != null) series.setTopic(request.getTopic());
        if (request.getRelevanceOfTopic() != null)
            series.setRelevanceOfTopic(request.getRelevanceOfTopic());
        if (request.getBriefOfContent() != null)
            series.setBriefOfContent(request.getBriefOfContent());
        if (request.getChapterNo() != null) series.setChapterNo(request.getChapterNo());
        if (request.getSubjectLine() != null) series.setSubjectLine(request.getSubjectLine());
        if (request.getMainContent() != null) series.setMainContent(request.getMainContent());
        if (request.getAttachment() != null) series.setAttachment(request.getAttachment());
        if (request.getBulkMessaging() != null) series.setBulkMessaging(request.getBulkMessaging());
        if (request.getProposedPublishDate() != null)
            series.setProposedPublishDate(request.getProposedPublishDate());
    }

    public Dia3CInfoSeriesResponse toResponse(Dia3CInfoSeries series) {
        return Dia3CInfoSeriesResponse.builder()
                .id(series.getId())
                .topic(series.getTopic())
                .relevanceOfTopic(series.getRelevanceOfTopic())
                .briefOfContent(series.getBriefOfContent())
                .chapterNo(series.getChapterNo())
                .subjectLine(series.getSubjectLine())
                .mainContent(series.getMainContent())
                .attachment(series.getAttachment())
                .bulkMessaging(series.getBulkMessaging())
                .proposedPublishDate(series.getProposedPublishDate())
                .createdAt(series.getCreatedAt())
                .updatedAt(series.getUpdatedAt())
                .createdBy(series.getCreatedBy())
                .updatedBy(series.getUpdatedBy())
                .isActive(series.getIsActive())
                .build();
    }
}