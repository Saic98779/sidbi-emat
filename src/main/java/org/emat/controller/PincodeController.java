package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.service.PincodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pincodes")
@RequiredArgsConstructor
public class PincodeController {

    private final PincodeService pincodeService;

    @GetMapping("/states")
    public ResponseEntity<ApiResponse<List<String>>> getStates() {
        return ResponseEntity.ok(
                ApiResponse.success("States fetched successfully", pincodeService.getStates()));
    }

    @GetMapping("/districts")
    public ResponseEntity<ApiResponse<List<String>>> getDistricts(
            @RequestParam(required = false) String state) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Districts fetched successfully", pincodeService.getDistricts(state)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<String>>> getPincodes(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String district) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pincodes fetched successfully",
                        pincodeService.getPincodes(state, district)));
    }
}
