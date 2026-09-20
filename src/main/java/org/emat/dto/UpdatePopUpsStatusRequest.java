package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.Status;

/** DTO for updating the status of an existing Pop-Ups record. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePopUpsStatusRequest {

    private Status status;
}