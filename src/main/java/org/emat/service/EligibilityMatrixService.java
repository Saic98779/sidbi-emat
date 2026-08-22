package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.EligibilityMatrixDto;
import org.emat.dto.RegistrationDropdownDto;
import org.emat.entity.EligibilityMatrix;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.mapper.EligibilityMatrixMapper;
import org.emat.repository.EligibilityMatrixRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.validator.EligibilityMatrixValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EligibilityMatrixService {

    private final EligibilityMatrixRepository eligibilityMatrixRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final EligibilityMatrixMapper eligibilityMatrixMapper;
    private final EligibilityMatrixValidator eligibilityMatrixValidator;

    @Transactional
    public EligibilityMatrixDto create(EligibilityMatrixDto request) {
        Long registrationId = request.getRegistrationId();

        eligibilityMatrixValidator.validateCreateAllowed(registrationId);
        IndustryAssociationRegistration registration = eligibilityMatrixValidator.getRegistrationOrThrow(registrationId);

        EligibilityMatrix entity = new EligibilityMatrix();
        entity.setRegistration(registration);
        eligibilityMatrixMapper.updateEntityFromRequest(request, entity);

        EligibilityMatrix saved = eligibilityMatrixRepository.save(entity);
        registration.setIsEligibleMatricsAdded(true);
        registrationRepository.save(registration);
        return eligibilityMatrixMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public EligibilityMatrixDto getById(Long id) {
        EligibilityMatrix entity = eligibilityMatrixValidator.getEligibilityByIdOrThrow(id);
        return eligibilityMatrixMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<EligibilityMatrixDto> getAll() {
        return eligibilityMatrixRepository.findAll().stream().map(eligibilityMatrixMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EligibilityMatrixDto getByRegistrationId(Long registrationId) {
        EligibilityMatrix entity = eligibilityMatrixValidator.getEligibilityByRegistrationIdOrThrow(registrationId);
        return eligibilityMatrixMapper.toResponse(entity);
    }

    @Transactional
    public EligibilityMatrixDto update(Long id, EligibilityMatrixDto request) {
        EligibilityMatrix entity = eligibilityMatrixValidator.getEligibilityByIdOrThrow(id);

        if (request.getRegistrationId() != null && !request.getRegistrationId().equals(entity.getRegistration().getId())) {
            IndustryAssociationRegistration registration =
                    eligibilityMatrixValidator.getRegistrationOrThrow(request.getRegistrationId());
            entity.setRegistration(registration);
        }

        eligibilityMatrixMapper.updateEntityFromRequest(request, entity);
        return eligibilityMatrixMapper.toResponse(eligibilityMatrixRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        eligibilityMatrixValidator.validateExistsById(id);
        eligibilityMatrixRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RegistrationDropdownDto> getRegistrationDropdown() {
        return registrationRepository.findAll().stream()
                .map(eligibilityMatrixMapper::toRegistrationDropdown)
                .toList();
    }
}