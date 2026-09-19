package org.emat.repository;

import java.util.List;
import org.emat.entity.BdsServiceProvidersOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BdsServiceProvidersOnboardingRepository
        extends JpaRepository<BdsServiceProvidersOnboarding, Long> {

    List<BdsServiceProvidersOnboarding> findAllByIsActiveTrue();
}