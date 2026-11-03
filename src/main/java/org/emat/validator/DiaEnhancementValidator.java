package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DiaEnhancement;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DiaEnhancementRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaEnhancementValidator {

    private static final String NOT_FOUND_MESSAGE = "DIA Enhancement not found with ID: ";

    private final DiaEnhancementRepository repository;

    public DiaEnhancement getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}
