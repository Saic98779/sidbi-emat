package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DisbursementNoteCapacityBuildingIaOfficials;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DisbursementNoteCapacityBuildingIaOfficialsRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisbursementNoteCapacityBuildingIaOfficialsValidator {

    private static final String NOT_FOUND_MESSAGE =
            "Disbursement Note Capacity Building IA Officials not found with ID: ";
    private static final String NOT_FOUND_FOR_REGISTRATION_MESSAGE =
            "Disbursement Note Capacity Building IA Officials not found for registration ID: ";
    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration not found with ID: ";

    private final DisbursementNoteCapacityBuildingIaOfficialsRepository repository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    public IndustryAssociationRegistration getRegistrationOrThrow(Long registrationId) {
        return registrationRepository
                .findById(registrationId)
                .orElseThrow(
                        () -> new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + registrationId));
    }

    public DisbursementNoteCapacityBuildingIaOfficials getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }

    public DisbursementNoteCapacityBuildingIaOfficials getByRegistrationIdOrThrow(
            Long registrationId) {
        return repository
                .findByRegistrationId(registrationId)
                .stream()
                .findFirst()
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        NOT_FOUND_FOR_REGISTRATION_MESSAGE + registrationId));
    }
}