package org.emat.service;

import java.util.List;
import org.emat.dto.BseRecommendationResponse;
import org.emat.dto.CreateBseRecommendationRequest;
import org.emat.dto.UpdateBseRecommendationRequest;

public interface IndustryAssociationBseRecommendationService {

    BseRecommendationResponse createBseRecommendation(CreateBseRecommendationRequest request);

    List<BseRecommendationResponse> getAllBseRecommendations();

    BseRecommendationResponse getBseRecommendationById(Long id);

    List<BseRecommendationResponse> getBseRecommendationsByRegistration(Long registrationId);

    BseRecommendationResponse updateBseRecommendation(
            Long id, UpdateBseRecommendationRequest request);

    List<BseRecommendationResponse> searchByBseName(String bseName);

    List<BseRecommendationResponse> getByGtRecommendation(boolean isRecommended);

    List<BseRecommendationResponse> getByPmuRecommendation(boolean isRecommended);

    List<BseRecommendationResponse> getByHoRecommendation(boolean isRecommended);

    List<BseRecommendationResponse> getByMappedStatus(Boolean iaMapped);

    List<BseRecommendationResponse> getSelectedBseByVendor(Long userId);
}
