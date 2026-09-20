package org.emat.service;

import java.util.List;
import org.emat.dto.BdsServiceProvidersOnboardingResponse;
import org.emat.dto.CreateBdsServiceProvidersOnboardingRequest;
import org.emat.dto.UpdateBdsServiceProvidersOnboardingRequest;
import org.emat.dto.UpdateBdsServiceProvidersOnboardingStatusRequest;

public interface BdsServiceProvidersOnboardingService {

    BdsServiceProvidersOnboardingResponse create(
            CreateBdsServiceProvidersOnboardingRequest request);

    BdsServiceProvidersOnboardingResponse getById(Long id);

    List<BdsServiceProvidersOnboardingResponse> getAll();

    BdsServiceProvidersOnboardingResponse update(
            Long id, UpdateBdsServiceProvidersOnboardingRequest request);

    BdsServiceProvidersOnboardingResponse updateStatus(
            Long id, UpdateBdsServiceProvidersOnboardingStatusRequest request);

    void delete(Long id);
}