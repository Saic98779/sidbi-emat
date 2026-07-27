package org.emat.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.dto.SidbiSdeRequest;
import org.emat.dto.SidbiSdeResponse;
import org.emat.dto.UpdateSidbiSdeRequest;
import org.emat.entity.RegionalOffice;
import org.emat.entity.SidbiSde;
import org.emat.repository.RegionalOfficeRepository;
import org.emat.repository.SidbiSdeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SidbiSdeService {

    private final SidbiSdeRepository repository;
    private final RegionalOfficeRepository regionalOfficeRepository;

    /**
     * Create SIDBI SDE.
     */
    @Transactional
    public SidbiSdeResponse createSidbiSde(SidbiSdeRequest request) {

        if (repository.existsBySdeId(request.getSdeId())) {
            throw new IllegalArgumentException(
                    "SIDBI SDE already exists with SDE ID : " + request.getSdeId());
        }

        RegionalOffice regionalOffice = regionalOfficeRepository
                .findByUuid(UUID.fromString(request.getRegionalOfficeUuid()))
                .orElseThrow(() ->
                        new EntityNotFoundException("Regional Office not found"));

        SidbiSde sde = SidbiSde.builder()
                .sdeId(request.getSdeId())
                .name(request.getName())
                .email(request.getEmail())
                .mobileNo(request.getMobileNo())
                .regionalOffice(regionalOffice)
                .build();

        repository.save(sde);

        log.info("SIDBI SDE created successfully : {}", sde.getUuid());

        return convertToResponse(sde);
    }

    /**
     * Get SIDBI SDE by UUID.
     */
    @Transactional
    public SidbiSdeResponse getSidbiSde(String uuid) {

        SidbiSde sde = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("SIDBI SDE not found : " + uuid));

        return convertToResponse(sde);
    }

    /**
     * Get all active SIDBI SDEs.
     */
    @Transactional
    public List<SidbiSdeResponse> getAllSidbiSdes() {

        return repository.findAllByIsActiveTrue()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Search SIDBI SDE by State and District.
     */
//    @Transactional
//    public List<SidbiSdeResponse> search(String state, String district) {
//
//        return repository
//                .findAllByIsActiveTrueAndRegionalOfficeStateAndDistrict(
//                        state,
//                        district)
//                .stream()
//                .map(this::convertToResponse)
//                .collect(Collectors.toList());
//    }

    /**
     * Update SIDBI SDE.
     */
    @Transactional
    public SidbiSdeResponse updateSidbiSde(String uuid,
                                           UpdateSidbiSdeRequest request) {

        SidbiSde sde = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("SIDBI SDE not found : " + uuid));

        RegionalOffice regionalOffice = regionalOfficeRepository
                .findByUuid(UUID.fromString(request.getRegionalOfficeUuid()))
                .orElseThrow(() ->
                        new EntityNotFoundException("Regional Office not found"));

        sde.setName(request.getName());
        sde.setEmail(request.getEmail());
        sde.setMobileNo(request.getMobileNo());
        sde.setRegionalOffice(regionalOffice);

        repository.save(sde);

        return convertToResponse(sde);
    }

    /**
     * Soft delete.
     */
    @Transactional
    public void deleteSidbiSde(String uuid) {

        SidbiSde sde = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("SIDBI SDE not found : " + uuid));

        sde.setIsActive(false);

        repository.save(sde);
    }

    /**
     * Permanent delete.
     */
    @Transactional
    public void permanentlyDeleteSidbiSde(String uuid) {

        SidbiSde sde = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("SIDBI SDE not found : " + uuid));

        repository.delete(sde);
    }

    /**
     * Convert Entity to Response.
     */
    private SidbiSdeResponse convertToResponse(SidbiSde sde) {

        return SidbiSdeResponse.builder()
                .uuid(sde.getUuid())
                .sdeId(sde.getSdeId())
                .name(sde.getName())
                .email(sde.getEmail())
                .mobileNo(sde.getMobileNo())
                .regionalOfficeUuid(sde.getRegionalOffice().getUuid().toString())
                .roId(sde.getRegionalOffice().getRoId())
                .roName(sde.getRegionalOffice().getRoName())
                .build();
    }

    public List<SidbiSdeDropdownResponse> getDropdownByBranch(UUID branchUuid) {
        return repository.findDropdownByBranchUuid(branchUuid);
    }
}