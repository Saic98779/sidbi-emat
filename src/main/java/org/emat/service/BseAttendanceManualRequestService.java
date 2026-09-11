package org.emat.service;

import java.util.List;
import org.emat.dto.BseAttendanceManualRequestDTO;

public interface BseAttendanceManualRequestService {

    BseAttendanceManualRequestDTO save(BseAttendanceManualRequestDTO dto);

    BseAttendanceManualRequestDTO update(Long id, BseAttendanceManualRequestDTO dto);

    BseAttendanceManualRequestDTO getById(Long id);

    List<BseAttendanceManualRequestDTO> getAll();

    List<BseAttendanceManualRequestDTO> getByRecommendation(Long recommendationId);

    void delete(Long id);

    List<BseAttendanceManualRequestDTO> getByApprovalStatus(Boolean isApproved);

    BseAttendanceManualRequestDTO approve(Long id, Long approvedBy);

    BseAttendanceManualRequestDTO reject(Long id, Long approvedBy);
}
