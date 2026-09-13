package org.emat.service;

import java.util.List;
import org.emat.dto.SidbiSdeDropdownResponse;

public interface SidbiSdeService {
    List<SidbiSdeDropdownResponse> getDropdownByBranch(Long branchId);
}
