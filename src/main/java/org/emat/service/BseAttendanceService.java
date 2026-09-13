package org.emat.service;

import java.util.List;
import org.emat.dto.BseAttendanceDTO;

public interface BseAttendanceService {

    BseAttendanceDTO save(BseAttendanceDTO dto);

    BseAttendanceDTO update(Long id, BseAttendanceDTO dto);

    BseAttendanceDTO getById(Long id);

    List<BseAttendanceDTO> getByRecommendation(Long recommendationId);

    List<BseAttendanceDTO> getAll();

    void delete(Long id);
}
