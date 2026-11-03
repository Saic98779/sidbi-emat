package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.GrantDisbursement;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.GrantDisbursementRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrantDisbursementValidator {

    private static final String NOT_FOUND_MESSAGE = "Grant Disbursement not found with ID: ";

    private final GrantDisbursementRepository repository;

    public GrantDisbursement getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}
