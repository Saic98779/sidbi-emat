package org.emat.repository;

import java.util.List;
import java.util.Optional;
import org.emat.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

    Optional<UploadedFile> findByRegistrationIdAndFilename(String registrationId, String filename);

    List<UploadedFile> findByRegistrationId(String registrationId);
}
