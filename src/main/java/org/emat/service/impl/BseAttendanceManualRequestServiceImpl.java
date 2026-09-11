package org.emat.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.BseAttendanceManualRequestDTO;
import org.emat.entity.BseAttendance;
import org.emat.entity.BseAttendanceManualRequest;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.mapper.BseAttendanceManualRequestMapper;
import org.emat.repository.BseAttendanceManualRequestRepository;
import org.emat.repository.BseAttendanceRepository;
import org.emat.service.BseAttendanceManualRequestService;
import org.emat.validator.BseAttendanceManualRequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BseAttendanceManualRequestServiceImpl implements BseAttendanceManualRequestService {

    private final BseAttendanceRepository attendanceRepository;
    private final BseAttendanceManualRequestRepository repository;
    private final BseAttendanceManualRequestMapper bseAttendanceManualRequestMapper;
    private final BseAttendanceManualRequestValidator bseAttendanceManualRequestValidator;

    @Override
    public BseAttendanceManualRequestDTO save(BseAttendanceManualRequestDTO dto) {
        IndustryAssociationBseRecommendation recommendation =
                bseAttendanceManualRequestValidator.getRecommendationOrThrow(
                        dto.getBseRecommendationId());

        BseAttendanceManualRequest entity =
                bseAttendanceManualRequestMapper.toEntity(dto, recommendation);
        return bseAttendanceManualRequestMapper.toDto(repository.save(entity));
    }

    @Override
    public BseAttendanceManualRequestDTO update(Long id, BseAttendanceManualRequestDTO dto) {
        BseAttendanceManualRequest entity =
                bseAttendanceManualRequestValidator.getManualRequestOrThrowTitleCase(id);
        IndustryAssociationBseRecommendation recommendation =
                bseAttendanceManualRequestValidator.getRecommendationOrThrow(
                        dto.getBseRecommendationId());

        bseAttendanceManualRequestMapper.updateEntityFromRequest(dto, entity, recommendation);
        return bseAttendanceManualRequestMapper.toDto(repository.save(entity));
    }

    @Override
    public BseAttendanceManualRequestDTO getById(Long id) {
        return bseAttendanceManualRequestMapper.toDto(
                bseAttendanceManualRequestValidator.getManualRequestOrThrowTitleCase(id));
    }

    @Override
    public List<BseAttendanceManualRequestDTO> getAll() {
        return repository.findAll().stream().map(bseAttendanceManualRequestMapper::toDto).toList();
    }

    @Override
    public List<BseAttendanceManualRequestDTO> getByRecommendation(Long recommendationId) {
        return repository.findByBseRecommendationId(recommendationId).stream()
                .map(bseAttendanceManualRequestMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<BseAttendanceManualRequestDTO> getByApprovalStatus(Boolean isApproved) {
        return repository.findByIsApproved(isApproved).stream()
                .map(bseAttendanceManualRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BseAttendanceManualRequestDTO approve(Long id, Long approvedBy) {
        BseAttendanceManualRequest request =
                bseAttendanceManualRequestValidator.getManualRequestOrThrow(id);
        bseAttendanceManualRequestValidator.validateNotApproved(request);

        request.setIsApproved(true);
        request.setApprovedBy(approvedBy);
        request.setApprovedDate(LocalDateTime.now());
        repository.save(request);

        BseAttendance attendance = bseAttendanceManualRequestMapper.toAttendanceEntity(request);
        attendanceRepository.save(attendance);

        return bseAttendanceManualRequestMapper.toDto(request);
    }

    @Override
    public BseAttendanceManualRequestDTO reject(Long id, Long approvedBy) {
        BseAttendanceManualRequest entity =
                bseAttendanceManualRequestValidator.getManualRequestOrThrow(id);

        entity.setIsApproved(false);
        entity.setApprovedBy(approvedBy);
        entity.setApprovedDate(LocalDateTime.now());

        entity = repository.save(entity);
        return bseAttendanceManualRequestMapper.toDto(entity);
    }
}
