package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for SIDBE approval request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequest {
    private Boolean isSidbeApproved;
}

