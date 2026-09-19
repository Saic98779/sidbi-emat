package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.BulkBroadcast;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.BulkBroadcastRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BulkBroadcastValidator {

    private static final String NOT_FOUND_MESSAGE = "Bulk Broadcast not found with ID: ";

    private final BulkBroadcastRepository repository;

    public BulkBroadcast getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}