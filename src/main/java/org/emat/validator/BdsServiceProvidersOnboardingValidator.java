package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.BdsServiceProvidersOnboarding;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.BdsServiceProvidersOnboardingRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BdsServiceProvidersOnboardingValidator {

    private static final String NOT_FOUND_MESSAGE =
            "BDS Service Providers Onboarding not found with ID: ";

    private final BdsServiceProvidersOnboardingRepository repository;

    public BdsServiceProvidersOnboarding getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}