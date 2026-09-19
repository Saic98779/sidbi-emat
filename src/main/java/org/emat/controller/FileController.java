package org.emat.controller;

import java.util.List;
import org.emat.dto.ApiResponse;
import org.emat.dto.UploadedFileResponse;
import org.emat.service.FileStorageService;
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

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(
            value = "/{registrationId}/{stage}/{stageId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UploadedFileResponse>> uploadFile(
            @PathVariable Long registrationId,
            @PathVariable String stage,
            @PathVariable Long stageId,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(201)
                .body(
                        ApiResponse.created(
                                "File uploaded successfully",
                                storageService.store(registrationId, stage, stageId, file)));
    }

    @PostMapping(
            value = "/{registrationId}/{stage}/{stageId}/batch",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<UploadedFileResponse>>> uploadFiles(
            @PathVariable Long registrationId,
            @PathVariable String stage,
            @PathVariable Long stageId,
            @RequestPart("files") List<MultipartFile> files) {
        return ResponseEntity.status(201)
                .body(
                        ApiResponse.created(
                                "Files uploaded successfully",
                                storageService.storeAll(registrationId, stage, stageId, files)));
    }

    @GetMapping("/{registrationId}/{stage}/{stageId}")
    public ResponseEntity<ApiResponse<List<UploadedFileResponse>>> listFiles(
            @PathVariable Long registrationId,
            @PathVariable String stage,
            @PathVariable Long stageId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Files fetched successfully",
                        storageService.listFiles(registrationId, stage, stageId)));
    }

    @GetMapping("/{registrationId}/{stage}/{stageId}/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long registrationId,
            @PathVariable String stage,
            @PathVariable Long stageId,
            @PathVariable String filename) {
        Resource resource = storageService.loadAsResource(registrationId, stage, stageId, filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{registrationId}/{stage}/{stageId}/{filename:.+}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(
            @PathVariable Long registrationId,
            @PathVariable String stage,
            @PathVariable Long stageId,
            @PathVariable String filename) {
        storageService.delete(registrationId, stage, stageId, filename);
        return ResponseEntity.ok(ApiResponse.success("File deleted successfully", null));
    }
}