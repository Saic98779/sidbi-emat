package org.emat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.config.EndpointRolePolicyCacheConfig;
import org.emat.dto.EndpointRolePolicyResponse;
import org.emat.repository.EndpointRolePolicyRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EndpointRolePolicyBootstrapService {

    private final EndpointRolePolicyRepository repository;
    private final CacheManager cacheManager;

    @PostConstruct
    @Transactional(readOnly = true)
    public void preloadPolicies() {
        Cache cache = cacheManager.getCache(EndpointRolePolicyCacheConfig.ENDPOINT_ROLE_POLICY_CACHE);
        if (cache == null) {
            log.warn("Endpoint role policy cache is not available");
            return;
        }

        repository.findAll().forEach(policy -> {
            EndpointRolePolicyResponse response = new EndpointRolePolicyResponse(
                    policy.getPolicyKey(),
                    policy.getRolesCsv() == null || policy.getRolesCsv().isBlank()
                            ? List.of()
                            : java.util.Arrays.stream(policy.getRolesCsv().split(","))
                            .map(String::trim)
                            .filter(role -> !role.isBlank())
                            .toList(),
                    policy.getDescription(),
                    policy.getUpdatedAt()
            );
            cache.put(policy.getPolicyKey(), response);
            log.info("Preloaded endpoint role policy into cache: {}", policy.getPolicyKey());
        });
    }
}
