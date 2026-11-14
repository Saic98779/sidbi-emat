package org.emat.service;

import org.emat.dto.ChangePasswordRequest;

/** Change password for a logged-in user using their email and current password. */
public interface ChangePasswordService {

    /**
     * Checks that the email belongs to the logged-in user and the current password is correct,
     * validates the new password and saves it.
     */
    void changePassword(String username, ChangePasswordRequest request);
}
