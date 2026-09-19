package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreatePopUpsRequest;
import org.emat.dto.PopUpsResponse;
import org.emat.dto.UpdatePopUpsRequest;
import org.emat.entity.PopUps;
import org.emat.mapper.PopUpsMapper;
import org.emat.repository.PopUpsRepository;
import org.emat.service.PopUpsService;
import org.emat.validator.PopUpsValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PopUpsServiceImpl implements PopUpsService {

    private final PopUpsRepository repository;
    private final PopUpsMapper mapper;
    private final PopUpsValidator validator;

    @Override
    public PopUpsResponse create(CreatePopUpsRequest request) {
        log.info("Creating Pop-Ups");
        PopUps popUps = mapper.toEntity(request);
        return mapper.toResponse(repository.save(popUps));
    }

    @Override
    @Transactional(readOnly = true)
    public PopUpsResponse getById(Long id) {
        log.debug("Fetching Pop-Ups with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PopUpsResponse> getAll() {
        log.debug("Fetching all active Pop-Ups");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public PopUpsResponse update(Long id, UpdatePopUpsRequest request) {
        log.info("Updating Pop-Ups with ID: {}", id);
        PopUps popUps = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(popUps, request);
        return mapper.toResponse(repository.save(popUps));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Pop-Ups with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}