package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DiaCreditApplication;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DiaCreditApplicationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaCreditApplicationValidator {

    private static final String NOT_FOUND_MESSAGE = "DIA Credit Application not found with ID: ";

    private final DiaCreditApplicationRepository repository;

    public DiaCreditApplication getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}
