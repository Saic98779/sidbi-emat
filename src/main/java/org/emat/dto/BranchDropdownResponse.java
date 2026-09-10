package org.emat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchDropdownResponse {

    private Long id;
    private String branchName;

    public BranchDropdownResponse(Long id, String branchName) {
        this.id = id;
        this.branchName = branchName;
    }
}