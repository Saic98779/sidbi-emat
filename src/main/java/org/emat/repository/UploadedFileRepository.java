package org.emat.repository;

import org.emat.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
    List<UploadedFile> findByRegistrationUuid(String registrationUuid);
    Optional<UploadedFile> findByRegistrationUuidAndFilename(String registrationUuid, String filename);
}

