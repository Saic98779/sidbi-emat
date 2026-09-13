package org.emat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.repository.SidbiSdeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SidbiSdeService {

    private final SidbiSdeRepository repository;

    public List<SidbiSdeDropdownResponse> getDropdownByBranch(Long branchId) {
        return repository.findDropdownByBranchId(branchId);
    }
}