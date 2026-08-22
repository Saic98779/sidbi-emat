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
import org.emat.mapper.IndustryAssociationRegistrationMapper;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.UserRepository;
import org.emat.util.CommonUtil;
import org.emat.validator.IndustryAssociationRegistrationValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IndustryAssociationRegistrationService {

    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration not found with ID: ";
    private static final String USER_NOT_FOUND_WITH_USERNAME_MESSAGE = "User not found with username: ";

    private final IndustryAssociationRegistrationRepository repository;
    private final UserRepository userRepository;
    private final IndustryAssociationRegistrationMapper registrationMapper;
    private final CommonUtil commonUtil;
    private final IndustryAssociationRegistrationValidator registrationValidator;

    public IndustryAssociationRegistrationResponse createRegistration(
            CreateIndustryAssociationRegistrationRequest request) {
        log.info("Creating new Industry Association Registration for: {}", request.getIndustryAssociationName());
        registrationValidator.validateCreateRequest(request);

        boolean isSidbiSdeCaller = commonUtil.isCurrentUserSidbiSde();
        User sidbiApprover = commonUtil.resolveSidbiApprover(request.getSidbeApprovedByUserId(), isSidbiSdeCaller);

        IndustryAssociationRegistration registration = registrationMapper.toEntity(
                request,
                isSidbiSdeCaller,
                sidbiApprover);

        IndustryAssociationRegistration saved = repository.save(registration);
        log.info("Industry Association Registration created successfully with ID: {}", saved.getId());
        return registrationMapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    public IndustryAssociationRegistrationResponse getRegistrationById(Long id) {
        log.debug("Fetching Industry Association Registration with ID: {}", id);
        IndustryAssociationRegistration registration = getRegistrationOrThrow(id);
        return registrationMapper.toResponse(registration);
    }

    @Transactional(readOnly = true)
    public List<IndustryAssociationRegistrationResponse> getAllRegistrations() {
        log.debug("Fetching all active Industry Association Registrations");
        return repository.findAllByIsActiveTrue().stream()
                .map(registrationMapper::toResponse)
                .toList();
    }

    public IndustryAssociationRegistrationResponse updateRegistration(
            Long id, UpdateIndustryAssociationRegistrationRequest request) {
        log.info("Updating Industry Association Registration with ID: {}", id);
        IndustryAssociationRegistration registration = getRegistrationOrThrow(id);

        registrationMapper.applyUpdateRequest(registration, request);

        IndustryAssociationRegistration updated = repository.save(registration);
        log.info("Industry Association Registration updated successfully with ID: {}", id);
        return registrationMapper.toResponse(updated);
    }

    public void deleteRegistration(Long id) {
        log.info("Deleting (soft delete) Industry Association Registration with ID: {}", id);
        IndustryAssociationRegistration registration = getRegistrationOrThrow(id);

        registration.setIsActive(false);
        repository.save(registration);
        log.info("Industry Association Registration deleted successfully with ID: {}", id);
    }

    public IndustryAssociationRegistrationResponse approveBySidbe(
            Long id, ApprovalRequest approvalRequest, String username) {
        log.info("Processing SIDBE approval for registration with ID: {} by user: {}", id, username);
        IndustryAssociationRegistration registration = getRegistrationOrThrow(id);

        User approver = getUserByUsernameOrThrow(username);

        registration.setIsSidbeApproved(approvalRequest.getIsSidbeApproved());
        registration.setSidbeApprovedByUser(approver);

        IndustryAssociationRegistration updated = repository.save(registration);
        log.info("SIDBE approval processed successfully for registration ID: {} by user: {}", id, username);

        return registrationMapper.toResponse(updated);
    }

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
                .map(registrationMapper::toResponse)
                .toList();
    }

    private IndustryAssociationRegistration getRegistrationOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error(REGISTRATION_NOT_FOUND_MESSAGE + id);
                    return new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + id);
                });
    }


    private User getUserByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new EntityNotFoundException(USER_NOT_FOUND_WITH_USERNAME_MESSAGE + username);
                });
    }
}
