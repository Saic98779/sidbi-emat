package org.emat.controller;

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

    @PostMapping(value = "/{registrationUuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadedFileResponse> uploadFile(@PathVariable String registrationUuid, @RequestPart("file") MultipartFile file) {
        UploadedFileResponse resp = storageService.store(registrationUuid, file);
        return ResponseEntity.status(201).body(resp);
    }

    @GetMapping("/{registrationUuid}")
    public ResponseEntity<List<UploadedFileResponse>> listFiles(@PathVariable String registrationUuid) {
        List<UploadedFileResponse> list = storageService.listFiles(registrationUuid);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{registrationUuid}/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String registrationUuid, @PathVariable String filename) {
        Resource resource = storageService.loadAsResource(registrationUuid, filename);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{registrationUuid}/{filename:.+}")
    public ResponseEntity<Void> deleteFile(@PathVariable String registrationUuid, @PathVariable String filename) {
        storageService.delete(registrationUuid, filename);
        return ResponseEntity.noContent().build();
    }
}

