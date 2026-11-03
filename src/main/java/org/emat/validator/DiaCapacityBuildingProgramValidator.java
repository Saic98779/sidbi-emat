package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DiaCapacityBuildingProgram;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DiaCapacityBuildingProgramRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaCapacityBuildingProgramValidator {

    private static final String NOT_FOUND_MESSAGE =
            "DIA Capacity Building Program not found with ID: ";

    private final DiaCapacityBuildingProgramRepository repository;

    public DiaCapacityBuildingProgram getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}
