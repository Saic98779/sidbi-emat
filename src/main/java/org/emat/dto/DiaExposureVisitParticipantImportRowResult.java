package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** Per-row result of a DIA Exposure Visit Participant Excel import. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaExposureVisitParticipantImportRowResult {

    /** Outcome of processing a single Excel row. */
    public enum Status {
        IMPORTED,
        SKIPPED,
        INVALID
    }

    /** 1-based Excel row number. */
    private int rowNumber;

    private Status status;

    private String message;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long participantId;

    private String msmeName;
}
