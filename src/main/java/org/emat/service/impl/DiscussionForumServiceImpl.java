package org.emat.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiscussionForumRequest;
import org.emat.dto.DiscussionForumResponse;
import org.emat.dto.UpdateDiscussionForumRequest;
import org.emat.dto.UpdateDiscussionForumStatusRequest;
import org.emat.entity.DiscussionForum;
import org.emat.enums.Status;
import org.emat.mapper.DiscussionForumMapper;
import org.emat.repository.DiscussionForumRepository;
import org.emat.service.DiscussionForumService;
import org.emat.validator.DiscussionForumValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiscussionForumServiceImpl implements DiscussionForumService {

    private final DiscussionForumRepository repository;
    private final DiscussionForumMapper mapper;
    private final DiscussionForumValidator validator;

    @Override
    public DiscussionForumResponse create(CreateDiscussionForumRequest request) {
        log.info("Creating Discussion Forum");
        DiscussionForum forum = mapper.toEntity(request);
        return mapper.toResponse(repository.save(forum));
    }

    @Override
    @Transactional(readOnly = true)
    public DiscussionForumResponse getById(Long id) {
        log.debug("Fetching Discussion Forum with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionForumResponse> getAll() {
        log.debug("Fetching all active Discussion Forums");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public DiscussionForumResponse update(Long id, UpdateDiscussionForumRequest request) {
        log.info("Updating Discussion Forum with ID: {}", id);
        DiscussionForum forum = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(forum, request);
        return mapper.toResponse(repository.save(forum));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Discussion Forum with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }

    @Override
    public DiscussionForumResponse updateStatus(Long id, UpdateDiscussionForumStatusRequest request) {
        log.info("Updating status for Discussion Forum with ID: {}", id);
        if (request == null || request.getMakerStatus() == null) {
            throw new IllegalArgumentException("Maker status must not be null");
        }
        DiscussionForum forum = validator.getByIdOrThrow(id);
        forum.setMakerStatus(request.getMakerStatus());
        forum.setCheckerStatus(request.getCheckerStatus());
        forum.setRemark(request.getRemark());
        if (request.getMakerStatus() == Status.APPROVED) {
            forum.setApprovedDate(LocalDate.now());
        } else {
            forum.setApprovedDate(null);
        }
        return mapper.toResponse(repository.save(forum));
    }
}