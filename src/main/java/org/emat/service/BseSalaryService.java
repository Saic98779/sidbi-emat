package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.BseSalaryRequest;
import org.emat.dto.BseSalaryResponse;
import org.emat.dto.BseSalaryUpdateRequest;
import org.emat.entity.BseSalary;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.mapper.BseSalaryMapper;
import org.emat.repository.BseSalaryRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.validator.BseSalaryValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BseSalaryService {

    private final BseSalaryRepository vendorDisbursementRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final BseSalaryMapper bseSalaryMapper;
    private final BseSalaryValidator bseSalaryValidator;

    @Transactional
    public BseSalaryResponse create(BseSalaryRequest request) {
        BseSalary entity = new BseSalary();
        bseSalaryMapper.applyCreateFields(entity, request);
        entity.setMonthlySalaryDetails(bseSalaryMapper.mapCreateDetails(request.getDetails(), entity));
        return bseSalaryMapper.toResponse(vendorDisbursementRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public BseSalaryResponse getById(Long id) {
        return bseSalaryMapper.toResponse(bseSalaryValidator.getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<BseSalaryResponse> getAll() {
        return vendorDisbursementRepository.findAll().stream().map(bseSalaryMapper::toResponse).toList();
    }

    @Transactional
    public BseSalaryResponse update(Long id, BseSalaryUpdateRequest request) {
        BseSalary entity = bseSalaryValidator.getByIdOrThrow(id);
        bseSalaryMapper.applyUpdateFields(entity, request);
        if (request.getDetails() != null) {
            entity.getMonthlySalaryDetails().clear();
            entity.getMonthlySalaryDetails().addAll(bseSalaryMapper.mapUpdateDetails(request.getDetails(), entity));
        }
        return bseSalaryMapper.toResponse(vendorDisbursementRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        vendorDisbursementRepository.delete(bseSalaryValidator.getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<String> getApprovedIndustryAssociationNames() {
        return registrationRepository.findAllByIsActiveTrueAndIsSidbeApprovedTrue().stream()
                .map(IndustryAssociationRegistration::getIndustryAssociationName)
                .filter(name -> name != null && !name.isBlank())
                .toList();
    }
}
