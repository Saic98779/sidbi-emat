package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.repository.SidbiSdeRepository;
import org.emat.service.SidbiSdeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SidbiSdeServiceImpl implements SidbiSdeService {

    private final SidbiSdeRepository repository;

    @Override
    public List<SidbiSdeDropdownResponse> getDropdownByBranch(Long branchId) {
        return repository.findDropdownByBranchId(branchId);
    }
}
