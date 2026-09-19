package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BdsServiceProvidersOnboardingResponse;
import org.emat.dto.CreateBdsServiceProvidersOnboardingRequest;
import org.emat.dto.UpdateBdsServiceProvidersOnboardingRequest;
import org.emat.entity.BdsServiceProvidersOnboarding;
import org.emat.mapper.BdsServiceProvidersOnboardingMapper;
import org.emat.repository.BdsServiceProvidersOnboardingRepository;
import org.emat.service.BdsServiceProvidersOnboardingService;
import org.emat.validator.BdsServiceProvidersOnboardingValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BdsServiceProvidersOnboardingServiceImpl
        implements BdsServiceProvidersOnboardingService {

    private final BdsServiceProvidersOnboardingRepository repository;
    private final BdsServiceProvidersOnboardingMapper mapper;
    private final BdsServiceProvidersOnboardingValidator validator;

    @Override
    public BdsServiceProvidersOnboardingResponse create(
            CreateBdsServiceProvidersOnboardingRequest request) {
        log.info("Creating BDS Service Providers Onboarding");
        BdsServiceProvidersOnboarding onboarding = mapper.toEntity(request);
        return mapper.toResponse(repository.save(onboarding));
    }

    @Override
    @Transactional(readOnly = true)
    public BdsServiceProvidersOnboardingResponse getById(Long id) {
        log.debug("Fetching BDS Service Providers Onboarding with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BdsServiceProvidersOnboardingResponse> getAll() {
        log.debug("Fetching all active BDS Service Providers Onboarding");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public BdsServiceProvidersOnboardingResponse update(
            Long id, UpdateBdsServiceProvidersOnboardingRequest request) {
        log.info("Updating BDS Service Providers Onboarding with ID: {}", id);
        BdsServiceProvidersOnboarding onboarding = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(onboarding, request);
        return mapper.toResponse(repository.save(onboarding));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting BDS Service Providers Onboarding with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}