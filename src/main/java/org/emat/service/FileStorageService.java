package org.emat.service;

import org.emat.dto.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    UploadedFileResponse store(String registrationId, MultipartFile file);
    List<UploadedFileResponse> storeAll(String registrationId, List<MultipartFile> files);
    Resource loadAsResource(String registrationId, String filename);
    List<UploadedFileResponse> listFiles(String registrationId);
    void delete(String registrationId, String filename);
}

