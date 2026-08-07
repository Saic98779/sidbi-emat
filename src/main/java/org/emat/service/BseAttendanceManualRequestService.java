    package org.emat.service;

    import lombok.RequiredArgsConstructor;
    import org.emat.dto.BseAttendanceManualRequestDTO;
    import org.emat.entity.BseAttendance;
    import org.emat.entity.BseAttendanceManualRequest;
    import org.emat.entity.IndustryAssociationBseRecommendation;
    import org.emat.repository.BseAttendanceManualRequestRepository;
    import org.emat.repository.BseAttendanceRepository;
    import org.emat.repository.IndustryAssociationBseRecommendationRepository;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.UUID;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class BseAttendanceManualRequestService {

        private final BseAttendanceRepository attendanceRepository;
        private final BseAttendanceManualRequestRepository repository;
        private final IndustryAssociationBseRecommendationRepository recommendationRepository;

        public BseAttendanceManualRequestDTO save(BseAttendanceManualRequestDTO dto) {

            IndustryAssociationBseRecommendation recommendation =
                    recommendationRepository.findById(dto.getBseRecommendationId())
                            .orElseThrow(() -> new RuntimeException("BSE Recommendation not found"));

            BseAttendanceManualRequest entity = new BseAttendanceManualRequest();
            entity.setBseRecommendation(recommendation);
            entity.setAttendanceDate(dto.getAttendanceDate());
            entity.setInTime(dto.getInTime());
            entity.setOutTime(dto.getOutTime());
            entity.setReason(dto.getReason());
            entity.setIsApproved(dto.getIsApproved());
            entity.setApprovedDate(dto.getApprovedDate());
            entity.setApprovedBy(dto.getApprovedBy());

            return mapToDTO(repository.save(entity));
        }

        public BseAttendanceManualRequestDTO update(UUID id, BseAttendanceManualRequestDTO dto) {

            BseAttendanceManualRequest entity = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manual Request not found"));

            IndustryAssociationBseRecommendation recommendation =
                    recommendationRepository.findById(dto.getBseRecommendationId())
                            .orElseThrow(() -> new RuntimeException("BSE Recommendation not found"));

            entity.setBseRecommendation(recommendation);
            entity.setAttendanceDate(dto.getAttendanceDate());
            entity.setInTime(dto.getInTime());
            entity.setOutTime(dto.getOutTime());
            entity.setReason(dto.getReason());
            entity.setIsApproved(dto.getIsApproved());
            entity.setApprovedDate(dto.getApprovedDate());
            entity.setApprovedBy(dto.getApprovedBy());

            return mapToDTO(repository.save(entity));
        }

        public BseAttendanceManualRequestDTO getById(UUID id) {

            return repository.findById(id)
                    .map(this::mapToDTO)
                    .orElseThrow(() -> new RuntimeException("Manual Request not found"));
        }

        public List<BseAttendanceManualRequestDTO> getAll() {

            return repository.findAll()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        public List<BseAttendanceManualRequestDTO> getByRecommendation(UUID recommendationId) {

            return repository.findByBseRecommendationUuid(recommendationId)
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        public void delete(UUID id) {
            repository.deleteById(id);
        }

        public List<BseAttendanceManualRequestDTO> getByApprovalStatus(Boolean isApproved) {

            return repository.findByIsApproved(isApproved)
                    .stream()
                    .map(this::mapToDTO)
                    .toList();
        }

        @Transactional
        public BseAttendanceManualRequestDTO approve(UUID id, UUID approvedBy) {

            BseAttendanceManualRequest request = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manual request not found"));

            if (Boolean.TRUE.equals(request.getIsApproved())) {
                throw new RuntimeException("Request is already approved");
            }

            // Approve the request
            request.setIsApproved(true);
            request.setApprovedBy(approvedBy);
            request.setApprovedDate(LocalDateTime.now());

            repository.save(request);

            // Fetch Recommendation
            IndustryAssociationBseRecommendation recommendation =
                    recommendationRepository.findById(request.getBseRecommendation().getUuid())
                            .orElseThrow(() -> new RuntimeException("BSE Recommendation not found"));

            // Create Attendance
            BseAttendance attendance = new BseAttendance();
            attendance.setBseRecommendation(recommendation);
            attendance.setAttendanceDate(request.getAttendanceDate());
            attendance.setInTime(request.getInTime());
            attendance.setOutTime(request.getOutTime());

            attendanceRepository.save(attendance);

            return mapToDTO(request);
        }

        public BseAttendanceManualRequestDTO reject(UUID id, UUID approvedBy) {

            BseAttendanceManualRequest entity = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manual request not found"));

            entity.setIsApproved(false);
            entity.setApprovedBy(approvedBy);
            entity.setApprovedDate(LocalDateTime.now());

            entity = repository.save(entity);

            return mapToDTO(entity);
        }

        private BseAttendanceManualRequestDTO mapToDTO(BseAttendanceManualRequest entity) {

            return BseAttendanceManualRequestDTO.builder()
                    .uuid(entity.getUuid())
                    .bseRecommendationId(entity.getBseRecommendation().getUuid())
                    .attendanceDate(entity.getAttendanceDate())
                    .inTime(entity.getInTime())
                    .outTime(entity.getOutTime())
                    .reason(entity.getReason())
                    .isApproved(entity.getIsApproved())
                    .approvedDate(entity.getApprovedDate())
                    .approvedBy(entity.getApprovedBy())
                    .build();
        }
    }