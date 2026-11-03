package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiaMsmeParticipantRequest;
import org.emat.dto.DiaMsmeParticipantResponse;
import org.emat.dto.UpdateDiaMsmeParticipantRequest;
import org.emat.entity.DiaCapacityBuildingProgram;
import org.emat.entity.DiaMsmeParticipant;
import org.emat.mapper.DiaMsmeParticipantMapper;
import org.emat.repository.DiaMsmeParticipantRepository;
import org.emat.service.DiaMsmeParticipantService;
import org.emat.validator.DiaMsmeParticipantValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiaMsmeParticipantServiceImpl implements DiaMsmeParticipantService {

    private final DiaMsmeParticipantRepository repository;
    private final DiaMsmeParticipantMapper mapper;
    private final DiaMsmeParticipantValidator validator;

    @Override
    public DiaMsmeParticipantResponse create(CreateDiaMsmeParticipantRequest request) {
        log.info(
                "Creating DIA MSME Participant for DIA Capacity Building Program ID: {}",
                request.getEventId());
        DiaCapacityBuildingProgram event = validator.getEventOrThrow(request.getEventId());
        DiaMsmeParticipant participant = mapper.toEntity(request, event);
        return mapper.toResponse(repository.save(participant));
    }

    @Override
    @Transactional(readOnly = true)
    public DiaMsmeParticipantResponse getById(Long id) {
        log.debug("Fetching DIA MSME Participant with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaMsmeParticipantResponse> getAll() {
        log.debug("Fetching all active DIA MSME Participants");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaMsmeParticipantResponse> getAllByEventId(Long eventId) {
        log.debug(
                "Fetching all active DIA MSME Participants for DIA Capacity Building Program ID:"
                        + " {}",
                eventId);
        return repository.findAllByEventIdAndIsActiveTrue(eventId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public DiaMsmeParticipantResponse update(Long id, UpdateDiaMsmeParticipantRequest request) {
        log.info("Updating DIA MSME Participant with ID: {}", id);
        DiaMsmeParticipant participant = validator.getByIdOrThrow(id);

        if (request.getEventId() != null
                && (participant.getEvent() == null
                        || !request.getEventId().equals(participant.getEvent().getId()))) {
            participant.setEvent(validator.getEventOrThrow(request.getEventId()));
        }

        mapper.applyUpdateRequest(participant, request);
        return mapper.toResponse(repository.save(participant));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA MSME Participant with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}
