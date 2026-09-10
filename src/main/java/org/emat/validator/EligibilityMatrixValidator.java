package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.EligibilityMatrix;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.repository.EligibilityMatrixRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EligibilityMatrixValidator {

    private static final String ELIGIBILITY_NOT_FOUND_MESSAGE = "Eligibility Matrix not found: ";
    private static final String ELIGIBILITY_ALREADY_EXISTS_MESSAGE = "Eligibility Matrix already exists for Registration ID: ";
    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Industry Association Registration not found: ";
    private static final String ELIGIBILITY_NOT_FOUND_FOR_REGISTRATION_MESSAGE = "Eligibility Matrix not found for registration: ";

    private final EligibilityMatrixRepository eligibilityMatrixRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    public void validateCreateAllowed(Long registrationId) {
        if (eligibilityMatrixRepository.existsByRegistrationId(registrationId)) {
            throw new IllegalStateException(ELIGIBILITY_ALREADY_EXISTS_MESSAGE + registrationId);
        }
    }

    public IndustryAssociationRegistration getRegistrationOrThrow(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException(REGISTRATION_NOT_FOUND_MESSAGE + registrationId));
    }

    public EligibilityMatrix getEligibilityByIdOrThrow(Long id) {
        return eligibilityMatrixRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ELIGIBILITY_NOT_FOUND_MESSAGE + id));
    }

    public EligibilityMatrix getEligibilityByRegistrationIdOrThrow(Long registrationId) {
        return eligibilityMatrixRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new RuntimeException(ELIGIBILITY_NOT_FOUND_FOR_REGISTRATION_MESSAGE + registrationId));
    }

    public void validateExistsById(Long id) {
        if (!eligibilityMatrixRepository.existsById(id)) {
            throw new IllegalStateException(ELIGIBILITY_NOT_FOUND_MESSAGE + id);
        }
    }
}

