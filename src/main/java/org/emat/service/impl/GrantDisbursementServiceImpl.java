package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateGrantDisbursementRequest;
import org.emat.dto.GrantDisbursementResponse;
import org.emat.dto.UpdateGrantDisbursementRequest;
import org.emat.entity.GrantDisbursement;
import org.emat.mapper.GrantDisbursementMapper;
import org.emat.repository.GrantDisbursementRepository;
import org.emat.service.GrantDisbursementService;
import org.emat.validator.GrantDisbursementValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GrantDisbursementServiceImpl implements GrantDisbursementService {

    private final GrantDisbursementRepository repository;
    private final GrantDisbursementMapper mapper;
    private final GrantDisbursementValidator validator;

    @Override
    public GrantDisbursementResponse create(CreateGrantDisbursementRequest request) {
        log.info("Creating Grant Disbursement");
        GrantDisbursement disbursement = mapper.toEntity(request);
        return mapper.toResponse(repository.save(disbursement));
    }

    @Override
    @Transactional(readOnly = true)
    public GrantDisbursementResponse getById(Long id) {
        log.debug("Fetching Grant Disbursement with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GrantDisbursementResponse> getAll() {
        log.debug("Fetching all active Grant Disbursements");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public GrantDisbursementResponse update(Long id, UpdateGrantDisbursementRequest request) {
        log.info("Updating Grant Disbursement with ID: {}", id);
        GrantDisbursement disbursement = validator.getByIdOrThrow(id);

        mapper.applyUpdateRequest(disbursement, request);
        return mapper.toResponse(repository.save(disbursement));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Grant Disbursement with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}
