package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateLatestDevelopmentsRequest;
import org.emat.dto.LatestDevelopmentsResponse;
import org.emat.dto.UpdateLatestDevelopmentsRequest;
import org.emat.entity.LatestDevelopments;
import org.emat.mapper.LatestDevelopmentsMapper;
import org.emat.repository.LatestDevelopmentsRepository;
import org.emat.service.LatestDevelopmentsService;
import org.emat.validator.LatestDevelopmentsValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LatestDevelopmentsServiceImpl implements LatestDevelopmentsService {

    private final LatestDevelopmentsRepository repository;
    private final LatestDevelopmentsMapper mapper;
    private final LatestDevelopmentsValidator validator;

    @Override
    public LatestDevelopmentsResponse create(CreateLatestDevelopmentsRequest request) {
        log.info("Creating Latest Developments");
        LatestDevelopments developments = mapper.toEntity(request);
        return mapper.toResponse(repository.save(developments));
    }

    @Override
    @Transactional(readOnly = true)
    public LatestDevelopmentsResponse getById(Long id) {
        log.debug("Fetching Latest Developments with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LatestDevelopmentsResponse> getAll() {
        log.debug("Fetching all active Latest Developments");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public LatestDevelopmentsResponse update(Long id, UpdateLatestDevelopmentsRequest request) {
        log.info("Updating Latest Developments with ID: {}", id);
        LatestDevelopments developments = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(developments, request);
        return mapper.toResponse(repository.save(developments));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Latest Developments with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}