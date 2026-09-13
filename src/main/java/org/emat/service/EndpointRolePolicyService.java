package org.emat.service;

import java.util.List;
import org.emat.dto.EndpointRolePolicyRequest;
import org.emat.dto.EndpointRolePolicyResponse;

public interface EndpointRolePolicyService {

    String SIDBI_SDE = "sidbiSde";

    List<EndpointRolePolicyResponse> getAllPolicies();

    EndpointRolePolicyResponse getPolicy(String policyKey);

    EndpointRolePolicyResponse upsertPolicy(EndpointRolePolicyRequest request);

    void deletePolicy(String policyKey);

    String[] resolveRoles(String policyKey);
}
