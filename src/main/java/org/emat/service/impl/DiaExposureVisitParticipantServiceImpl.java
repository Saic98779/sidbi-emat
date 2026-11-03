package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiaExposureVisitParticipantRequest;
import org.emat.dto.DiaExposureVisitParticipantResponse;
import org.emat.dto.UpdateDiaExposureVisitParticipantRequest;
import org.emat.entity.DiaExposureVisit;
import org.emat.entity.DiaExposureVisitParticipant;
import org.emat.mapper.DiaExposureVisitParticipantMapper;
import org.emat.repository.DiaExposureVisitParticipantRepository;
import org.emat.service.DiaExposureVisitParticipantService;
import org.emat.validator.DiaExposureVisitParticipantValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiaExposureVisitParticipantServiceImpl implements DiaExposureVisitParticipantService {

    private final DiaExposureVisitParticipantRepository repository;
    private final DiaExposureVisitParticipantMapper mapper;
    private final DiaExposureVisitParticipantValidator validator;

    @Override
    public DiaExposureVisitParticipantResponse create(
            CreateDiaExposureVisitParticipantRequest request) {
        log.info(
                "Creating DIA Exposure Visit Participant for DIA Exposure Visit ID: {}",
                request.getExposureVisitId());
        DiaExposureVisit exposureVisit =
                validator.getExposureVisitOrThrow(request.getExposureVisitId());
        DiaExposureVisitParticipant participant = mapper.toEntity(request, exposureVisit);
        return mapper.toResponse(repository.save(participant));
    }

    @Override
    @Transactional(readOnly = true)
    public DiaExposureVisitParticipantResponse getById(Long id) {
        log.debug("Fetching DIA Exposure Visit Participant with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaExposureVisitParticipantResponse> getAll() {
        log.debug("Fetching all active DIA Exposure Visit Participants");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaExposureVisitParticipantResponse> getAllByExposureVisitId(Long exposureVisitId) {
        log.debug(
                "Fetching all active DIA Exposure Visit Participants for DIA Exposure Visit ID: {}",
                exposureVisitId);
        return repository.findAllByExposureVisitIdAndIsActiveTrue(exposureVisitId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public DiaExposureVisitParticipantResponse update(
            Long id, UpdateDiaExposureVisitParticipantRequest request) {
        log.info("Updating DIA Exposure Visit Participant with ID: {}", id);
        DiaExposureVisitParticipant participant = validator.getByIdOrThrow(id);

        if (request.getExposureVisitId() != null
                && (participant.getExposureVisit() == null
                        || !request.getExposureVisitId()
                                .equals(participant.getExposureVisit().getId()))) {
            participant.setExposureVisit(
                    validator.getExposureVisitOrThrow(request.getExposureVisitId()));
        }

        mapper.applyUpdateRequest(participant, request);
        return mapper.toResponse(repository.save(participant));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA Exposure Visit Participant with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}
