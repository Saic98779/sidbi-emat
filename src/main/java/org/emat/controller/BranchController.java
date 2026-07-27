package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.BranchDropdownResponse;
import org.emat.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/dropdown")
    public ResponseEntity<List<BranchDropdownResponse>> getBranchDropdown(
            @RequestParam String state) {

        return ResponseEntity.ok(branchService.getBranchDropdownByState(state));
    }
}
