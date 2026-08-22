package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.User;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationBseRecommendationRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndustryAssociationBseRecommendationValidator {

    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Industry Association Registration not found with ID: ";
    private static final String BSE_RECOMMENDATION_NOT_FOUND_MESSAGE = "BSE Recommendation not found with ID: ";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with ID: ";

    private final IndustryAssociationBseRecommendationRepository bseRecommendationRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    public IndustryAssociationRegistration getRegistrationOrThrow(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + registrationId));
    }

    public IndustryAssociationBseRecommendation getActiveRecommendationOrThrow(Long id) {
        return bseRecommendationRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException(BSE_RECOMMENDATION_NOT_FOUND_MESSAGE + id));
    }

    public User getUserOrNull(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE + userId));
    }
}

