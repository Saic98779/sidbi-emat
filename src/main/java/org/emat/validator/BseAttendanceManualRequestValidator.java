package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.BseAttendanceManualRequest;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.repository.BseAttendanceManualRequestRepository;
import org.emat.repository.IndustryAssociationBseRecommendationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BseAttendanceManualRequestValidator {

    private static final String MANUAL_REQUEST_NOT_FOUND = "Manual request not found";
    private static final String MANUAL_REQUEST_NOT_FOUND_TITLE_CASE = "Manual Request not found";
    private static final String BSE_RECOMMENDATION_NOT_FOUND = "BSE Recommendation not found";
    private static final String REQUEST_ALREADY_APPROVED = "Request is already approved";

    private final BseAttendanceManualRequestRepository repository;
    private final IndustryAssociationBseRecommendationRepository recommendationRepository;

    public BseAttendanceManualRequest getManualRequestOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MANUAL_REQUEST_NOT_FOUND));
    }

    public BseAttendanceManualRequest getManualRequestOrThrowTitleCase(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MANUAL_REQUEST_NOT_FOUND_TITLE_CASE));
    }

    public IndustryAssociationBseRecommendation getRecommendationOrThrow(Long recommendationId) {
        return recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new RuntimeException(BSE_RECOMMENDATION_NOT_FOUND));
    }

    public void validateNotApproved(BseAttendanceManualRequest request) {
        if (Boolean.TRUE.equals(request.getIsApproved())) {
            throw new RuntimeException(REQUEST_ALREADY_APPROVED);
        }
    }
}

