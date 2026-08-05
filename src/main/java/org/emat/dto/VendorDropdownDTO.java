package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDropdownDTO {

    private UUID uuid;
    private String vendorId;
    private String vendorName;
}