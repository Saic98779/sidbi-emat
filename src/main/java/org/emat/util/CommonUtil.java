package org.emat.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.entity.User;
import org.emat.enums.Role;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommonUtil {

    private static final String USER_NOT_FOUND_WITH_ID_MESSAGE = "User not found with ID: ";

    private final UserRepository userRepository;

    public Optional<User> resolveCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails userDetails)) {
            return Optional.empty();
        }

        return userRepository.findByUsername(userDetails.getUsername());
    }

    public boolean isCurrentUserSidbiSde() {
        return resolveCurrentUser()
                .map(user -> user.getRole() == Role.SIDBI_SDE)
                .orElse(false);
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "system";
    }

    public User resolveSidbiApprover(Long sidbeApprovedByUserId, boolean isSidbiSdeCaller) {
        if (sidbeApprovedByUserId != null) {
            User sidbiApprover = userRepository.findById(sidbeApprovedByUserId)
                    .orElseThrow(() -> {
                        log.error("SIDBI approver user not found with ID: {}", sidbeApprovedByUserId);
                        return new EntityNotFoundException(USER_NOT_FOUND_WITH_ID_MESSAGE + sidbeApprovedByUserId);
                    });
            log.info("SIDBI approver user found: {}", sidbiApprover.getUsername());
            return sidbiApprover;
        }

        if (isSidbiSdeCaller) {
            return resolveCurrentUser().orElse(null);
        }

        return null;
    }
}

