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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UploadedFileResponse>> uploadFile(
            @RequestParam(required = false) Long registrationId,
            @RequestParam String stage,
            @RequestParam Long stageId,
            @RequestPart("file") MultipartFile file) {
        return created(
                "File uploaded successfully",
                storageService.store(registrationId, stage, stageId, file));
    }

    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<UploadedFileResponse>>> uploadFiles(
            @RequestParam(required = false) Long registrationId,
            @RequestParam String stage,
            @RequestParam Long stageId,
            @RequestPart("files") List<MultipartFile> files) {
        return created(
                "Files uploaded successfully",
                storageService.storeAll(registrationId, stage, stageId, files));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UploadedFileResponse>>> listFiles(
            @RequestParam(required = false) Long registrationId,
            @RequestParam String stage,
            @RequestParam Long stageId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Files fetched successfully",
                        storageService.listFiles(registrationId, stage, stageId)));
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam(required = false) Long registrationId,
            @RequestParam String stage,
            @RequestParam Long stageId,
            @PathVariable String filename) {
        Resource resource = storageService.loadAsResource(registrationId, stage, stageId, filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(
            @RequestParam(required = false) Long registrationId,
            @RequestParam String stage,
            @RequestParam Long stageId,
            @PathVariable String filename) {
        storageService.delete(registrationId, stage, stageId, filename);
        return ResponseEntity.ok(ApiResponse.success("File deleted successfully", null));
    }

    private <T> ResponseEntity<ApiResponse<T>> created(String message, T body) {
        return ResponseEntity.status(201).body(ApiResponse.created(message, body));
    }
}