package org.emat.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateIndustryAssociationRegistrationRequest;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class IndustryAssociationRegistrationValidator {

    private static final String DUPLICATE_REGISTRATION_MESSAGE =
            "Registration already exists for this Industry Association in the state";

    private final IndustryAssociationRegistrationRepository repository;

    public void validateCreateRequest(CreateIndustryAssociationRegistrationRequest request) {
        if (repository.existsByIndustryAssociationNameAndStateAndIsActiveTrue(
                request.getIndustryAssociationName(), request.getState())) {
            log.warn("Duplicate registration attempt for: {} in state: {}",
                    request.getIndustryAssociationName(), request.getState());
            throw new IllegalArgumentException(DUPLICATE_REGISTRATION_MESSAGE);
        }
    }
}

