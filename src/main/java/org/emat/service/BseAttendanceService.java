package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.BseAttendanceDTO;
import org.emat.entity.BseAttendance;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.mapper.BseAttendanceMapper;
import org.emat.repository.BseAttendanceRepository;
import org.emat.validator.BseAttendanceValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BseAttendanceService {

    private final BseAttendanceRepository attendanceRepository;
    private final BseAttendanceMapper bseAttendanceMapper;
    private final BseAttendanceValidator bseAttendanceValidator;

    public BseAttendanceDTO save(BseAttendanceDTO dto) {
        IndustryAssociationBseRecommendation recommendation =
                bseAttendanceValidator.getRecommendationOrThrow(dto.getBseRecommendationId());

        BseAttendance attendance = bseAttendanceMapper.toEntity(dto, recommendation);

        attendance = attendanceRepository.save(attendance);

        return bseAttendanceMapper.toDto(attendance);
    }

    public BseAttendanceDTO update(Long id, BseAttendanceDTO dto) {
        BseAttendance attendance = bseAttendanceValidator.getAttendanceOrThrow(id);
        IndustryAssociationBseRecommendation recommendation =
                bseAttendanceValidator.getRecommendationOrThrow(dto.getBseRecommendationId());

        bseAttendanceMapper.updateEntityFromRequest(dto, attendance, recommendation);

        attendance = attendanceRepository.save(attendance);

        return bseAttendanceMapper.toDto(attendance);
    }

    public BseAttendanceDTO getById(Long id) {
        return bseAttendanceMapper.toDto(bseAttendanceValidator.getAttendanceOrThrow(id));
    }

    public List<BseAttendanceDTO> getByRecommendation(Long recommendationId) {
        return attendanceRepository.findByBseRecommendationId(recommendationId)
                .stream()
                .map(bseAttendanceMapper::toDto)
                .toList();
    }

    public List<BseAttendanceDTO> getAll() {
        return attendanceRepository.findAll()
                .stream()
                .map(bseAttendanceMapper::toDto)
                .toList();
    }

    public void delete(Long id) {
        attendanceRepository.deleteById(id);
    }
}