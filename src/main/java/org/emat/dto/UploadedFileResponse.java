package org.emat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploadedFileResponse {
    private Long id;
    private String registrationUuid;
    private String filename;
    private String contentType;
    private Long size;
    private String downloadUrl;
    private LocalDateTime createdAt;

    public UploadedFileResponse() {}

    public UploadedFileResponse(Long id, String registrationUuid, String filename, String contentType, Long size, String downloadUrl, LocalDateTime createdAt) {
        this.id = id;
        this.registrationUuid = registrationUuid;
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.createdAt = createdAt;
    }
}

