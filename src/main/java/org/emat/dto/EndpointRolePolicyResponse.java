package org.emat.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EndpointRolePolicyResponse(
        String policyKey,
        List<String> roles,
        String description,
        LocalDateTime updatedAt
) {
}

