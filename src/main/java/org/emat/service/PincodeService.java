package org.emat.service;

import java.util.List;

public interface PincodeService {
    List<String> getStates();

    List<String> getDistricts(String state);

    List<String> getPincodes(String state, String district);
}
