package org.emat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** Result summary of a DIA MSME Participant Excel import. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaMsmeParticipantImportResult {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long diaCapacityBuildingProgramId;

    /** Number of non-empty data rows in the Excel file. */
    private int totalRecords;

    private int importedCount;

    /** Participants already linked to the program, or repeated within the same file. */
    private int skippedCount;

    private int invalidCount;

    private List<DiaMsmeParticipantImportRowResult> imported;

    private List<DiaMsmeParticipantImportRowResult> skipped;

    private List<DiaMsmeParticipantImportRowResult> invalid;
}
