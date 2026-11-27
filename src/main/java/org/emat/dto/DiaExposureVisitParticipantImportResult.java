package org.emat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** Result summary of a DIA Exposure Visit Participant Excel import. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaExposureVisitParticipantImportResult {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long diaExposureVisitId;

    /** Number of non-empty data rows in the Excel file. */
    private int totalRecords;

    private int importedCount;

    /** Participants already linked to the visit, or repeated within the same file. */
    private int skippedCount;

    private int invalidCount;

    private List<DiaExposureVisitParticipantImportRowResult> imported;

    private List<DiaExposureVisitParticipantImportRowResult> skipped;

    private List<DiaExposureVisitParticipantImportRowResult> invalid;
}
