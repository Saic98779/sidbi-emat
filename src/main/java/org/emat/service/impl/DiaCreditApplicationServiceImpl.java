package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiaCreditApplicationRequest;
import org.emat.dto.DiaCreditApplicationResponse;
import org.emat.dto.UpdateDiaCreditApplicationRequest;
import org.emat.entity.DiaCreditApplication;
import org.emat.mapper.DiaCreditApplicationMapper;
import org.emat.repository.DiaCreditApplicationRepository;
import org.emat.service.DiaCreditApplicationService;
import org.emat.validator.DiaCreditApplicationValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiaCreditApplicationServiceImpl implements DiaCreditApplicationService {

    private final DiaCreditApplicationRepository repository;
    private final DiaCreditApplicationMapper mapper;
    private final DiaCreditApplicationValidator validator;

    @Override
    public DiaCreditApplicationResponse create(CreateDiaCreditApplicationRequest request) {
        log.info("Creating DIA Credit Application");
        DiaCreditApplication application = mapper.toEntity(request);
        return mapper.toResponse(repository.save(application));
    }

    @Override
    @Transactional(readOnly = true)
    public DiaCreditApplicationResponse getById(Long id) {
        log.debug("Fetching DIA Credit Application with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaCreditApplicationResponse> getAll() {
        log.debug("Fetching all active DIA Credit Applications");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public DiaCreditApplicationResponse update(Long id, UpdateDiaCreditApplicationRequest request) {
        log.info("Updating DIA Credit Application with ID: {}", id);
        DiaCreditApplication application = validator.getByIdOrThrow(id);

        mapper.applyUpdateRequest(application, request);
        return mapper.toResponse(repository.save(application));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA Credit Application with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}
