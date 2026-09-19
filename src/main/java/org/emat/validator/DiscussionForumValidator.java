package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.DiscussionForum;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.DiscussionForumRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscussionForumValidator {

    private static final String NOT_FOUND_MESSAGE = "Discussion Forum not found with ID: ";

    private final DiscussionForumRepository repository;

    public DiscussionForum getByIdOrThrow(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}