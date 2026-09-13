package org.emat.service.impl;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.config.EndpointRolePolicyCacheConfig;
import org.emat.dto.EndpointRolePolicyResponse;
import org.emat.entity.EndpointRolePolicy;
import org.emat.repository.EndpointRolePolicyRepository;
import org.emat.service.EndpointRolePolicyCacheService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EndpointRolePolicyCacheServiceImpl implements EndpointRolePolicyCacheService {

    private final EndpointRolePolicyRepository repository;
    private final CacheManager cacheManager;

    @Override
    @Transactional(readOnly = true)
    public EndpointRolePolicyResponse getPolicy(String policyKey) {
        Cache cache =
                cacheManager.getCache(EndpointRolePolicyCacheConfig.ENDPOINT_ROLE_POLICY_CACHE);
        if (cache == null) {
            return loadPolicy(policyKey);
        }

        EndpointRolePolicyResponse cached = cache.get(policyKey, EndpointRolePolicyResponse.class);
        if (cached != null) {
            return cached;
        }

        EndpointRolePolicyResponse loaded = loadPolicy(policyKey);
        cache.put(policyKey, loaded);
        return loaded;
    }

    @Override
    @Transactional
    public void refreshCache() {
        Cache cache =
                cacheManager.getCache(EndpointRolePolicyCacheConfig.ENDPOINT_ROLE_POLICY_CACHE);
        if (cache == null) {
            return;
        }

        cache.clear();
        repository
                .findAll()
                .forEach(policy -> cache.put(policy.getPolicyKey(), toResponse(policy)));
    }

    @Override
    public void evict(String policyKey) {
        Cache cache =
                cacheManager.getCache(EndpointRolePolicyCacheConfig.ENDPOINT_ROLE_POLICY_CACHE);
        if (cache != null) {
            cache.evict(policyKey);
        }
    }

    private EndpointRolePolicyResponse loadPolicy(String policyKey) {
        EndpointRolePolicy policy =
                repository
                        .findById(policyKey)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "Policy not found: " + policyKey));
        return toResponse(policy);
    }

    private EndpointRolePolicyResponse toResponse(EndpointRolePolicy policy) {
        List<String> roles =
                policy.getRolesCsv() == null || policy.getRolesCsv().isBlank()
                        ? List.of()
                        : Arrays.stream(policy.getRolesCsv().split(","))
                                .map(String::trim)
                                .filter(s -> !s.isBlank())
                                .toList();
        return new EndpointRolePolicyResponse(
                policy.getPolicyKey(), roles, policy.getDescription(), policy.getUpdatedAt());
    }
}
