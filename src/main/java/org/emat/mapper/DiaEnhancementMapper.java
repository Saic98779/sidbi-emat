package org.emat.mapper;

import org.emat.dto.CreateDiaEnhancementRequest;
import org.emat.dto.DiaEnhancementResponse;
import org.emat.dto.UpdateDiaEnhancementRequest;
import org.emat.entity.DiaEnhancement;
import org.springframework.stereotype.Component;

@Component
public class DiaEnhancementMapper {

    public DiaEnhancement toEntity(CreateDiaEnhancementRequest request) {
        return DiaEnhancement.builder()
                .platform(request.getPlatform())
                .enhancementType(request.getEnhancementType())
                .enhancementDetails(request.getEnhancementDetails())
                .initiationDate(request.getInitiationDate())
                .completionDate(request.getCompletionDate())
                .status(request.getStatus())
                .pocSidbi(request.getPocSidbi())
                .pocGt(request.getPocGt())
                .sidbiPocId(request.getSidbiPocId())
                .gtPocId(request.getGtPocId())
                .enhancementSupportFilePath(request.getEnhancementSupportFilePath())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            DiaEnhancement enhancement, UpdateDiaEnhancementRequest request) {
        if (request.getPlatform() != null) enhancement.setPlatform(request.getPlatform());
        if (request.getEnhancementType() != null)
            enhancement.setEnhancementType(request.getEnhancementType());
        if (request.getEnhancementDetails() != null)
            enhancement.setEnhancementDetails(request.getEnhancementDetails());
        if (request.getInitiationDate() != null)
            enhancement.setInitiationDate(request.getInitiationDate());
        if (request.getCompletionDate() != null)
            enhancement.setCompletionDate(request.getCompletionDate());
        if (request.getStatus() != null) enhancement.setStatus(request.getStatus());
        if (request.getPocSidbi() != null) enhancement.setPocSidbi(request.getPocSidbi());
        if (request.getPocGt() != null) enhancement.setPocGt(request.getPocGt());
        if (request.getSidbiPocId() != null) enhancement.setSidbiPocId(request.getSidbiPocId());
        if (request.getGtPocId() != null) enhancement.setGtPocId(request.getGtPocId());
        if (request.getEnhancementSupportFilePath() != null)
            enhancement.setEnhancementSupportFilePath(request.getEnhancementSupportFilePath());
    }

    public DiaEnhancementResponse toResponse(DiaEnhancement enhancement) {
        return DiaEnhancementResponse.builder()
                .id(enhancement.getId())
                .platform(enhancement.getPlatform())
                .enhancementType(enhancement.getEnhancementType())
                .enhancementDetails(enhancement.getEnhancementDetails())
                .initiationDate(enhancement.getInitiationDate())
                .completionDate(enhancement.getCompletionDate())
                .status(enhancement.getStatus())
                .pocSidbi(enhancement.getPocSidbi())
                .pocGt(enhancement.getPocGt())
                .sidbiPocId(enhancement.getSidbiPocId())
                .gtPocId(enhancement.getGtPocId())
                .enhancementSupportFilePath(enhancement.getEnhancementSupportFilePath())
                .createdAt(enhancement.getCreatedAt())
                .updatedAt(enhancement.getUpdatedAt())
                .createdBy(enhancement.getCreatedBy())
                .updatedBy(enhancement.getUpdatedBy())
                .isActive(enhancement.getIsActive())
                .build();
    }
}
