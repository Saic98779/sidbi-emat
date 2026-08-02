package org.emat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BranchDropdownResponse;
import org.emat.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchService {

    private final BranchRepository repository;

    public List<BranchDropdownResponse> getBranchDropdownByState(String state) {
        return repository.findBranchDropdownByState(state);
    }
}