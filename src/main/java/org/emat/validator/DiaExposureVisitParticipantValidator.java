package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DiaExposureVisit;
import org.emat.entity.DiaExposureVisitParticipant;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DiaExposureVisitParticipantRepository;
import org.emat.repository.DiaExposureVisitRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaExposureVisitParticipantValidator {

    private static final String NOT_FOUND_MESSAGE =
            "DIA Exposure Visit Participant not found with ID: ";
    private static final String EXPOSURE_VISIT_NOT_FOUND_MESSAGE =
            "DIA Exposure Visit not found with ID: ";

    private final DiaExposureVisitParticipantRepository repository;
    private final DiaExposureVisitRepository diaExposureVisitRepository;

    public DiaExposureVisitParticipant getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }

    public DiaExposureVisit getExposureVisitOrThrow(Long exposureVisitId) {
        if (exposureVisitId == null) {
            throw new IllegalArgumentException("Exposure Visit ID must not be null");
        }
        return diaExposureVisitRepository
                .findById(exposureVisitId)
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        EXPOSURE_VISIT_NOT_FOUND_MESSAGE + exposureVisitId));
    }
}
