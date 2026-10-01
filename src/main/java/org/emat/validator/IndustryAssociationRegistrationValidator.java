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
    private static final String DUPLICATE_PAN_MESSAGE = "PAN number already exists";
    private static final String PAN_REQUIRED_MESSAGE = "PAN number is required";

    private final IndustryAssociationRegistrationRepository repository;

    public void validateCreateRequest(CreateIndustryAssociationRegistrationRequest request) {
        if (repository.existsByIndustryAssociationNameAndStateAndIsActiveTrue(
                request.getIndustryAssociationName(), request.getState())) {
            log.warn("Duplicate registration attempt for: {} in state: {}",
                    request.getIndustryAssociationName(), request.getState());
            throw new IllegalArgumentException(DUPLICATE_REGISTRATION_MESSAGE);
        }

        validatePanUniqueness(request.getPanNo());
    }

    public void validatePanUniqueness(String panNo) {
        if (panNo == null || panNo.isBlank()) {
            throw new IllegalArgumentException(PAN_REQUIRED_MESSAGE);
        }

        if (repository.existsByPanNoIgnoreCase(panNo.trim())) {
            log.warn("Duplicate PAN number attempt for PAN: {}", panNo);
            throw new IllegalArgumentException(DUPLICATE_PAN_MESSAGE);
        }
    }

    public boolean isPanDuplicate(String panNo) {
        if (panNo == null || panNo.isBlank()) {
            throw new IllegalArgumentException(PAN_REQUIRED_MESSAGE);
        }
        return repository.existsByPanNoIgnoreCase(panNo.trim());
    }
}
