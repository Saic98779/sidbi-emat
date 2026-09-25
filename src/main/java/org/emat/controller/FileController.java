package org.emat.controller;

import java.util.List;
import org.emat.dto.ApiResponse;
import org.emat.dto.UploadedFileResponse;
import org.emat.service.FileStorageService;
import org.emat.util.PiiEncryptionService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileStorageService storageService;
    private final PiiEncryptionService encryptionService;

    public FileController(FileStorageService storageService, PiiEncryptionService encryptionService) {
        this.storageService = storageService;
        this.encryptionService = encryptionService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UploadedFileResponse>> uploadFile(
            @RequestParam(required = false) String registrationId,
            @RequestParam String stage,
            @RequestParam String stageId,
            @RequestPart("file") MultipartFile file) {
        return created(
                "File uploaded successfully",
                storageService.store(
                        decryptId(registrationId), stage, decryptRequiredId(stageId), file));
    }

    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<UploadedFileResponse>>> uploadFiles(
            @RequestParam(required = false) String registrationId,
            @RequestParam String stage,
            @RequestParam String stageId,
            @RequestPart("files") List<MultipartFile> files) {
        return created(
                "Files uploaded successfully",
                storageService.storeAll(
                        decryptId(registrationId), stage, decryptRequiredId(stageId), files));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UploadedFileResponse>>> listFiles(
            @RequestParam(required = false) String registrationId,
            @RequestParam String stage,
            @RequestParam String stageId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Files fetched successfully",
                        storageService.listFiles(
                                decryptId(registrationId), stage, decryptRequiredId(stageId))));
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam(required = false) String registrationId,
            @RequestParam String stage,
            @RequestParam String stageId,
            @PathVariable String filename) {
        Resource resource =
                storageService.loadAsResource(
                        decryptId(registrationId), stage, decryptRequiredId(stageId), filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(
            @RequestParam(required = false) String registrationId,
            @RequestParam String stage,
            @RequestParam String stageId,
            @PathVariable String filename) {
        storageService.delete(
                decryptId(registrationId), stage, decryptRequiredId(stageId), filename);
        return ResponseEntity.ok(ApiResponse.success("File deleted successfully", null));
    }

    private Long decryptId(String encryptedId) {
        if (encryptedId == null || encryptedId.isBlank()) {
            return null;
        }
        return encryptionService.decryptId(encryptedId);
    }

    private Long decryptRequiredId(String encryptedId) {
        Long id = decryptId(encryptedId);
        if (id == null) {
            throw new IllegalArgumentException(
                    "stageId is required and must be sent as an encrypted ENC:... value");
        }
        return id;
    }

    private <T> ResponseEntity<ApiResponse<T>> created(String message, T body) {
        return ResponseEntity.status(201).body(ApiResponse.created(message, body));
    }
}