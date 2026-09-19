package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.Survey;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.SurveyRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurveyValidator {

    private static final String NOT_FOUND = "Survey not found with ID: ";

    private final SurveyRepository repository;

    public Survey getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
    }
}
