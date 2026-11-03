package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DiaCapacityBuildingProgram;
import org.emat.entity.DiaMsmeParticipant;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DiaCapacityBuildingProgramRepository;
import org.emat.repository.DiaMsmeParticipantRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaMsmeParticipantValidator {

    private static final String NOT_FOUND_MESSAGE = "DIA MSME Participant not found with ID: ";
    private static final String EVENT_NOT_FOUND_MESSAGE =
            "DIA Capacity Building Program not found with ID: ";

    private final DiaMsmeParticipantRepository repository;
    private final DiaCapacityBuildingProgramRepository diaCapacityBuildingProgramRepository;

    public DiaMsmeParticipant getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }

    public DiaCapacityBuildingProgram getEventOrThrow(Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID must not be null");
        }
        return diaCapacityBuildingProgramRepository
                .findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(EVENT_NOT_FOUND_MESSAGE + eventId));
    }
}
