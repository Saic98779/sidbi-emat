package org.emat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.repository.RegionalOfficeRepository;
import org.emat.repository.SidbiSdeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SidbiSdeService {

    private final SidbiSdeRepository repository;

    public List<SidbiSdeDropdownResponse> getDropdownByBranch(UUID branchUuid) {
        return repository.findDropdownByBranchUuid(branchUuid);
    }
}