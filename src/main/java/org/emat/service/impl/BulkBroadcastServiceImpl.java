package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BulkBroadcastResponse;
import org.emat.dto.CreateBulkBroadcastRequest;
import org.emat.dto.UpdateBulkBroadcastRequest;
import org.emat.entity.BulkBroadcast;
import org.emat.mapper.BulkBroadcastMapper;
import org.emat.repository.BulkBroadcastRepository;
import org.emat.service.BulkBroadcastService;
import org.emat.validator.BulkBroadcastValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BulkBroadcastServiceImpl implements BulkBroadcastService {

    private final BulkBroadcastRepository repository;
    private final BulkBroadcastMapper mapper;
    private final BulkBroadcastValidator validator;

    @Override
    public BulkBroadcastResponse create(CreateBulkBroadcastRequest request) {
        log.info("Creating Bulk Broadcast");
        BulkBroadcast broadcast = mapper.toEntity(request);
        return mapper.toResponse(repository.save(broadcast));
    }

    @Override
    @Transactional(readOnly = true)
    public BulkBroadcastResponse getById(Long id) {
        log.debug("Fetching Bulk Broadcast with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BulkBroadcastResponse> getAll() {
        log.debug("Fetching all active Bulk Broadcast");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public BulkBroadcastResponse update(Long id, UpdateBulkBroadcastRequest request) {
        log.info("Updating Bulk Broadcast with ID: {}", id);
        BulkBroadcast broadcast = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(broadcast, request);
        return mapper.toResponse(repository.save(broadcast));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Bulk Broadcast with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}