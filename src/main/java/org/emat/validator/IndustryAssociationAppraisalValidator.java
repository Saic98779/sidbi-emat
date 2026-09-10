package org.emat.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.entity.IndustryAssociationAppraisal;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.User;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationAppraisalRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class IndustryAssociationAppraisalValidator {

    private static final String APPRAISAL_NOT_FOUND_MESSAGE = "Appraisal not found with ID: ";
    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration not found with ID: ";
    private static final String USER_NOT_FOUND_WITH_USERNAME_MESSAGE = "User not found with username: ";
    private static final String APPRAISAL_ALREADY_EXISTS_MESSAGE = "Appraisal already exists for this registration";

    private final IndustryAssociationAppraisalRepository appraisalRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    public IndustryAssociationRegistration getRegistrationOrThrow(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> {
                    log.error(REGISTRATION_NOT_FOUND_MESSAGE + registrationId);
                    return new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + registrationId);
                });
    }

    public void validateAppraisalNotExists(Long registrationId) {
        if (appraisalRepository.existsByRegistrationId(registrationId)) {
            log.warn("Appraisal already exists for registration ID: {}", registrationId);
            throw new IllegalArgumentException(APPRAISAL_ALREADY_EXISTS_MESSAGE);
        }
    }

    public IndustryAssociationAppraisal getAppraisalOrThrow(Long id) {
        return appraisalRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(APPRAISAL_NOT_FOUND_MESSAGE + id);
                    return new EntityNotFoundException(APPRAISAL_NOT_FOUND_MESSAGE + id);
                });
    }

    public IndustryAssociationAppraisal getAppraisalByRegistrationOrThrow(Long registrationId) {
        return appraisalRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> {
                    String message = "Appraisal not found for registration ID: " + registrationId;
                    log.error(message);
                    return new EntityNotFoundException(message);
                });
    }

    public User getUserByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new EntityNotFoundException(USER_NOT_FOUND_WITH_USERNAME_MESSAGE + username);
                });
    }
}

