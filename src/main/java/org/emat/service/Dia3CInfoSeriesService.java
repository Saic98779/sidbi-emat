package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDia3CInfoSeriesRequest;
import org.emat.dto.Dia3CInfoSeriesResponse;
import org.emat.dto.UpdateDia3CInfoSeriesRequest;
import org.emat.dto.UpdateDia3CInfoSeriesStatusRequest;

public interface Dia3CInfoSeriesService {

    Dia3CInfoSeriesResponse create(CreateDia3CInfoSeriesRequest request);

    Dia3CInfoSeriesResponse getById(Long id);

    List<Dia3CInfoSeriesResponse> getAll();

    Dia3CInfoSeriesResponse update(Long id, UpdateDia3CInfoSeriesRequest request);

    Dia3CInfoSeriesResponse updateStatus(Long id, UpdateDia3CInfoSeriesStatusRequest request);

    void delete(Long id);
}