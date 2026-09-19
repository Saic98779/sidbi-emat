package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.ElearningModuleContent;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.ElearningModuleContentRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElearningModuleContentValidator {

    private static final String NOT_FOUND_MESSAGE = "E-learning Module Content not found with ID: ";

    private final ElearningModuleContentRepository repository;

    public ElearningModuleContent getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}