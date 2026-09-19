package org.emat.service;

import java.util.List;
import org.emat.dto.BdspImportResult;
import org.emat.dto.BdspResponse;
import org.emat.dto.CreateBdspRequest;
import org.emat.dto.UpdateBdspRequest;
import org.springframework.web.multipart.MultipartFile;

public interface BdspService {

    BdspResponse create(CreateBdspRequest request);

    BdspResponse getById(Long id);

    List<BdspResponse> getAll();

    BdspResponse update(Long id, UpdateBdspRequest request);

    void delete(Long id);

    BdspImportResult importFromExcel(MultipartFile file);
}