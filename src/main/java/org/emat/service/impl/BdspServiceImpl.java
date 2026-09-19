package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BdspResponse;
import org.emat.dto.CreateBdspRequest;
import org.emat.dto.UpdateBdspRequest;
import org.emat.entity.Bdsp;
import org.emat.mapper.BdspMapper;
import org.emat.repository.BdspRepository;
import org.emat.service.BdspService;
import org.emat.validator.BdspValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BdspServiceImpl implements BdspService {

    private final BdspRepository repository;
    private final BdspMapper mapper;
    private final BdspValidator validator;

    @Override
    public BdspResponse create(CreateBdspRequest request) {
        log.info("Creating BDSP");
        Bdsp bdsp = mapper.toEntity(request);
        return mapper.toResponse(repository.save(bdsp));
    }

    @Override
    @Transactional(readOnly = true)
    public BdspResponse getById(Long id) {
        log.debug("Fetching BDSP with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BdspResponse> getAll() {
        log.debug("Fetching all active BDSP");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public BdspResponse update(Long id, UpdateBdspRequest request) {
        log.info("Updating BDSP with ID: {}", id);
        Bdsp bdsp = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(bdsp, request);
        return mapper.toResponse(repository.save(bdsp));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting BDSP with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}