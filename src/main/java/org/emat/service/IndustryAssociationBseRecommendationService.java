package org.emat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BseRecommendationResponse;
import org.emat.dto.CreateBseRecommendationRequest;
import org.emat.dto.UpdateBseRecommendationRequest;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.User;
import org.emat.mapper.IndustryAssociationBseRecommendationMapper;
import org.emat.repository.IndustryAssociationBseRecommendationRepository;
import org.emat.util.CommonUtil;
import org.emat.validator.IndustryAssociationBseRecommendationValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndustryAssociationBseRecommendationService {

    private final IndustryAssociationBseRecommendationRepository bseRecommendationRepository;
    private final IndustryAssociationBseRecommendationMapper bseRecommendationMapper;
    private final IndustryAssociationBseRecommendationValidator bseRecommendationValidator;
    private final CommonUtil commonUtil;

    @Transactional
    public BseRecommendationResponse createBseRecommendation(CreateBseRecommendationRequest request) {
        log.info("Creating BSE recommendation for registration: {}", request.getRegistrationId());

        IndustryAssociationRegistration registration =
                bseRecommendationValidator.getRegistrationOrThrow(request.getRegistrationId());
        User user = bseRecommendationValidator.getUserOrNull(request.getUserId());

        IndustryAssociationBseRecommendation bseRecommendation = bseRecommendationMapper.toEntity(
                request,
                registration,
                user,
                commonUtil.getCurrentUsername());

        IndustryAssociationBseRecommendation saved = bseRecommendationRepository.save(bseRecommendation);
        log.info("BSE recommendation created successfully with ID: {}", saved.getId());

        return bseRecommendationMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getAllBseRecommendations() {
        log.info("Fetching all BSE recommendations");
        return bseRecommendationRepository.findByIsActiveTrue()
                .stream()
                .map(bseRecommendationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BseRecommendationResponse getBseRecommendationById(Long id) {
        log.info("Fetching BSE recommendation with ID: {}", id);
        IndustryAssociationBseRecommendation bseRecommendation =
                bseRecommendationValidator.getActiveRecommendationOrThrow(id);
        return bseRecommendationMapper.toResponse(bseRecommendation);
    }

    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getBseRecommendationsByRegistration(Long registrationId) {
        log.info("Fetching BSE recommendations for registration: {}", registrationId);
        return bseRecommendationRepository.findByRegistrationId(registrationId)
                .stream()
                .map(bseRecommendationMapper::toResponse)
                .toList();
    }

    @Transactional
    public BseRecommendationResponse updateBseRecommendation(Long id, UpdateBseRecommendationRequest request) {
        log.info("Updating BSE recommendation with ID: {}", id);

        IndustryAssociationBseRecommendation bseRecommendation =
                bseRecommendationValidator.getActiveRecommendationOrThrow(id);
        User user = bseRecommendationValidator.getUserOrNull(request.getUserId());

        bseRecommendationMapper.applyUpdateRequest(
                request,
                bseRecommendation,
                user,
                commonUtil.getCurrentUsername());

        IndustryAssociationBseRecommendation updated = bseRecommendationRepository.save(bseRecommendation);
        log.info("BSE recommendation updated successfully with ID: {}", id);

        return bseRecommendationMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> searchByBseName(String bseName) {
        log.info("Searching BSE recommendations by name: {}", bseName);
        return bseRecommendationRepository.findByBseNameContainingIgnoreCaseAndIsActiveTrue(bseName)
                .stream()
                .map(bseRecommendationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getByGtRecommendation(boolean isRecommended) {
        List<IndustryAssociationBseRecommendation> list = isRecommended
                ? bseRecommendationRepository.findByGtRecommendationIsNotNullAndIsActiveTrue()
                : bseRecommendationRepository.findByGtRecommendationIsNullAndIsActiveTrue();
        return list.stream().map(bseRecommendationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getByPmuRecommendation(boolean isRecommended) {
        List<IndustryAssociationBseRecommendation> list = isRecommended
                ? bseRecommendationRepository.findByPmuRecommendationIsNotNullAndIsActiveTrue()
                : bseRecommendationRepository.findByPmuRecommendationIsNullAndIsActiveTrue();
        return list.stream().map(bseRecommendationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getByHoRecommendation(boolean isRecommended) {
        List<IndustryAssociationBseRecommendation> list = isRecommended
                ? bseRecommendationRepository.findByHoRecommendationIsNotNullAndIsActiveTrue()
                : bseRecommendationRepository.findByHoRecommendationIsNullAndIsActiveTrue();
        return list.stream().map(bseRecommendationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getByMappedStatus(Boolean iaMapped) {
        return bseRecommendationRepository.findByIaMappedAndIsActiveTrue(iaMapped)
                .stream()
                .map(bseRecommendationMapper::toResponse)
                .toList();
    }

    public List<BseRecommendationResponse> getSelectedBseByVendor(Long userId) {
        return bseRecommendationRepository
                .findByUserIdAndIaSelectedTrueAndIsActiveTrue(userId)
                .stream()
                .map(bseRecommendationMapper::toResponse)
                .toList();
    }
}
