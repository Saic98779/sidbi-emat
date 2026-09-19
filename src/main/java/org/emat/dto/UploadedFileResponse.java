package org.emat.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
public class UploadedFileResponse {
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long registrationId;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long stageId;

    private String filename;
    private String contentType;
    private Long size;
    private String downloadUrl;
    private LocalDateTime createdAt;

    public UploadedFileResponse() {}

    public UploadedFileResponse(
            Long id,
            Long registrationId,
            Long stageId,
            String filename,
            String contentType,
            Long size,
            String downloadUrl,
            LocalDateTime createdAt) {
        this.id = id;
        this.registrationId = registrationId;
        this.stageId = stageId;
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.createdAt = createdAt;
    }
}
