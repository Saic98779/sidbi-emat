package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.Status;

/** DTO for updating the status of an existing Disbursement Note for Capacity Building of IA members. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDisbursementNoteCapacityBuildingIaStatusRequest {

    private Status status;
}