package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Per-row result of a BDSP Excel import. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BdspImportRowResult {

    private int rowNumber;

    private boolean success;

    private String errorMessage;

    private Long bdspId;
}