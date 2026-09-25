package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.Status;

/** DTO for updating the status of an existing DIA 3C Info Series. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDia3CInfoSeriesStatusRequest {

    private Status makerStatus;
    private Status checkerStatus;

    private String remark;
}