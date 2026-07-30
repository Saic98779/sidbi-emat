package org.emat.service;

import org.emat.dto.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    UploadedFileResponse store(String registrationUuid, MultipartFile file);
    List<UploadedFileResponse> storeAll(String registrationUuid, List<MultipartFile> files);
    Resource loadAsResource(String registrationUuid, String filename);
    List<UploadedFileResponse> listFiles(String registrationUuid);
    void delete(String registrationUuid, String filename);
}

