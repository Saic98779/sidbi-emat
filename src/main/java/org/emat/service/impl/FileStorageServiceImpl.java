package org.emat.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import org.emat.dto.UploadedFileResponse;
import org.emat.entity.UploadedFile;
import org.emat.exception.FileStorageException;
import org.emat.repository.UploadedFileRepository;
import org.emat.service.FileStorageService;
import org.emat.util.PiiEncryptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String STORAGE_INIT_ERROR = "Could not create storage directory";
    private static final String STORE_ERROR = "Failed to store file";
    private static final String LOAD_ERROR = "Failed to load file as resource";
    private static final String DELETE_ERROR = "Failed to delete file";
    private static final String UNREGISTERED_KEY = "unregistered";

    private final Path storageBase;
    private final UploadedFileRepository repository;
    private final String downloadBase;
    private final PiiEncryptionService encryptionService;

    public FileStorageServiceImpl(
            @Value("${file.storage.base:uploads}") String storageBase,
            @Value("${file.download.base:/files}") String downloadBase,
            UploadedFileRepository repository,
            PiiEncryptionService encryptionService) {
        this.storageBase = Paths.get(storageBase).toAbsolutePath().normalize();
        this.repository = repository;
        this.downloadBase = downloadBase;
        this.encryptionService = encryptionService;
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
    public UploadedFileResponse store(Long registrationId, String stage, Long stageId, MultipartFile file) {
        return storeInternal(registrationId, stage, stageId, file);
    }

    private UploadedFileResponse storeInternal(
            Long registrationId, String stage, Long stageId, MultipartFile file) {
        String regId = normalizeRegistrationKey(registrationId);
        String sId = idToString(stageId);
        validateRegistrationId(regId);
        validateStage(stage);
        validateStageId(sId);
        String filename = validateAndSanitizeFile(file);

        try {
            Path targetDir = resolveStageDirectory(regId, stage, sId);
            Files.createDirectories(targetDir);

            Path target = targetDir.resolve(filename);
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            // NOTE: repository lookup/entity now needs to be keyed by stage + stageId
            // as well as registrationId + filename, otherwise two different stages
            // (e.g. "appraisal" and "bse") with the same stageId/filename would collide.
            // This assumes UploadedFileRepository gains a
            // findByRegistrationIdAndStageAndStageIdAndFilename(...) method,
            // and UploadedFile entity gains stage/stageId fields.
            UploadedFile entity =
                    repository
                            .findByRegistrationIdAndStageAndStageIdAndFilename(
                                    regId, stage, sId, filename)
                            .orElseGet(() -> UploadedFile.builder().build());

            entity.setRegistrationId(regId);
            entity.setStage(stage);
            entity.setStageId(sId);
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
    public List<UploadedFileResponse> storeAll(
            Long registrationId, String stage, Long stageId, List<MultipartFile> files) {
        String regId = normalizeRegistrationKey(registrationId);
        String sId = idToString(stageId);
        validateRegistrationId(regId);
        validateStage(stage);
        validateStageId(sId);
        if (files == null) {
            throw new IllegalArgumentException("Files list cannot be null");
        }

        return files.stream()
                .map(file -> storeInternal(registrationId, stage, stageId, file))
                .toList();
    }

    @Override
    public Resource loadAsResource(Long registrationId, String stage, Long stageId, String filename) {
        String regId = normalizeRegistrationKey(registrationId);
        String sId = idToString(stageId);
        validateRegistrationId(regId);
        validateStage(stage);
        validateStageId(sId);
        String sanitizedFilename = validateFilename(filename);

        try {
            Path file = resolveAndValidateStoragePath(regId, stage, sId, sanitizedFilename);
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
    public List<UploadedFileResponse> listFiles(Long registrationId, String stage, Long stageId) {
        String regId = normalizeRegistrationKey(registrationId);
        String sId = idToString(stageId);
        validateRegistrationId(regId);
        validateStage(stage);
        validateStageId(sId);
        // NOTE: assumes UploadedFileRepository gains
        // findByRegistrationIdAndStageAndStageId(...)
        List<UploadedFile> list =
                repository.findByRegistrationIdAndStageAndStageId(regId, stage, sId);
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void delete(Long registrationId, String stage, Long stageId, String filename) {
        String regId = normalizeRegistrationKey(registrationId);
        String sId = idToString(stageId);
        validateRegistrationId(regId);
        validateStage(stage);
        validateStageId(sId);
        String sanitizedFilename = validateFilename(filename);

        try {
            Path file = resolveAndValidateStoragePath(regId, stage, sId, sanitizedFilename);
            Files.deleteIfExists(file);
            repository
                    .findByRegistrationIdAndStageAndStageIdAndFilename(
                            regId, stage, sId, sanitizedFilename)
                    .ifPresent(repository::delete);
        } catch (IOException e) {
            throw new FileStorageException(DELETE_ERROR, e);
        }
    }

    // Helper to build download URL from configured base and path parts.
    // Stage ID and registration ID are encrypted query parameters; stage is a plain
    // query parameter; filename stays in the path.
    private String buildDownloadUrl(Long registrationId, String stage, Long stageId, String filename) {
        String base = (downloadBase == null) ? "" : downloadBase.replaceAll("/+$", "");
        String url = base + "/" + filename + "?stage=" + stage + "&stageId=" + encryptionService.encryptId(stageId);
        if (registrationId != null) {
            url += "&registrationId=" + encryptionService.encryptId(registrationId);
        }
        return url;
    }

    private UploadedFileResponse toResponse(UploadedFile file) {
        Long registrationId = parseIdSafely(file.getRegistrationId());
        Long stageId = parseIdSafely(file.getStageId());
        return new UploadedFileResponse(
                file.getId(),
                registrationId,
                stageId,
                file.getFilename(),
                file.getContentType(),
                file.getSize(),
                buildDownloadUrl(registrationId, file.getStage(), stageId, file.getFilename()),
                file.getCreatedAt());
    }

    private String idToString(Long id) {
        return id == null ? null : id.toString();
    }

    // Anonymous uploads (no registration yet) share a fixed storage key so the
    // required registrationId column/path segment has a stable non-null value.
    private String normalizeRegistrationKey(Long registrationId) {
        String key = idToString(registrationId);
        return key == null ? UNREGISTERED_KEY : key;
    }

    private Long parseIdSafely(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void validateRegistrationId(String registrationId) {
        if (!StringUtils.hasText(registrationId)) {
            throw new IllegalArgumentException("Registration ID is required");
        }
    }

    private void validateStage(String stage) {
        if (!StringUtils.hasText(stage)) {
            throw new IllegalArgumentException("Stage is required");
        }
        // Restrict to known stage folders to prevent arbitrary path segments.
        if (!(stage.equals("registration") || stage.equals("appraisal") || stage.equals("bse"))) {
            throw new IllegalArgumentException("Invalid stage: " + stage);
        }
    }

    private void validateStageId(String stageId) {
        if (!StringUtils.hasText(stageId)) {
            throw new IllegalArgumentException("Stage ID is required");
        }
        String sanitized = StringUtils.cleanPath(stageId);
        if (!sanitized.equals(stageId)
                || sanitized.contains("..")
                || sanitized.contains("/")
                || sanitized.contains("\\")) {
            throw new IllegalArgumentException("Invalid stage ID: " + stageId);
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

    // Builds {storageBase}/{registrationId}/{stage}/{stageId} and guards against
    // path traversal via any of the three segments.
    private Path resolveStageDirectory(String registrationId, String stage, String stageId) {
        Path regDir = storageBase.resolve(registrationId).normalize();
        if (!regDir.startsWith(storageBase)) {
            throw new IllegalArgumentException("Invalid registration ID: " + registrationId);
        }

        Path stageDir = regDir.resolve(stage).normalize();
        if (!stageDir.startsWith(regDir)) {
            throw new IllegalArgumentException("Invalid stage: " + stage);
        }

        Path stageIdDir = stageDir.resolve(stageId).normalize();
        if (!stageIdDir.startsWith(stageDir)) {
            throw new IllegalArgumentException("Invalid stage ID: " + stageId);
        }

        return stageIdDir;
    }

    private Path resolveAndValidateStoragePath(
            String registrationId, String stage, String stageId, String filename) {
        Path stageIdDir = resolveStageDirectory(registrationId, stage, stageId);
        Path filePath = stageIdDir.resolve(filename).normalize();
        if (!filePath.startsWith(stageIdDir)) {
            throw new IllegalArgumentException("Invalid file path: " + filename);
        }
        return filePath;
    }
}