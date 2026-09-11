package org.emat.service;

import java.util.List;
import org.emat.dto.BranchDropdownResponse;

public interface BranchService {
    List<BranchDropdownResponse> getBranchDropdownByState(String state);
}
