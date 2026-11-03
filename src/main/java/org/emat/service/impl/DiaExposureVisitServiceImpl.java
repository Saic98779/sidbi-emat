package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiaExposureVisitRequest;
import org.emat.dto.DiaExposureVisitResponse;
import org.emat.dto.UpdateDiaExposureVisitRequest;
import org.emat.entity.DiaExposureVisit;
import org.emat.mapper.DiaExposureVisitMapper;
import org.emat.repository.DiaExposureVisitRepository;
import org.emat.service.DiaExposureVisitService;
import org.emat.validator.DiaExposureVisitValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiaExposureVisitServiceImpl implements DiaExposureVisitService {

    private final DiaExposureVisitRepository repository;
    private final DiaExposureVisitMapper mapper;
    private final DiaExposureVisitValidator validator;

    @Override
    public DiaExposureVisitResponse create(CreateDiaExposureVisitRequest request) {
        log.info("Creating DIA Exposure Visit");
        DiaExposureVisit visit = mapper.toEntity(request);
        return mapper.toResponse(repository.save(visit));
    }

    @Override
    @Transactional(readOnly = true)
    public DiaExposureVisitResponse getById(Long id) {
        log.debug("Fetching DIA Exposure Visit with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaExposureVisitResponse> getAll() {
        log.debug("Fetching all active DIA Exposure Visits");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public DiaExposureVisitResponse update(Long id, UpdateDiaExposureVisitRequest request) {
        log.info("Updating DIA Exposure Visit with ID: {}", id);
        DiaExposureVisit visit = validator.getByIdOrThrow(id);

        mapper.applyUpdateRequest(visit, request);
        return mapper.toResponse(repository.save(visit));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA Exposure Visit with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}
