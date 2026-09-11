package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BranchDropdownResponse;
import org.emat.repository.BranchRepository;
import org.emat.service.BranchService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository repository;

    @Override
    public List<BranchDropdownResponse> getBranchDropdownByState(String state) {
        return repository.findBranchDropdownByState(state);
    }
}
