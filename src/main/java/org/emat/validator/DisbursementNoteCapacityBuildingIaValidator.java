package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DisbursementNoteCapacityBuildingIa;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DisbursementNoteCapacityBuildingIaRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisbursementNoteCapacityBuildingIaValidator {

    private static final String NOT_FOUND_MESSAGE =
            "Disbursement Note Capacity Building IA not found with ID: ";
    private static final String NOT_FOUND_FOR_REGISTRATION_MESSAGE =
            "Disbursement Note Capacity Building IA not found for registration ID: ";
    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration not found with ID: ";

    private final DisbursementNoteCapacityBuildingIaRepository repository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    public IndustryAssociationRegistration getRegistrationOrThrow(Long registrationId) {
        return registrationRepository
                .findById(registrationId)
                .orElseThrow(
                        () -> new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + registrationId));
    }

    public DisbursementNoteCapacityBuildingIa getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }

    public DisbursementNoteCapacityBuildingIa getByRegistrationIdOrThrow(Long registrationId) {
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