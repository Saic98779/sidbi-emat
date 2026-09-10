package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.EndpointRolePolicyRequest;
import org.emat.dto.EndpointRolePolicyResponse;
import org.emat.enums.Role;
import org.emat.service.EndpointRolePolicyCacheService;
import org.emat.service.EndpointRolePolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/endpoint-role-policies")
@RequiredArgsConstructor
public class EndpointRolePolicyController {

    private final EndpointRolePolicyService service;
    private final EndpointRolePolicyCacheService cacheService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EndpointRolePolicyResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Policies fetched successfully", service.getAllPolicies()));
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<String>>> getAvailableRoles() {
        return ResponseEntity.ok(ApiResponse.success("Roles fetched successfully", Arrays.stream(Role.values()).map(Enum::name).toList()));
    }

    @GetMapping("/{policyKey}")
    public ResponseEntity<ApiResponse<EndpointRolePolicyResponse>> getOne(@PathVariable String policyKey) {
        return ResponseEntity.ok(ApiResponse.success("Policy fetched successfully", service.getPolicy(policyKey)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EndpointRolePolicyResponse>> upsert(@RequestBody EndpointRolePolicyRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Policy upserted successfully", service.upsertPolicy(request)));
    }

    @PutMapping("/{policyKey}")
    public ResponseEntity<ApiResponse<EndpointRolePolicyResponse>> updateByPolicyKey(
            @PathVariable String policyKey,
            @RequestBody EndpointRolePolicyRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Policy updated successfully", service.upsertPolicy(new EndpointRolePolicyRequest(
                policyKey,
                request.roles(),
                request.description()
        ))));
    }

    @PostMapping("/refresh-cache")
    public ResponseEntity<ApiResponse<Void>> refreshCache() {
        cacheService.refreshCache();
        return ResponseEntity.ok(ApiResponse.success("Cache refreshed successfully", null));
    }

    @DeleteMapping("/{policyKey}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String policyKey) {
        service.deletePolicy(policyKey);
        return ResponseEntity.ok(ApiResponse.success("Policy deleted successfully", null));
    }
}
