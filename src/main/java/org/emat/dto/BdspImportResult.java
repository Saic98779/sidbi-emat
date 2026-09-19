package org.emat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Result summary of a BDSP Excel import. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BdspImportResult {

    private int totalRows;

    private int successCount;

    private int failureCount;

    private List<BdspImportRowResult> rows;
}