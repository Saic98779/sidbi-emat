package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.EndpointRolePolicyRequest;
import org.emat.dto.EndpointRolePolicyResponse;
import org.emat.enums.Role;
import org.emat.service.EndpointRolePolicyCacheService;
import org.emat.service.EndpointRolePolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/endpoint-role-policies")
@RequiredArgsConstructor
public class EndpointRolePolicyController {

    private final EndpointRolePolicyService service;
    private final EndpointRolePolicyCacheService cacheService;

    @GetMapping
    public ResponseEntity<List<EndpointRolePolicyResponse>> getAll() {
        return ResponseEntity.ok(service.getAllPolicies());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getAvailableRoles() {
        return ResponseEntity.ok(Arrays.stream(Role.values()).map(Enum::name).toList());
    }

    @GetMapping("/{policyKey}")
    public ResponseEntity<EndpointRolePolicyResponse> getOne(@PathVariable String policyKey) {
        return ResponseEntity.ok(service.getPolicy(policyKey));
    }

    @PostMapping
    public ResponseEntity<EndpointRolePolicyResponse> upsert(@RequestBody EndpointRolePolicyRequest request) {
        return ResponseEntity.ok(service.upsertPolicy(request));
    }

    @PutMapping("/{policyKey}")
    public ResponseEntity<EndpointRolePolicyResponse> updateByPolicyKey(
            @PathVariable String policyKey,
            @RequestBody EndpointRolePolicyRequest request) {
        return ResponseEntity.ok(service.upsertPolicy(new EndpointRolePolicyRequest(
                policyKey,
                request.roles(),
                request.description()
        )));
    }

    @PostMapping("/refresh-cache")
    public ResponseEntity<Void> refreshCache() {
        cacheService.refreshCache();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{policyKey}")
    public ResponseEntity<Void> delete(@PathVariable String policyKey) {
        service.deletePolicy(policyKey);
        return ResponseEntity.noContent().build();
    }
}
