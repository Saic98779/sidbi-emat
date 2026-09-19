package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.ActionPlan;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.ActionPlanRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionPlanValidator {

    private static final String NOT_FOUND = "Action Plan not found with ID: ";
    private static final String REGISTRATION_NOT_FOUND = "Registration not found with ID: ";

    private final ActionPlanRepository repository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    public ActionPlan getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
    }

    public IndustryAssociationRegistration getRegistrationOrThrow(Long registrationId) {
        return registrationRepository
                .findById(registrationId)
                .orElseThrow(
                        () -> new EntityNotFoundException(REGISTRATION_NOT_FOUND + registrationId));
    }
}
