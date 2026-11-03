package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DiaExposureVisit;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DiaExposureVisitRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaExposureVisitValidator {

    private static final String NOT_FOUND_MESSAGE = "DIA Exposure Visit not found with ID: ";

    private final DiaExposureVisitRepository repository;

    public DiaExposureVisit getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}
