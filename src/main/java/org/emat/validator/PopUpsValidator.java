package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.PopUps;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.PopUpsRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopUpsValidator {

    private static final String NOT_FOUND_MESSAGE = "Pop-Ups not found with ID: ";

    private final PopUpsRepository repository;

    public PopUps getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}