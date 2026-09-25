package org.emat.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ActionPlanResponse;
import org.emat.dto.CreateActionPlanRequest;
import org.emat.dto.UpdateActionPlanRequest;
import org.emat.dto.UpdateActionPlanStatusRequest;
import org.emat.entity.ActionPlan;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.enums.Status;
import org.emat.mapper.ActionPlanMapper;
import org.emat.repository.ActionPlanRepository;
import org.emat.service.ActionPlanService;
import org.emat.validator.ActionPlanValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ActionPlanServiceImpl implements ActionPlanService {

    private final ActionPlanRepository repository;
    private final ActionPlanMapper mapper;
    private final ActionPlanValidator validator;

    @Override
    public ActionPlanResponse create(CreateActionPlanRequest request) {
        log.info("Creating Action Plan");
        IndustryAssociationRegistration registration =
                validator.getRegistrationOrThrow(request.getRegistrationId());
        ActionPlan actionPlan = mapper.toEntity(request, registration);
        return mapper.toResponse(repository.save(actionPlan));
    }

    @Override
    @Transactional(readOnly = true)
    public ActionPlanResponse getById(Long id) {
        log.debug("Fetching Action Plan with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActionPlanResponse> getAll() {
        log.debug("Fetching all active Action Plans");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public ActionPlanResponse update(Long id, UpdateActionPlanRequest request) {
        log.info("Updating Action Plan with ID: {}", id);
        ActionPlan actionPlan = validator.getByIdOrThrow(id);

        if (request.getRegistrationId() != null
                && !request.getRegistrationId().equals(actionPlan.getRegistration().getId())) {
            actionPlan.setRegistration(
                    validator.getRegistrationOrThrow(request.getRegistrationId()));
        }

        mapper.applyUpdateRequest(actionPlan, request);
        if (request.getActivities() != null) {
            actionPlan.getActivities().clear();
            actionPlan
                    .getActivities()
                    .addAll(mapper.mapUpdateActivities(request.getActivities(), actionPlan));
        }
        return mapper.toResponse(repository.save(actionPlan));
    }

    @Override
    public ActionPlanResponse updateStatus(Long id, UpdateActionPlanStatusRequest request) {
        log.info("Updating status for Action Plan with ID: {}", id);
        if (request == null
                || request.getMakerStatus() == null
                || request.getCheckerStatus() == null) {
            throw new IllegalArgumentException("Maker status and checker status must not be null");
        }
        ActionPlan actionPlan = validator.getByIdOrThrow(id);
        actionPlan.setMakerStatus(request.getMakerStatus());
        actionPlan.setCheckerStatus(request.getCheckerStatus());
        actionPlan.setRemark(request.getRemark());
        if (request.getMakerStatus() == Status.APPROVED) {
            actionPlan.setApprovedDate(LocalDate.now());
        } else {
            actionPlan.setApprovedDate(null);
        }
        return mapper.toResponse(repository.save(actionPlan));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Action Plan with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}
