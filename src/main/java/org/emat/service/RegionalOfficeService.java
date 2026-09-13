package org.emat.service;

import java.util.List;
import org.emat.dto.RegionalOfficeResponse;

public interface RegionalOfficeService {
    /** Get all active Regional Offices. */
    List<RegionalOfficeResponse> getAllRegionalOffices();
}
