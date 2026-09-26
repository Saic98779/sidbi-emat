package org.emat.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateElearningModuleContentRequest;
import org.emat.dto.ElearningModuleContentResponse;
import org.emat.dto.UpdateElearningModuleContentRequest;
import org.emat.dto.UpdateElearningModuleContentStatusRequest;
import org.emat.entity.ElearningModuleContent;
import org.emat.enums.Status;
import org.emat.mapper.ElearningModuleContentMapper;
import org.emat.repository.ElearningModuleContentRepository;
import org.emat.service.ElearningModuleContentService;
import org.emat.validator.ElearningModuleContentValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ElearningModuleContentServiceImpl implements ElearningModuleContentService {

    private final ElearningModuleContentRepository repository;
    private final ElearningModuleContentMapper mapper;
    private final ElearningModuleContentValidator validator;

    @Override
    public ElearningModuleContentResponse create(CreateElearningModuleContentRequest request) {
        log.info("Creating E-learning Module Content");
        ElearningModuleContent content = mapper.toEntity(request);
        return mapper.toResponse(repository.save(content));
    }

    @Override
    @Transactional(readOnly = true)
    public ElearningModuleContentResponse getById(Long id) {
        log.debug("Fetching E-learning Module Content with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ElearningModuleContentResponse> getAll() {
        log.debug("Fetching all active E-learning Module Content");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public ElearningModuleContentResponse update(
            Long id, UpdateElearningModuleContentRequest request) {
        log.info("Updating E-learning Module Content with ID: {}", id);
        ElearningModuleContent content = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(content, request);
        return mapper.toResponse(repository.save(content));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting E-learning Module Content with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }

    @Override
    public ElearningModuleContentResponse updateStatus(
            Long id, UpdateElearningModuleContentStatusRequest request) {
        log.info("Updating status for E-learning Module Content with ID: {}", id);
        if (request == null || request.getMakerStatus() == null) {
            throw new IllegalArgumentException("Maker status must not be null");
        }
        ElearningModuleContent content = validator.getByIdOrThrow(id);
        content.setMakerStatus(request.getMakerStatus());
        content.setCheckerStatus(request.getCheckerStatus());
        content.setRemark(request.getRemark());
        if (request.getMakerStatus() == Status.APPROVED) {
            content.setApprovedDate(LocalDate.now());
        } else {
            content.setApprovedDate(null);
        }
        return mapper.toResponse(repository.save(content));
    }
}