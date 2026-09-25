package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.Status;

/** DTO for updating the status of an existing BDS Service Providers Onboarding. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBdsServiceProvidersOnboardingStatusRequest {

    private Status makerStatus;
    private Status checkerStatus;

    private String remark;
}