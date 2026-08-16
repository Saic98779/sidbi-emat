package org.emat.dto;

import java.util.List;

public record EndpointRolePolicyRequest(
        String policyKey,
        List<String> roles,
        String description
) {
}

