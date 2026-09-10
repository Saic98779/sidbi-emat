package org.emat.service;

import org.emat.dto.UploadedFileResponse;
import org.emat.entity.UploadedFile;
import org.emat.exception.FileStorageException;
import org.emat.repository.UploadedFileRepository;
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

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String STORAGE_INIT_ERROR = "Could not create storage directory";
    private static final String STORE_ERROR = "Failed to store file";
    private static final String LOAD_ERROR = "Failed to load file as resource";
    private static final String DELETE_ERROR = "Failed to delete file";

    private final Path storageBase;
    private final UploadedFileRepository repository;
    private final String downloadBase;

    public FileStorageServiceImpl(@Value("${file.storage.base:uploads}") String storageBase, @Value("${file.download.base:/files}") String downloadBase, UploadedFileRepository repository) {
        this.storageBase = Paths.get(storageBase).toAbsolutePath().normalize();
        this.repository = repository;
        this.downloadBase = downloadBase;
        initializeStorageBase();
    }

    private void initializeStorageBase() {
        try {
            Files.createDirectories(this.storageBase);
        } catch (IOException e) {
            throw new FileStorageException(STORAGE_INIT_ERROR, e);
        }
    }

    @Override
    @Transactional
    public UploadedFileResponse store(String registrationId, MultipartFile file) {
        return storeInternal(registrationId, file);
    }

    private UploadedFileResponse storeInternal(String registrationId, MultipartFile file) {
        validateRegistrationId(registrationId);
        String filename = validateAndSanitizeFile(file);

        try {
            Path regDir = resolveRegistrationDirectory(registrationId);
            Files.createDirectories(regDir);

            Path target = regDir.resolve(filename);
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            UploadedFile entity = repository.findByRegistrationIdAndFilename(registrationId, filename)
                    .orElseGet(() -> UploadedFile.builder().build());

            entity.setRegistrationId(registrationId);
            entity.setFilename(filename);
            entity.setContentType(file.getContentType());
            entity.setSize(file.getSize());
            entity.setRelativePath(storageBase.relativize(target).toString());
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(LocalDateTime.now());
            }

            UploadedFile saved = repository.save(entity);
            return toResponse(saved);

        } catch (IOException e) {
            throw new FileStorageException(STORE_ERROR, e);
        }
    }

    @Override
    @Transactional
    public List<UploadedFileResponse> storeAll(String registrationId, List<MultipartFile> files) {
        validateRegistrationId(registrationId);
        if (files == null) {
            throw new IllegalArgumentException("Files list cannot be null");
        }

        return files.stream()
                .map(file -> storeInternal(registrationId, file))
                .toList();
    }

    @Override
    public Resource loadAsResource(String registrationId, String filename) {
        validateRegistrationId(registrationId);
        String sanitizedFilename = validateFilename(filename);

        try {
            Path file = resolveAndValidateStoragePath(registrationId, sanitizedFilename);
            if (!Files.exists(file) || !Files.isReadable(file)) {
                throw new FileStorageException("File not found: " + sanitizedFilename);
            }
            return new FileSystemResource(file.toFile());
        } catch (FileStorageException e) {
            throw e;
        } catch (Exception e) {
            throw new FileStorageException(LOAD_ERROR, e);
        }
    }

    @Override
    public List<UploadedFileResponse> listFiles(String registrationId) {
        validateRegistrationId(registrationId);
        List<UploadedFile> list = repository.findByRegistrationId(registrationId);
        return list.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(String registrationId, String filename) {
        validateRegistrationId(registrationId);
        String sanitizedFilename = validateFilename(filename);

        try {
            Path file = resolveAndValidateStoragePath(registrationId, sanitizedFilename);
            Files.deleteIfExists(file);
            repository.findByRegistrationIdAndFilename(registrationId, sanitizedFilename).ifPresent(repository::delete);
        } catch (IOException e) {
            throw new FileStorageException(DELETE_ERROR, e);
        }
    }

    // Helper to build download URL from configured base and path parts.
    private String buildDownloadUrl(String registrationId, String filename) {
        String base = (downloadBase == null) ? "" : downloadBase.replaceAll("/+$", "");
        return base + "/" + registrationId + "/" + filename;
    }

    private UploadedFileResponse toResponse(UploadedFile file) {
        return new UploadedFileResponse(
                file.getId(),
                file.getRegistrationId(),
                file.getFilename(),
                file.getContentType(),
                file.getSize(),
                buildDownloadUrl(file.getRegistrationId(), file.getFilename()),
                file.getCreatedAt()
        );
    }

    private void validateRegistrationId(String registrationId) {
        if (!StringUtils.hasText(registrationId)) {
            throw new IllegalArgumentException("Registration ID is required");
        }
    }

    private String validateAndSanitizeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file.");
        }

        String originalFilename = file.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new IllegalArgumentException("Original filename is missing");
        }

        return validateFilename(originalFilename);
    }

    private String validateFilename(String filename) {
        String sanitized = StringUtils.cleanPath(filename);
        if (!StringUtils.hasText(sanitized)
                || sanitized.contains("..")
                || sanitized.startsWith("/")
                || sanitized.startsWith("\\")) {
            throw new IllegalArgumentException("Invalid filename: " + filename);
        }
        return sanitized;
    }

    private Path resolveRegistrationDirectory(String registrationId) {
        Path directory = storageBase.resolve(registrationId).normalize();
        if (!directory.startsWith(storageBase)) {
            throw new IllegalArgumentException("Invalid registration ID: " + registrationId);
        }
        return directory;
    }

    private Path resolveAndValidateStoragePath(String registrationId, String filename) {
        Path registrationDirectory = resolveRegistrationDirectory(registrationId);
        Path filePath = registrationDirectory.resolve(filename).normalize();
        if (!filePath.startsWith(registrationDirectory)) {
            throw new IllegalArgumentException("Invalid file path: " + filename);
        }
        return filePath;
    }
}
