package org.emat.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointRolePolicyCacheConfig {

    public static final String ENDPOINT_ROLE_POLICY_CACHE = "endpointRolePolicies";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(ENDPOINT_ROLE_POLICY_CACHE);
    }
}
