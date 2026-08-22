package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DisbursementCapex;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DisbursementCapexRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisbursementCapexValidator {

    private static final String NOT_FOUND = "Vendor Expenditure not found";
    private static final String REGISTRATION_NOT_FOUND = "Registration not found with ID: ";
    private static final String NOT_FOUND_FOR_REGISTRATION = "Vendor Expenditure not found for registration";

    private final DisbursementCapexRepository disbursementCapexRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    public IndustryAssociationRegistration getRegistrationOrThrow(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException(REGISTRATION_NOT_FOUND + registrationId));
    }

    public DisbursementCapex getByIdOrThrow(Long id) {
        return disbursementCapexRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND));
    }

    public DisbursementCapex getByRegistrationIdOrThrow(Long registrationId) {
        return disbursementCapexRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_FOR_REGISTRATION));
    }
}

