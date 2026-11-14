package org.emat.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.emat.entity.PasswordOtp;
import org.emat.enums.OtpPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

/** Repository for {@link PasswordOtp}. */
@Repository
public interface PasswordOtpRepository extends JpaRepository<PasswordOtp, Long> {

    /**
     * Loads the row with a write lock, so two requests running at the same time cannot both use
     * the same OTP or token or slip past the attempt limit.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PasswordOtp> findByUserIdAndPurpose(Long userId, OtpPurpose purpose);
}
