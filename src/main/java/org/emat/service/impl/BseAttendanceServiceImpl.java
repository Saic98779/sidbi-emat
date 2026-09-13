package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.BseAttendanceDTO;
import org.emat.entity.BseAttendance;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.mapper.BseAttendanceMapper;
import org.emat.repository.BseAttendanceRepository;
import org.emat.service.BseAttendanceService;
import org.emat.validator.BseAttendanceValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BseAttendanceServiceImpl implements BseAttendanceService {

    private final BseAttendanceRepository attendanceRepository;
    private final BseAttendanceMapper bseAttendanceMapper;
    private final BseAttendanceValidator bseAttendanceValidator;

    @Override
    public BseAttendanceDTO save(BseAttendanceDTO dto) {
        IndustryAssociationBseRecommendation recommendation =
                bseAttendanceValidator.getRecommendationOrThrow(dto.getBseRecommendationId());

        BseAttendance attendance = bseAttendanceMapper.toEntity(dto, recommendation);

        attendance = attendanceRepository.save(attendance);

        return bseAttendanceMapper.toDto(attendance);
    }

    @Override
    public BseAttendanceDTO update(Long id, BseAttendanceDTO dto) {
        BseAttendance attendance = bseAttendanceValidator.getAttendanceOrThrow(id);
        IndustryAssociationBseRecommendation recommendation =
                bseAttendanceValidator.getRecommendationOrThrow(dto.getBseRecommendationId());

        bseAttendanceMapper.updateEntityFromRequest(dto, attendance, recommendation);

        attendance = attendanceRepository.save(attendance);

        return bseAttendanceMapper.toDto(attendance);
    }

    @Override
    public BseAttendanceDTO getById(Long id) {
        return bseAttendanceMapper.toDto(bseAttendanceValidator.getAttendanceOrThrow(id));
    }

    @Override
    public List<BseAttendanceDTO> getByRecommendation(Long recommendationId) {
        return attendanceRepository.findByBseRecommendationId(recommendationId).stream()
                .map(bseAttendanceMapper::toDto)
                .toList();
    }

    @Override
    public List<BseAttendanceDTO> getAll() {
        return attendanceRepository.findAll().stream().map(bseAttendanceMapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        attendanceRepository.deleteById(id);
    }
}
