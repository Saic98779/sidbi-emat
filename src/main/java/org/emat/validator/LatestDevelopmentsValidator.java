package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.LatestDevelopments;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.LatestDevelopmentsRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LatestDevelopmentsValidator {

    private static final String NOT_FOUND_MESSAGE = "Latest Developments not found with ID: ";

    private final LatestDevelopmentsRepository repository;

    public LatestDevelopments getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}