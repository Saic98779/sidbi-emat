package org.emat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationRegistrationRequest;
import org.emat.dto.IndustryAssociationRegistrationResponse;
import org.emat.dto.UpdateIndustryAssociationRegistrationRequest;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.User;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.UserRepository;
import org.emat.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service layer for IndustryAssociationRegistration business logic.
 * Handles CRUD operations and business rules for Industry Association Registrations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IndustryAssociationRegistrationService {

    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration not found with UUID: ";

    private final IndustryAssociationRegistrationRepository repository;
    private final UserRepository userRepository;

    /**
     * Create a new Industry Association Registration.
     *
     * @param request the create request DTO
     * @return the created registration response
     */
    public IndustryAssociationRegistrationResponse createRegistration(
            CreateIndustryAssociationRegistrationRequest request) {
        log.info("Creating new Industry Association Registration for: {}", request.getIndustryAssociationName());

        // Validate duplicate registration
        if (repository.existsByIndustryAssociationNameAndStateAndIsActiveTrue(
                request.getIndustryAssociationName(), request.getState())) {
            log.warn("Duplicate registration attempt for: {} in state: {}",
                    request.getIndustryAssociationName(), request.getState());
            throw new IllegalArgumentException(
                    "Registration already exists for this Industry Association in the state");
        }

        // Fetch the SIDBI approving user if provided
        User sidbiApprover = null;
        if (request.getSidbeApprovedByUserId() != null) {
            sidbiApprover = userRepository.findById(request.getSidbeApprovedByUserId())
                    .orElseThrow(() -> {
                        log.error("SIDBI approver user not found with ID: {}", request.getSidbeApprovedByUserId());
                        return new EntityNotFoundException("User not found with ID: " + request.getSidbeApprovedByUserId());
                    });
            log.info("SIDBI approver user found: {}", sidbiApprover.getUsername());
        }

        IndustryAssociationRegistration registration = IndustryAssociationRegistration.builder()
                .state(request.getState())
                .industryAssociationName(request.getIndustryAssociationName())
                .constitutionType(request.getConstitutionType())
                .constitutionOther(request.getConstitutionOther())
                .incorporationDate(request.getIncorporationDate())
                .incorporationCertificate(request.getIncorporationCertificate())
                .iaType(request.getIaType())
                .constitutionProof(request.getConstitutionProof())
                .district(request.getDistrict())
                .pincode(request.getPincode())
                .apexHolderName(request.getApexHolderName())
                .apexHolderDesignation(request.getApexHolderDesignation())
                .apexHolderMobile(request.getApexHolderMobile())
                .apexHolderEmail(request.getApexHolderEmail())
                .addressProofType(request.getAddressProofType())
                .addressProof(request.getAddressProof())
                .idProofType(request.getIdProofType())
                .idProof(request.getIdProof())
                .nodalName(request.getNodalName())
                .nodalDesignation(request.getNodalDesignation())
                .nodalMobile(request.getNodalMobile())
                .nodalEmail(request.getNodalEmail())
                .sidbiBranch(request.getSidbiBranch())
                .mappedWithCluster(request.getMappedWithCluster())
                .clusterName(request.getClusterName())
                .mappedWithImportantDistrict(request.getMappedWithImportantDistrict())
                .districtMsmeCount(request.getDistrictMsmeCount())
                .activeMembersAbove200(request.getActiveMembersAbove200())
                .activeMembersCount(request.getActiveMembersCount())
                .justification(request.getJustification())
                .approvalLetter(request.getApprovalLetter())
                .msmeCountWithoutTraders(request.getMsmeCountWithoutTraders())
                .memberDirectoryAvailable(request.getMemberDirectoryAvailable())
                .buildingType(request.getBuildingType())
                .declarationSigned(request.getDeclarationSigned())
                .electricityBill(request.getElectricityBill())
                .telephoneBill(request.getTelephoneBill())
                .itInfrastructureAvailable(request.getItInfrastructureAvailable())
                .infrastructureType(request.getInfrastructureType())
                .secretariatStaffAvailable(request.getSecretariatStaffAvailable())
                .websiteAvailable(request.getWebsiteAvailable())
                .websiteUrl(request.getWebsiteUrl())
                .paidServicesAvailable(request.getPaidServicesAvailable())
                .adverseRemarksAvailable(request.getAdverseRemarksAvailable())
                .adverseRemarks(request.getAdverseRemarks())
                .webReport(request.getWebReport())
                .selectionCriteria(request.getSelectionCriteria())
                .willingnessComments(request.getWillingnessComments())
                .workedWithSidbiBefore(request.getWorkedWithSidbiBefore())
                .grantProposed(request.getGrantProposed())
                .grantDetails(request.getGrantDetails())
                .envisagedOutput(request.getEnvisagedOutput())
                .envisagedOutcome(request.getEnvisagedOutcome())
                .envisagedImpact(request.getEnvisagedImpact())
                .sde(request.getSde())
                .isSidbeApproved(request.getIsSidbeApproved())
                .sidbeApprovedByUser(sidbiApprover)
                .createdBy(request.getCreatedBy())
                .build();

        IndustryAssociationRegistration saved = repository.save(registration);
        log.info("Industry Association Registration created successfully with UUID: {}", saved.getUuid());
        return convertToResponse(saved);
    }

    /**
     * Retrieve a registration by UUID.
     *
     * @param uuid the unique identifier
     * @return the registration response
     * @throws EntityNotFoundException if registration not found
     */
    @Transactional(readOnly = true)
    public IndustryAssociationRegistrationResponse getRegistrationById(String uuid) {
        log.debug("Fetching Industry Association Registration with UUID: {}", uuid);
        UUID registrationUuid = UuidUtil.toUuid(uuid);
        IndustryAssociationRegistration registration = repository.findByUuid(registrationUuid)
                .orElseThrow(() -> {
                    log.error(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
                    return new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
                });
        return convertToResponse(registration);
    }

    /**
     * Retrieve all active registrations.
     *
     * @return list of active registration responses
     */
    @Transactional(readOnly = true)
    public List<IndustryAssociationRegistrationResponse> getAllRegistrations() {
        log.debug("Fetching all active Industry Association Registrations");
        return repository.findAllByIsActiveTrue().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing registration.
     *
     * @param uuid the unique identifier
     * @param request the update request DTO
     * @return the updated registration response
     * @throws EntityNotFoundException if registration not found
     */
    public IndustryAssociationRegistrationResponse updateRegistration(
            String uuid, UpdateIndustryAssociationRegistrationRequest request) {
        log.info("Updating Industry Association Registration with UUID: {}", uuid);
        UUID registrationUuid = UuidUtil.toUuid(uuid);
        IndustryAssociationRegistration registration = repository.findByUuid(registrationUuid)
                .orElseThrow(() -> {
                    log.error(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
                    return new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
                });

        // Update fields selectively based on request
        if (request.getState() != null) {
            registration.setState(request.getState());
        }
        if (request.getIndustryAssociationName() != null) {
            registration.setIndustryAssociationName(request.getIndustryAssociationName());
        }
        if (request.getConstitutionType() != null) {
            registration.setConstitutionType(request.getConstitutionType());
        }
        if (request.getConstitutionOther() != null) {
            registration.setConstitutionOther(request.getConstitutionOther());
        }
        if (request.getIncorporationDate() != null) {
            registration.setIncorporationDate(request.getIncorporationDate());
        }
        if (request.getIncorporationCertificate() != null) {
            registration.setIncorporationCertificate(request.getIncorporationCertificate());
        }
        if (request.getIaType() != null) {
            registration.setIaType(request.getIaType());
        }
        if (request.getConstitutionProof() != null) {
            registration.setConstitutionProof(request.getConstitutionProof());
        }
        if (request.getDistrict() != null) {
            registration.setDistrict(request.getDistrict());
        }
        if (request.getPincode() != null) {
            registration.setPincode(request.getPincode());
        }
        if (request.getApexHolderName() != null) {
            registration.setApexHolderName(request.getApexHolderName());
        }
        if (request.getApexHolderDesignation() != null) {
            registration.setApexHolderDesignation(request.getApexHolderDesignation());
        }
        if (request.getApexHolderMobile() != null) {
            registration.setApexHolderMobile(request.getApexHolderMobile());
        }
        if (request.getApexHolderEmail() != null) {
            registration.setApexHolderEmail(request.getApexHolderEmail());
        }
        if (request.getAddressProofType() != null) {
            registration.setAddressProofType(request.getAddressProofType());
        }
        if (request.getAddressProof() != null) {
            registration.setAddressProof(request.getAddressProof());
        }
        if (request.getIdProofType() != null) {
            registration.setIdProofType(request.getIdProofType());
        }
        if (request.getIdProof() != null) {
            registration.setIdProof(request.getIdProof());
        }
        if (request.getNodalName() != null) {
            registration.setNodalName(request.getNodalName());
        }
        if (request.getNodalDesignation() != null) {
            registration.setNodalDesignation(request.getNodalDesignation());
        }
        if (request.getNodalMobile() != null) {
            registration.setNodalMobile(request.getNodalMobile());
        }
        if (request.getNodalEmail() != null) {
            registration.setNodalEmail(request.getNodalEmail());
        }
        if (request.getSidbiBranch() != null) {
            registration.setSidbiBranch(request.getSidbiBranch());
        }
        if (request.getMappedWithCluster() != null) {
            registration.setMappedWithCluster(request.getMappedWithCluster());
        }
        if (request.getClusterName() != null) {
            registration.setClusterName(request.getClusterName());
        }
        if (request.getMappedWithImportantDistrict() != null) {
            registration.setMappedWithImportantDistrict(request.getMappedWithImportantDistrict());
        }
        if (request.getDistrictMsmeCount() != null) {
            registration.setDistrictMsmeCount(request.getDistrictMsmeCount());
        }
        if (request.getActiveMembersAbove200() != null) {
            registration.setActiveMembersAbove200(request.getActiveMembersAbove200());
        }
        if (request.getActiveMembersCount() != null) {
            registration.setActiveMembersCount(request.getActiveMembersCount());
        }
        if (request.getJustification() != null) {
            registration.setJustification(request.getJustification());
        }
        if (request.getApprovalLetter() != null) {
            registration.setApprovalLetter(request.getApprovalLetter());
        }
        if (request.getMsmeCountWithoutTraders() != null) {
            registration.setMsmeCountWithoutTraders(request.getMsmeCountWithoutTraders());
        }
        if (request.getMemberDirectoryAvailable() != null) {
            registration.setMemberDirectoryAvailable(request.getMemberDirectoryAvailable());
        }
        if (request.getBuildingType() != null) {
            registration.setBuildingType(request.getBuildingType());
        }
        if (request.getDeclarationSigned() != null) {
            registration.setDeclarationSigned(request.getDeclarationSigned());
        }
        if (request.getElectricityBill() != null) {
            registration.setElectricityBill(request.getElectricityBill());
        }
        if (request.getTelephoneBill() != null) {
            registration.setTelephoneBill(request.getTelephoneBill());
        }
        if (request.getItInfrastructureAvailable() != null) {
            registration.setItInfrastructureAvailable(request.getItInfrastructureAvailable());
        }
        if (request.getInfrastructureType() != null) {
            registration.setInfrastructureType(request.getInfrastructureType());
        }
        if (request.getSecretariatStaffAvailable() != null) {
            registration.setSecretariatStaffAvailable(request.getSecretariatStaffAvailable());
        }
        if (request.getWebsiteAvailable() != null) {
            registration.setWebsiteAvailable(request.getWebsiteAvailable());
        }
        if (request.getWebsiteUrl() != null) {
            registration.setWebsiteUrl(request.getWebsiteUrl());
        }
        if (request.getPaidServicesAvailable() != null) {
            registration.setPaidServicesAvailable(request.getPaidServicesAvailable());
        }
        if (request.getAdverseRemarksAvailable() != null) {
            registration.setAdverseRemarksAvailable(request.getAdverseRemarksAvailable());
        }
        if (request.getAdverseRemarks() != null) {
            registration.setAdverseRemarks(request.getAdverseRemarks());
        }
        if (request.getWebReport() != null) {
            registration.setWebReport(request.getWebReport());
        }
        if (request.getSelectionCriteria() != null) {
            registration.setSelectionCriteria(request.getSelectionCriteria());
        }
        if (request.getWillingnessComments() != null) {
            registration.setWillingnessComments(request.getWillingnessComments());
        }
        if (request.getWorkedWithSidbiBefore() != null) {
            registration.setWorkedWithSidbiBefore(request.getWorkedWithSidbiBefore());
        }
        if (request.getGrantProposed() != null) {
            registration.setGrantProposed(request.getGrantProposed());
        }
        if (request.getGrantDetails() != null) {
            registration.setGrantDetails(request.getGrantDetails());
        }
        if (request.getEnvisagedOutput() != null) {
            registration.setEnvisagedOutput(request.getEnvisagedOutput());
        }
        if (request.getEnvisagedOutcome() != null) {
            registration.setEnvisagedOutcome(request.getEnvisagedOutcome());
        }
        if (request.getEnvisagedImpact() != null) {
            registration.setEnvisagedImpact(request.getEnvisagedImpact());
        }
        if (request.getSde() != null) {
            registration.setSde(request.getSde());
        }
        if (request.getIsActive() != null) {
            registration.setIsActive(request.getIsActive());
        }
        if (request.getUpdatedBy() != null) {
            registration.setUpdatedBy(request.getUpdatedBy());
        }

        IndustryAssociationRegistration updated = repository.save(registration);
        log.info("Industry Association Registration updated successfully with UUID: {}", uuid);
        return convertToResponse(updated);
    }

    /**
     * Delete (soft delete) a registration by marking it inactive.
     *
     * @param uuid the unique identifier
     * @throws EntityNotFoundException if registration not found
     */
    public void deleteRegistration(String uuid) {
        log.info("Deleting (soft delete) Industry Association Registration with UUID: {}", uuid);
        UUID registrationUuid = UuidUtil.toUuid(uuid);
        IndustryAssociationRegistration registration = repository.findByUuid(registrationUuid)
                .orElseThrow(() -> {
                    log.error(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
                    return new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
                });

        registration.setIsActive(false);
        repository.save(registration);
        log.info("Industry Association Registration deleted successfully with UUID: {}", uuid);
    }

    /**
     * Approve or reject a registration by SIDBE.
     *
     * @param uuid the unique identifier of the registration
     * @param approvalRequest the approval request containing approval status
     * @param username the username of the user approving/rejecting
     * @return the updated registration response
     * @throws EntityNotFoundException if registration or user not found
     */
    public IndustryAssociationRegistrationResponse approveBySidbe(
            String uuid, ApprovalRequest approvalRequest, String username) {
        log.info("Processing SIDBE approval for registration with UUID: {} by user: {}", uuid, username);
        UUID registrationUuid = UuidUtil.toUuid(uuid);
        // Fetch the registration
        IndustryAssociationRegistration registration = repository.findByUuid(registrationUuid)
                .orElseThrow(() -> {
                    log.error(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
                    return new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
                });

        // Fetch the approving user
        User approver = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new EntityNotFoundException("User not found with username: " + username);
                });

        // Update approval fields
        registration.setIsSidbeApproved(approvalRequest.getIsSidbeApproved());
        registration.setSidbeApprovedByUser(approver);

        IndustryAssociationRegistration updated = repository.save(registration);
        log.info("SIDBE approval processed successfully for registration UUID: {} by user: {}", uuid, username);

        return convertToResponse(updated);
    }

    /**
     * Permanently delete a registration (hard delete).
     *
     * @param uuid the unique identifier
     * @throws EntityNotFoundException if registration not found
     */
    public void permanentlyDeleteRegistration(String uuid) {
        log.info("Permanently deleting Industry Association Registration with UUID: {}", uuid);

        if (!repository.existsById(uuid)) {
            log.error(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
            throw new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + uuid);
        }

        repository.deleteById(uuid);
        log.info("Industry Association Registration permanently deleted with UUID: {}", uuid);
    }

    /**
     * Convert IndustryAssociationRegistration entity to response DTO.
     *
     * @param registration the entity
     * @return the response DTO
     */
    private IndustryAssociationRegistrationResponse convertToResponse(IndustryAssociationRegistration registration) {
        return IndustryAssociationRegistrationResponse.builder()
                .uuid(registration.getUuid())
                .state(registration.getState())
                .industryAssociationName(registration.getIndustryAssociationName())
                .constitutionType(registration.getConstitutionType())
                .constitutionOther(registration.getConstitutionOther())
                .incorporationDate(registration.getIncorporationDate())
                .incorporationCertificate(registration.getIncorporationCertificate())
                .iaType(registration.getIaType())
                .constitutionProof(registration.getConstitutionProof())
                .district(registration.getDistrict())
                .pincode(registration.getPincode())
                .apexHolderName(registration.getApexHolderName())
                .apexHolderDesignation(registration.getApexHolderDesignation())
                .apexHolderMobile(registration.getApexHolderMobile())
                .apexHolderEmail(registration.getApexHolderEmail())
                .addressProofType(registration.getAddressProofType())
                .addressProof(registration.getAddressProof())
                .idProofType(registration.getIdProofType())
                .idProof(registration.getIdProof())
                .nodalName(registration.getNodalName())
                .nodalDesignation(registration.getNodalDesignation())
                .nodalMobile(registration.getNodalMobile())
                .nodalEmail(registration.getNodalEmail())
                .sidbiBranch(registration.getSidbiBranch())
                .mappedWithCluster(registration.getMappedWithCluster())
                .clusterName(registration.getClusterName())
                .mappedWithImportantDistrict(registration.getMappedWithImportantDistrict())
                .districtMsmeCount(registration.getDistrictMsmeCount())
                .activeMembersAbove200(registration.getActiveMembersAbove200())
                .activeMembersCount(registration.getActiveMembersCount())
                .justification(registration.getJustification())
                .approvalLetter(registration.getApprovalLetter())
                .msmeCountWithoutTraders(registration.getMsmeCountWithoutTraders())
                .memberDirectoryAvailable(registration.getMemberDirectoryAvailable())
                .buildingType(registration.getBuildingType())
                .declarationSigned(registration.getDeclarationSigned())
                .electricityBill(registration.getElectricityBill())
                .telephoneBill(registration.getTelephoneBill())
                .itInfrastructureAvailable(registration.getItInfrastructureAvailable())
                .infrastructureType(registration.getInfrastructureType())
                .secretariatStaffAvailable(registration.getSecretariatStaffAvailable())
                .websiteAvailable(registration.getWebsiteAvailable())
                .websiteUrl(registration.getWebsiteUrl())
                .paidServicesAvailable(registration.getPaidServicesAvailable())
                .adverseRemarksAvailable(registration.getAdverseRemarksAvailable())
                .adverseRemarks(registration.getAdverseRemarks())
                .webReport(registration.getWebReport())
                .selectionCriteria(registration.getSelectionCriteria())
                .willingnessComments(registration.getWillingnessComments())
                .workedWithSidbiBefore(registration.getWorkedWithSidbiBefore())
                .grantProposed(registration.getGrantProposed())
                .grantDetails(registration.getGrantDetails())
                .envisagedOutput(registration.getEnvisagedOutput())
                .envisagedOutcome(registration.getEnvisagedOutcome())
                .envisagedImpact(registration.getEnvisagedImpact())
                .sde(registration.getSde())
                .isSidbeApproved(registration.getIsSidbeApproved())
                .sidbeApprovedByUserId(registration.getSidbeApprovedByUser() != null ?
                        registration.getSidbeApprovedByUser().getId() : null)
                .sidbeApprovedByUsername(registration.getSidbeApprovedByUser() != null ?
                        registration.getSidbeApprovedByUser().getUsername() : null)
                .isActive(registration.getIsActive())
                .createdAt(registration.getCreatedAt())
                .updatedAt(registration.getUpdatedAt())
                .createdBy(registration.getCreatedBy())
                .updatedBy(registration.getUpdatedBy())
                .build();
    }
    /**
     * Retrieve registrations by state, district and SIDBI approval status.
     *
     * @param state state name
     * @param district district name
     * @param isSidbeApproved SIDBI approval status
     * @return list of matching registration responses
     */
    @Transactional
    public List<IndustryAssociationRegistrationResponse> getRegistrations(
            String state,
            String district,
            Boolean isSidbeApproved) {

        log.debug("Fetching Industry Association Registrations for state: {}, district: {}, approved: {}",
                state, district, isSidbeApproved);

        List<IndustryAssociationRegistration> registrations =
                repository.findAllByIsActiveTrueAndStateAndDistrictAndIsSidbeApproved(
                        state,
                        district,
                        isSidbeApproved);

        return registrations.stream()
                .map(this::convertToResponse)
                .toList();
    }
}
