package org.emat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class BranchDropdownResponse {

    private UUID uuid;
    private String branchName;

    public BranchDropdownResponse(UUID uuid, String branchName) {
        this.uuid = uuid;
        this.branchName = branchName;
    }
}