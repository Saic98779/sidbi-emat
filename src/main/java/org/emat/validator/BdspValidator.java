package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.Bdsp;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.BdspRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BdspValidator {

    private static final String NOT_FOUND_MESSAGE = "BDSP not found with ID: ";

    private final BdspRepository repository;

    public Bdsp getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}