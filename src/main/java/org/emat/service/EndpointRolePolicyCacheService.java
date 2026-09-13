package org.emat.service;

import org.emat.dto.EndpointRolePolicyResponse;

public interface EndpointRolePolicyCacheService {

    EndpointRolePolicyResponse getPolicy(String policyKey);

    void refreshCache();

    void evict(String policyKey);
}
