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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SustainabilityMatrixService {

    private final SustainabilityMatrixRepository repository;
    private final IndustryAssociationAppraisalRepository appraisalRepository;

    /**
     * Create Sustainability Matrix
     */
    @Transactional
    public SustainabilityMatrixResponse create(
            SustainabilityMatrixRequest request
    ) {

        if (request.getAppraisalUuid() == null) {
            throw new IllegalArgumentException("Appraisal UUID is required");
        }

        IndustryAssociationAppraisal appraisal =
                appraisalRepository.findByUuid(request.getAppraisalUuid())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Appraisal not found: "
                                                + request.getAppraisalUuid()
                                )
                        );

        SustainabilityMatrix matrix = new SustainabilityMatrix();

        matrix.setIndustryAssociationAppraisal(appraisal);

        setFields(matrix, request);

        // Score comes directly from request
        matrix.setTotalScore(request.getTotalScore());

        SustainabilityMatrix saved = repository.save(matrix);

        return toResponse(saved);
    }

    /**
     * Update Sustainability Matrix
     */
    @Transactional
    public SustainabilityMatrixResponse update(
            UUID uuid,
            SustainabilityMatrixRequest request
    ) {

        SustainabilityMatrix matrix =
                repository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sustainability Matrix not found: "
                                                + uuid
                                )
                        );

        /*
         * Update appraisal if appraisalUuid is provided
         */
        if (request.getAppraisalUuid() != null) {

            IndustryAssociationAppraisal appraisal =
                    appraisalRepository.findByUuid(
                                    request.getAppraisalUuid()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Appraisal not found: "
                                                    + request.getAppraisalUuid()
                                    )
                            );

            matrix.setIndustryAssociationAppraisal(appraisal);
        }

        setFields(matrix, request);

        // Score comes directly from request
        matrix.setTotalScore(request.getTotalScore());

        SustainabilityMatrix updated = repository.save(matrix);

        return toResponse(updated);
    }

    /**
     * Get Sustainability Matrix by UUID
     */
    @Transactional(readOnly = true)
    public SustainabilityMatrixResponse getById(UUID uuid) {

        SustainabilityMatrix matrix =
                repository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sustainability Matrix not found: "
                                                + uuid
                                )
                        );

        return toResponse(matrix);
    }

    /**
     * Get Sustainability Matrix by Appraisal UUID
     */
    @Transactional(readOnly = true)
    public List<SustainabilityMatrixResponse> getByAppraisalUuid(
            UUID appraisalUuid
    ) {

        return repository
                .findByIndustryAssociationAppraisalUuid(appraisalUuid)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Get all Sustainability Matrix records
     */
    @Transactional(readOnly = true)
    public List<SustainabilityMatrixResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Delete Sustainability Matrix
     */
    @Transactional
    public void delete(UUID uuid) {

        SustainabilityMatrix matrix =
                repository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sustainability Matrix not found: "
                                                + uuid
                                )
                        );

        repository.delete(matrix);
    }

    /**
     * Set request fields into entity
     */
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

    /**
     * Convert Entity to Response DTO
     */
    public SustainabilityMatrixResponse toResponse(
            SustainabilityMatrix entity
    ) {

        if (entity == null) {
            return null;
        }

        return SustainabilityMatrixResponse.builder()

                .uuid(entity.getUuid())

                .appraisalUuid(
                        entity.getIndustryAssociationAppraisal() != null
                                ? entity.getIndustryAssociationAppraisal().getUuid()
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

        return appraisalRepository.findAll()
                .stream()
                .map(appraisal -> AppraisalDropdownDto.builder()
                        .uuid(appraisal.getUuid())
                        .name(appraisal.getRegistration().getIndustryAssociationName())
                        .build())
                .toList();
    }
}