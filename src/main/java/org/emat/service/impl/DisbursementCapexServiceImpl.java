package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.DisbursementCapexRequest;
import org.emat.dto.DisbursementCapexResponse;
import org.emat.entity.DisbursementCapex;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.mapper.DisbursementCapexMapper;
import org.emat.repository.DisbursementCapexRepository;
import org.emat.service.DisbursementCapexService;
import org.emat.validator.DisbursementCapexValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DisbursementCapexServiceImpl implements DisbursementCapexService {

    private final DisbursementCapexRepository disbursementCapexRepository;
    private final DisbursementCapexMapper disbursementCapexMapper;
    private final DisbursementCapexValidator disbursementCapexValidator;

    @Override
    public DisbursementCapexResponse create(DisbursementCapexRequest request) {
        IndustryAssociationRegistration registration =
                disbursementCapexValidator.getRegistrationOrThrow(request.getRegistrationId());

        DisbursementCapex expenditure = disbursementCapexMapper.toEntity(request, registration);
        return disbursementCapexMapper.toResponse(disbursementCapexRepository.save(expenditure));
    }

    @Override
    public DisbursementCapexResponse getById(Long id) {
        return disbursementCapexMapper.toResponse(disbursementCapexValidator.getByIdOrThrow(id));
    }

    @Override
    public DisbursementCapexResponse getByRegistrationId(Long registrationId) {
        return disbursementCapexMapper.toResponse(
                disbursementCapexValidator.getByRegistrationIdOrThrow(registrationId));
    }

    @Override
    public List<DisbursementCapexResponse> getAll() {
        return disbursementCapexRepository.findAll().stream()
                .map(disbursementCapexMapper::toResponse)
                .toList();
    }

    @Override
    public DisbursementCapexResponse update(Long id, DisbursementCapexRequest request) {
        DisbursementCapex existing = disbursementCapexValidator.getByIdOrThrow(id);

        if (request.getRegistrationId() != null
                && !request.getRegistrationId().equals(existing.getRegistration().getId())) {
            IndustryAssociationRegistration registration =
                    disbursementCapexValidator.getRegistrationOrThrow(request.getRegistrationId());
            existing.setRegistration(registration);
        }

        disbursementCapexMapper.updateEntityFromRequest(existing, request);
        return disbursementCapexMapper.toResponse(disbursementCapexRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        disbursementCapexRepository.delete(disbursementCapexValidator.getByIdOrThrow(id));
    }
}
