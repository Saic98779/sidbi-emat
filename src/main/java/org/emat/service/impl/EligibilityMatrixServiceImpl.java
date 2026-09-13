package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.EligibilityMatrixDto;
import org.emat.dto.RegistrationDropdownDto;
import org.emat.entity.EligibilityMatrix;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.mapper.EligibilityMatrixMapper;
import org.emat.repository.EligibilityMatrixRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.service.EligibilityMatrixService;
import org.emat.service.StageService;
import org.emat.util.CommonUtil;
import org.emat.validator.EligibilityMatrixValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EligibilityMatrixServiceImpl implements EligibilityMatrixService {

    private final EligibilityMatrixRepository eligibilityMatrixRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final EligibilityMatrixMapper eligibilityMatrixMapper;
    private final EligibilityMatrixValidator eligibilityMatrixValidator;
    private final StageService stageService;
    private final CommonUtil commonUtil;

    @Override
    @Transactional
    public EligibilityMatrixDto create(EligibilityMatrixDto request) {
        Long registrationId = request.getRegistrationId();

        eligibilityMatrixValidator.validateCreateAllowed(registrationId);
        IndustryAssociationRegistration registration =
                eligibilityMatrixValidator.getRegistrationOrThrow(registrationId);

        EligibilityMatrix entity = new EligibilityMatrix();
        entity.setRegistration(registration);
        eligibilityMatrixMapper.updateEntityFromRequest(request, entity);

        EligibilityMatrix saved = eligibilityMatrixRepository.save(entity);
        registrationRepository.save(registration);
        if (saved != null) {
            stageService.updateStage(
                    saved.getRegistration().getId(),
                    request.getStageId(),
                    request.getStageComments(),
                    commonUtil.getCurrentUsername());
        }
        return eligibilityMatrixMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EligibilityMatrixDto getById(Long id) {
        EligibilityMatrix entity = eligibilityMatrixValidator.getEligibilityByIdOrThrow(id);
        return eligibilityMatrixMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EligibilityMatrixDto> getAll() {
        return eligibilityMatrixRepository.findAll().stream()
                .map(eligibilityMatrixMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EligibilityMatrixDto getByRegistrationId(Long registrationId) {
        EligibilityMatrix entity =
                eligibilityMatrixValidator.getEligibilityByRegistrationIdOrThrow(registrationId);
        return eligibilityMatrixMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public EligibilityMatrixDto update(Long id, EligibilityMatrixDto request) {
        EligibilityMatrix entity = eligibilityMatrixValidator.getEligibilityByIdOrThrow(id);

        if (request.getRegistrationId() != null
                && !request.getRegistrationId().equals(entity.getRegistration().getId())) {
            IndustryAssociationRegistration registration =
                    eligibilityMatrixValidator.getRegistrationOrThrow(request.getRegistrationId());
            entity.setRegistration(registration);
        }

        eligibilityMatrixMapper.updateEntityFromRequest(request, entity);
        return eligibilityMatrixMapper.toResponse(eligibilityMatrixRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eligibilityMatrixValidator.validateExistsById(id);
        eligibilityMatrixRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationDropdownDto> getRegistrationDropdown() {
        return registrationRepository.findAll().stream()
                .map(eligibilityMatrixMapper::toRegistrationDropdown)
                .toList();
    }
}
