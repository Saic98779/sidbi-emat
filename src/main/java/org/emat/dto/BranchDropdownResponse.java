package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

@Data
@NoArgsConstructor
public class BranchDropdownResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;
    private String branchName;

    public BranchDropdownResponse(Long id, String branchName) {
        this.id = id;
        this.branchName = branchName;
    }
}
