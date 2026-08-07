package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.BseAttendanceDTO;
import org.emat.entity.BseAttendance;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.repository.BseAttendanceRepository;
import org.emat.repository.IndustryAssociationBseRecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BseAttendanceService {

    private final BseAttendanceRepository attendanceRepository;
    private final IndustryAssociationBseRecommendationRepository recommendationRepository;

    public BseAttendanceDTO save(BseAttendanceDTO dto) {

        IndustryAssociationBseRecommendation recommendation =
                recommendationRepository.findById(dto.getBseRecommendationId())
                        .orElseThrow(() -> new RuntimeException("BSE Recommendation not found"));

        BseAttendance attendance = new BseAttendance();
        attendance.setBseRecommendation(recommendation);
        attendance.setAttendanceDate(dto.getAttendanceDate());
        attendance.setInTime(dto.getInTime());
        attendance.setOutTime(dto.getOutTime());

        attendance = attendanceRepository.save(attendance);

        return mapToDTO(attendance);
    }

    public BseAttendanceDTO update(UUID id, BseAttendanceDTO dto) {

        BseAttendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        IndustryAssociationBseRecommendation recommendation =
                recommendationRepository.findById(dto.getBseRecommendationId())
                        .orElseThrow(() -> new RuntimeException("BSE Recommendation not found"));

        attendance.setBseRecommendation(recommendation);
        attendance.setAttendanceDate(dto.getAttendanceDate());
        attendance.setInTime(dto.getInTime());
        attendance.setOutTime(dto.getOutTime());

        attendance = attendanceRepository.save(attendance);

        return mapToDTO(attendance);
    }

    public BseAttendanceDTO getById(UUID id) {

        return attendanceRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
    }

    public List<BseAttendanceDTO> getByRecommendation(UUID recommendationId) {

        return attendanceRepository.findByBseRecommendationUuid(recommendationId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<BseAttendanceDTO> getAll() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void delete(UUID id) {

        attendanceRepository.deleteById(id);
    }

    private BseAttendanceDTO mapToDTO(BseAttendance attendance) {

        return BseAttendanceDTO.builder()
                .uuid(attendance.getUuid())
                .bseRecommendationId(attendance.getBseRecommendation().getUuid())
                .attendanceDate(attendance.getAttendanceDate())
                .inTime(attendance.getInTime())
                .outTime(attendance.getOutTime())
                .build();
    }
}