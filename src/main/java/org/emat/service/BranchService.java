package org.emat.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BranchDropdownResponse;
import org.emat.dto.BranchResponse;
import org.emat.dto.BranchRequest;
import org.emat.dto.UpdateBranchRequest;
import org.emat.entity.Branch;
import org.emat.entity.RegionalOffice;
import org.emat.repository.BranchRepository;
import org.emat.repository.RegionalOfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchService {

    private final BranchRepository repository;
    private final RegionalOfficeRepository regionalOfficeRepository;

    /**
     * Create Branch.
     */
    @Transactional
    public BranchResponse createBranch(BranchRequest request) {

        if (repository.existsByBoId(request.getBoId())) {
            throw new IllegalArgumentException(
                    "Branch already exists with BO ID : " + request.getBoId());
        }

        RegionalOffice regionalOffice = regionalOfficeRepository
                .findByUuid(UUID.fromString(request.getRegionalOfficeUuid()))
                .orElseThrow(() ->
                        new EntityNotFoundException("Regional Office not found"));

        Branch branch = Branch.builder()
                .boId(request.getBoId())
                .branchName(request.getBranchName())
                .city(request.getCity())
                .district(request.getDistrict())
                .state(request.getState())
                .address(request.getAddress())
                .contactNo(request.getContactNo())
                .regionalOffice(regionalOffice)
                .build();

        repository.save(branch);

        log.info("Branch created successfully : {}", branch.getUuid());

        return convertToResponse(branch);
    }

    /**
     * Get Branch by UUID.
     */
    @Transactional
    public BranchResponse getBranch(String uuid) {

        Branch branch = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("Branch not found : " + uuid));

        return convertToResponse(branch);
    }

    /**
     * Get all active branches.
     */
    @Transactional
    public List<BranchResponse> getAllBranches() {

        return repository.findAllByIsActiveTrue()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Search branches by state and district.
     */
    @Transactional
    public List<BranchResponse> search(String state,
                                       String district) {

        return repository
                .findAllByIsActiveTrueAndStateAndDistrict(state, district)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get branches by Regional Office.
     */
    @Transactional
    public List<BranchResponse> getBranchesByRegionalOffice(String regionalOfficeUuid) {

        return repository
                .findAllByIsActiveTrueAndRegionalOfficeUuid(
                        UUID.fromString(regionalOfficeUuid))
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update Branch.
     */
    @Transactional
    public BranchResponse updateBranch(String uuid,
                                       UpdateBranchRequest request) {

        Branch branch = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("Branch not found : " + uuid));

        RegionalOffice regionalOffice = regionalOfficeRepository
                .findByUuid(UUID.fromString(request.getRegionalOfficeUuid()))
                .orElseThrow(() ->
                        new EntityNotFoundException("Regional Office not found"));

        branch.setBranchName(request.getBranchName());
        branch.setCity(request.getCity());
        branch.setDistrict(request.getDistrict());
        branch.setState(request.getState());
        branch.setAddress(request.getAddress());
        branch.setContactNo(request.getContactNo());
        branch.setRegionalOffice(regionalOffice);

        repository.save(branch);

        return convertToResponse(branch);
    }

    /**
     * Soft delete.
     */
    @Transactional
    public void deleteBranch(String uuid) {

        Branch branch = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("Branch not found : " + uuid));

        branch.setIsActive(false);

        repository.save(branch);
    }

    /**
     * Permanent delete.
     */
    @Transactional
    public void permanentlyDeleteBranch(String uuid) {

        Branch branch = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("Branch not found : " + uuid));

        repository.delete(branch);
    }

    /**
     * Convert Entity to Response.
     */
    private BranchResponse convertToResponse(Branch branch) {

        return BranchResponse.builder()
                .uuid(branch.getUuid())
                .boId(branch.getBoId())
                .branchName(branch.getBranchName())
                .city(branch.getCity())
                .district(branch.getDistrict())
                .state(branch.getState())
                .address(branch.getAddress())
                .contactNo(branch.getContactNo())
                .regionalOfficeUuid(branch.getRegionalOffice().getUuid().toString())
                .roId(branch.getRegionalOffice().getRoId())
                .roName(branch.getRegionalOffice().getRoName())
                .isActive(branch.getIsActive())
                .build();
    }

    public List<BranchDropdownResponse> getBranchDropdownByState(String state) {
        return repository.findBranchDropdownByState(state);
    }
}