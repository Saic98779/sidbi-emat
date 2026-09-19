package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.Dia3CInfoSeries;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.Dia3CInfoSeriesRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Dia3CInfoSeriesValidator {

    private static final String NOT_FOUND_MESSAGE = "DIA 3C Info Series not found with ID: ";

    private final Dia3CInfoSeriesRepository repository;

    public Dia3CInfoSeries getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}