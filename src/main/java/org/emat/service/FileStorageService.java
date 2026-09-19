package org.emat.service;

import java.util.List;
import org.emat.dto.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    UploadedFileResponse store(Long registrationId, String stage, Long stageId, MultipartFile file);

    List<UploadedFileResponse> storeAll(
            Long registrationId, String stage, Long stageId, List<MultipartFile> files);

    Resource loadAsResource(Long registrationId, String stage, Long stageId, String filename);

    List<UploadedFileResponse> listFiles(Long registrationId, String stage, Long stageId);

    void delete(Long registrationId, String stage, Long stageId, String filename);
}