package org.emat.controller;

import org.emat.dto.ApiResponse;
import org.emat.dto.UploadedFileResponse;
import org.emat.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(value = "/{registrationId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UploadedFileResponse>> uploadFile(@PathVariable String registrationId,
                                                                         @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(201)
                .body(ApiResponse.created("File uploaded successfully", storageService.store(registrationId, file)));
    }

    @PostMapping(value = "/{registrationId}/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<UploadedFileResponse>>> uploadFiles(@PathVariable String registrationId,
                                                                                @RequestPart("files") List<MultipartFile> files) {
        return ResponseEntity.status(201)
                .body(ApiResponse.created("Files uploaded successfully", storageService.storeAll(registrationId, files)));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<ApiResponse<List<UploadedFileResponse>>> listFiles(@PathVariable String registrationId) {
        return ResponseEntity.ok(ApiResponse.success("Files fetched successfully", storageService.listFiles(registrationId)));
    }

    @GetMapping("/{registrationId}/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String registrationId, @PathVariable String filename) {
        Resource resource = storageService.loadAsResource(registrationId, filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{registrationId}/{filename:.+}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable String registrationId, @PathVariable String filename) {
        storageService.delete(registrationId, filename);
        return ResponseEntity.ok(ApiResponse.success("File deleted successfully", null));
    }
}
