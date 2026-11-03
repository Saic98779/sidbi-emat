package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiaEnhancementRequest;
import org.emat.dto.DiaEnhancementResponse;
import org.emat.dto.UpdateDiaEnhancementRequest;
import org.emat.entity.DiaEnhancement;
import org.emat.mapper.DiaEnhancementMapper;
import org.emat.repository.DiaEnhancementRepository;
import org.emat.service.DiaEnhancementService;
import org.emat.validator.DiaEnhancementValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiaEnhancementServiceImpl implements DiaEnhancementService {

    private final DiaEnhancementRepository repository;
    private final DiaEnhancementMapper mapper;
    private final DiaEnhancementValidator validator;

    @Override
    public DiaEnhancementResponse create(CreateDiaEnhancementRequest request) {
        log.info("Creating DIA Enhancement");
        DiaEnhancement enhancement = mapper.toEntity(request);
        return mapper.toResponse(repository.save(enhancement));
    }

    @Override
    @Transactional(readOnly = true)
    public DiaEnhancementResponse getById(Long id) {
        log.debug("Fetching DIA Enhancement with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaEnhancementResponse> getAll() {
        log.debug("Fetching all active DIA Enhancements");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public DiaEnhancementResponse update(Long id, UpdateDiaEnhancementRequest request) {
        log.info("Updating DIA Enhancement with ID: {}", id);
        DiaEnhancement enhancement = validator.getByIdOrThrow(id);

        mapper.applyUpdateRequest(enhancement, request);
        return mapper.toResponse(repository.save(enhancement));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA Enhancement with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}
