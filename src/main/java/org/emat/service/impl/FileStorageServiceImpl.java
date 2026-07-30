package org.emat.service.impl;

import org.emat.dto.UploadedFileResponse;
import org.emat.entity.UploadedFile;
import org.emat.repository.UploadedFileRepository;
import org.emat.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path storageBase;
    private final UploadedFileRepository repository;
    private final String downloadBase;

    public FileStorageServiceImpl(@Value("${file.storage.base:uploads}") String storageBase, @Value("${file.download.base:/files}") String downloadBase, UploadedFileRepository repository) {
        this.storageBase = Paths.get(storageBase).toAbsolutePath().normalize();
        this.repository = repository;
        this.downloadBase = downloadBase;

        try {
            Files.createDirectories(this.storageBase);
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory", e);
        }
    }

    @Override
    @Transactional
    public UploadedFileResponse store(String registrationUuid, MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("Failed to store empty file.");
            }

            String original = file.getOriginalFilename();
            if (original == null) {
                throw new IllegalArgumentException("Original filename is missing");
            }

            String filename = StringUtils.cleanPath(original);
            // Prevent path traversal
            if (filename.contains("..") || filename.startsWith("/") || filename.startsWith("\\")) {
                throw new IllegalArgumentException("Invalid filename: " + filename);
            }

            Path regDir = storageBase.resolve(registrationUuid);
            Files.createDirectories(regDir);

            Path target = regDir.resolve(filename);
            // If file exists, overwrite
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            UploadedFile entity = repository.findByRegistrationUuidAndFilename(registrationUuid, filename)
                    .orElseGet(() -> UploadedFile.builder().build());

            entity.setRegistrationUuid(registrationUuid);
            entity.setFilename(filename);
            entity.setContentType(file.getContentType());
            entity.setSize(file.getSize());
            entity.setRelativePath(storageBase.relativize(target).toString());
            entity.setCreatedAt(LocalDateTime.now());

            repository.save(entity);

            String downloadUrl = buildDownloadUrl(registrationUuid, filename);

            return new UploadedFileResponse(entity.getId(), registrationUuid, filename, entity.getContentType(), entity.getSize(), downloadUrl, entity.getCreatedAt());

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    @Transactional
    public List<UploadedFileResponse> storeAll(String registrationUuid, List<MultipartFile> files) {
        return files.stream()
                .map(file -> store(registrationUuid, file))
                .collect(Collectors.toList());
    }

    @Override
    public Resource loadAsResource(String registrationUuid, String filename) {
        try {
            Path file = storageBase.resolve(registrationUuid).resolve(filename).normalize();
            if (!Files.exists(file) || !Files.isReadable(file)) {
                throw new RuntimeException("File not found: " + filename);
            }
            return new FileSystemResource(file.toFile());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load file as resource", e);
        }
    }

    @Override
    public List<UploadedFileResponse> listFiles(String registrationUuid) {
        List<UploadedFile> list = repository.findByRegistrationUuid(registrationUuid);
        return list.stream().map(f -> new UploadedFileResponse(f.getId(), f.getRegistrationUuid(), f.getFilename(), f.getContentType(), f.getSize(), buildDownloadUrl(f.getRegistrationUuid(), f.getFilename()), f.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(String registrationUuid, String filename) {
        Path file = storageBase.resolve(registrationUuid).resolve(filename).normalize();
        try {
            Files.deleteIfExists(file);
            repository.findByRegistrationUuidAndFilename(registrationUuid, filename).ifPresent(repository::delete);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    // Helper to build download URL from configured base and path parts.
    private String buildDownloadUrl(String registrationUuid, String filename) {
        String base = (downloadBase == null) ? "" : downloadBase.replaceAll("/+$", "");
        return base + "/" + registrationUuid + "/" + filename;
    }
}
