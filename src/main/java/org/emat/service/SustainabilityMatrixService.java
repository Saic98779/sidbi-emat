package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.AppraisalDropdownDto;
import org.emat.dto.SustainabilityMatrixRequest;
import org.emat.dto.SustainabilityMatrixResponse;
import org.emat.entity.IndustryAssociationAppraisal;
import org.emat.entity.SustainabilityMatrix;
import org.emat.repository.IndustryAssociationAppraisalRepository;
import org.emat.repository.SustainabilityMatrixRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SustainabilityMatrixService {

    private final SustainabilityMatrixRepository repository;
    private final IndustryAssociationAppraisalRepository appraisalRepository;
    private static final String NOT_FOUND = "Sustainability Matrix not found: ";


    @Transactional
    public SustainabilityMatrixResponse create(
            SustainabilityMatrixRequest request
    ) {

        Long appraisalId = request.getAppraisalId();
        if (appraisalId == null) {
            throw new IllegalArgumentException("Appraisal ID is required");
        }

        IndustryAssociationAppraisal appraisal = appraisalRepository.findById(appraisalId)
                .orElseThrow(() -> new IllegalArgumentException("Appraisal not found: " + appraisalId));

        if (repository.existsByIndustryAssociationAppraisal_Id(appraisalId)) {
            throw new IllegalStateException("Sustainability Matrix already exists for Appraisal ID: " + appraisalId);
        }

        SustainabilityMatrix matrix = new SustainabilityMatrix();
        matrix.setIndustryAssociationAppraisal(appraisal);

        setFields(matrix, request);

        // Score comes directly from request
        matrix.setTotalScore(request.getTotalScore());

        SustainabilityMatrix saved = repository.save(matrix);

        return toResponse(saved);
    }


    @Transactional
    public SustainabilityMatrixResponse update(
            Long id,
            SustainabilityMatrixRequest request
    ) {

        SustainabilityMatrix matrix = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException(NOT_FOUND + id));

        /*
         * Update appraisal if appraisalId is provided
         */
        if (request.getAppraisalId() != null) {

            IndustryAssociationAppraisal appraisal = appraisalRepository.findById(request.getAppraisalId())
                    .orElseThrow(() -> new IllegalArgumentException("Appraisal not found: " + request.getAppraisalId()));
            matrix.setIndustryAssociationAppraisal(appraisal);
        }

        setFields(matrix, request);

        // Score comes directly from request
        matrix.setTotalScore(request.getTotalScore());

        SustainabilityMatrix updated = repository.save(matrix);

        return toResponse(updated);
    }


    @Transactional(readOnly = true)
    public SustainabilityMatrixResponse getById(Long id) {

        SustainabilityMatrix matrix = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException(NOT_FOUND + id));

        return toResponse(matrix);
    }


    @Transactional(readOnly = true)
    public List<SustainabilityMatrixResponse> getByAppraisalId(Long appraisalId) {

        return repository.findByIndustryAssociationAppraisal_Id(appraisalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<SustainabilityMatrixResponse> getAll() {

        return repository.findAll().stream().map(this::toResponse).toList();
    }


    @Transactional
    public void delete(Long id) {

        SustainabilityMatrix matrix = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException(NOT_FOUND + id));

        repository.delete(matrix);
    }


    private void setFields(
            SustainabilityMatrix matrix,
            SustainabilityMatrixRequest request
    ) {

        matrix.setActiveGoverningBody(
                request.getActiveGoverningBody()
        );

        matrix.setElection(
                request.getElection()
        );

        matrix.setCommittees(
                request.getCommittees()
        );

        matrix.setDocumentedPolicies(
                request.getDocumentedPolicies()
        );

        matrix.setAttendance(
                request.getAttendance()
        );

        matrix.setAgm(
                request.getAgm()
        );

        matrix.setActivePayingMembers(
                request.getActivePayingMembers()
        );

        matrix.setRetentionRate(
                request.getRetentionRate()
        );

        matrix.setOwnSourceRevenueReserves(
                request.getOwnSourceRevenueReserves()
        );

        matrix.setAnnualRevenueThreshold(
                request.getAnnualRevenueThreshold()
        );

        matrix.setProgramServiceOffered(
                request.getProgramServiceOffered()
        );

        matrix.setWebsite(
                request.getWebsite()
        );

        matrix.setCrm(
                request.getCrm()
        );

        matrix.setDigitalMemberDatabase(
                request.getDigitalMemberDatabase()
        );

        matrix.setSocialMedia(
                request.getSocialMedia()
        );

        matrix.setGovernment(
                request.getGovernment()
        );

        matrix.setBanks(
                request.getBanks()
        );

        matrix.setSidbi(
                request.getSidbi()
        );

        matrix.setAcademia(
                request.getAcademia()
        );

        matrix.setCorporates(
                request.getCorporates()
        );

        matrix.setDedicatedStaff(
                request.getDedicatedStaff()
        );

        matrix.setOperationalProcesses(
                request.getOperationalProcesses()
        );
    }


    public SustainabilityMatrixResponse toResponse(
            SustainabilityMatrix entity
    ) {

        if (entity == null) {
            return null;
        }

        return SustainabilityMatrixResponse.builder()

                .id(entity.getId())

                .appraisalId(
                        entity.getIndustryAssociationAppraisal() != null
                                ? entity.getIndustryAssociationAppraisal().getId()
                                : null
                )

                .activeGoverningBody(
                        entity.getActiveGoverningBody()
                )

                .election(
                        entity.getElection()
                )

                .committees(
                        entity.getCommittees()
                )

                .documentedPolicies(
                        entity.getDocumentedPolicies()
                )

                .attendance(
                        entity.getAttendance()
                )

                .agm(
                        entity.getAgm()
                )

                .activePayingMembers(
                        entity.getActivePayingMembers()
                )

                .retentionRate(
                        entity.getRetentionRate()
                )

                .ownSourceRevenueReserves(
                        entity.getOwnSourceRevenueReserves()
                )

                .annualRevenueThreshold(
                        entity.getAnnualRevenueThreshold()
                )

                .programServiceOffered(
                        entity.getProgramServiceOffered()
                )

                .website(
                        entity.getWebsite()
                )

                .crm(
                        entity.getCrm()
                )

                .digitalMemberDatabase(
                        entity.getDigitalMemberDatabase()
                )

                .socialMedia(
                        entity.getSocialMedia()
                )

                .government(
                        entity.getGovernment()
                )

                .banks(
                        entity.getBanks()
                )

                .sidbi(
                        entity.getSidbi()
                )

                .academia(
                        entity.getAcademia()
                )

                .corporates(
                        entity.getCorporates()
                )

                .dedicatedStaff(
                        entity.getDedicatedStaff()
                )

                .operationalProcesses(
                        entity.getOperationalProcesses()
                )

                .totalScore(
                        entity.getTotalScore()
                )

                .build();
    }

    @Transactional(readOnly = true)
    public List<AppraisalDropdownDto> getAppraisalDropdown() {

        return appraisalRepository.findAll().stream()
                .map(appraisal -> AppraisalDropdownDto.builder()
                        .id(appraisal.getId())
                        .name(appraisal.getRegistration() != null ? appraisal.getRegistration().getIndustryAssociationName() : null)
                        .build())
                .toList();
    }
}
