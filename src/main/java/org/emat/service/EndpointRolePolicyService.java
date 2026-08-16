package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.EndpointRolePolicyRequest;
import org.emat.dto.EndpointRolePolicyResponse;
import org.emat.entity.EndpointRolePolicy;
import org.emat.enums.Role;
import org.emat.repository.EndpointRolePolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EndpointRolePolicyService {

    public static final String SIDBI_SDE = "sidbiSde";

    private final EndpointRolePolicyRepository repository;
    private final EndpointRolePolicyCacheService cacheService;

    @Transactional(readOnly = true)
    public List<EndpointRolePolicyResponse> getAllPolicies() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EndpointRolePolicyResponse getPolicy(String policyKey) {
        return cacheService.getPolicy(policyKey);
    }

    public EndpointRolePolicyResponse upsertPolicy(EndpointRolePolicyRequest request) {
        validatePolicyKey(request.policyKey());
        validateRoles(request.roles());

        EndpointRolePolicy policy = repository.findById(request.policyKey())
                .orElseGet(() -> EndpointRolePolicy.builder().policyKey(request.policyKey()).build());
        policy.setRolesCsv(String.join(",", request.roles()));
        policy.setDescription(request.description());
        policy.setUpdatedAt(LocalDateTime.now());

        EndpointRolePolicyResponse response = toResponse(repository.save(policy));
        cacheService.evict(request.policyKey());
        return response;
    }

    public void deletePolicy(String policyKey) {
        repository.deleteById(policyKey);
        cacheService.evict(policyKey);
    }

    public String[] resolveRoles(String policyKey) {
        return cacheService.getPolicy(policyKey).roles().toArray(String[]::new);
    }

    private void validatePolicyKey(String policyKey) {
        if (policyKey == null || policyKey.isBlank()) {
            throw new IllegalArgumentException("policyKey is required");
        }
    }

    private void validateRoles(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("roles must not be empty");
        }
        Set<String> validRoles = Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toSet());
        List<String> invalid = roles.stream().filter(role -> !validRoles.contains(role.toUpperCase(Locale.ROOT))).toList();
        if (!invalid.isEmpty()) {
            throw new IllegalArgumentException("Invalid roles: " + invalid);
        }
    }

    private EndpointRolePolicyResponse toResponse(EndpointRolePolicy policy) {
        List<String> roles = policy.getRolesCsv() == null || policy.getRolesCsv().isBlank()
                ? List.of()
                : Arrays.stream(policy.getRolesCsv().split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
        return new EndpointRolePolicyResponse(
                policy.getPolicyKey(),
                roles,
                policy.getDescription(),
                policy.getUpdatedAt()
        );
    }
}
