package org.emat.event;

/** Published after a new user is saved. Handled once the transaction commits. */
public record UserCreatedEvent(
        Long userId, String username, String email, String firstName, String lastName) {}
