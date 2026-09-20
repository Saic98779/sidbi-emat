package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.Status;

/** DTO for updating the status of an existing E-learning Module Content. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateElearningModuleContentStatusRequest {

    private Status status;
}