package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.repository.PincodeMasterRepository;
import org.emat.service.PincodeService;
import org.emat.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PincodeServiceImpl implements PincodeService {

    private final PincodeMasterRepository repository;
    private final CommonUtil commonUtil;

    @Override
    public List<String> getStates() {
        return repository.findDistinctStates();
    }

    @Override
    public List<String> getDistricts(String state) {
        return repository.findDistinctDistrictsByState(resolveState(state));
    }

    @Override
    public List<String> getPincodes(String state, String district) {
        return repository.findPincodesByStateAndDistrict(resolveState(state), resolveDistrict(district));
    }

    private String resolveState(String state) {
        if (StringUtils.hasText(state)) {
            return state.trim();
        }
        return commonUtil
                .resolveCurrentUser()
                .map(user -> user.getState())
                .filter(StringUtils::hasText)
                .map(String::trim)
                .orElseThrow(() -> new IllegalArgumentException(
                        "State is required: pass ?state= or set state on the logged-in user"));
    }

    private String resolveDistrict(String district) {
        if (StringUtils.hasText(district)) {
            return district.trim();
        }
        return commonUtil
                .resolveCurrentUser()
                .map(user -> user.getDistrict())
                .filter(StringUtils::hasText)
                .map(String::trim)
                .orElseThrow(() -> new IllegalArgumentException(
                        "District is required: pass ?district= or set district on the logged-in user"));
    }
}
