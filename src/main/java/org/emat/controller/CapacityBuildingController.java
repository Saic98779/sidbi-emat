package org.emat.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CapacityBuildingResponse;
import org.emat.service.CapacityBuildingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/capacity-building")
@RequiredArgsConstructor
@Slf4j
public class CapacityBuildingController {

    private final CapacityBuildingService service;

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('capacityBuildingRead'))")
    public ResponseEntity<ApiResponse<List<CapacityBuildingResponse>>> getAll(
            @RequestParam(value = "fromDate", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate fromDate,
            @RequestParam(value = "toDate", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate toDate,
            @RequestParam(value = "source", required = false) String source) {
        log.info("Received request to fetch capacity building report");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Capacity building report fetched successfully",
                        service.getAll(fromDate, toDate, source)));
    }
}
