package org.emat.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Participant details read from one Excel row, shared by the DIA Exposure Visit Participant and DIA
 * MSME Participant imports. Identifier fields are already normalized (PAN / Udyam upper-case, email
 * lower-case, contact number without spaces or separators).
 */
public record ParticipantImportRow(
        String msmeName,
        String designation,
        String gender,
        String fatherHusbandName,
        LocalDate dateOfBirth,
        String email,
        String contactNo,
        String panNo,
        String udyamAadharNo) {

    /** Identifier keys used to detect the same participant repeated within one file. */
    public List<String> identifierKeys() {
        List<String> keys = new ArrayList<>();
        if (panNo != null) keys.add("PAN:" + panNo);
        if (udyamAadharNo != null) keys.add("UDYAM:" + udyamAadharNo);
        if (email != null) keys.add("EMAIL:" + email);
        if (contactNo != null) keys.add("CONTACT:" + contactNo);
        return keys;
    }
}
