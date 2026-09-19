package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiscussionForumRequest;
import org.emat.dto.DiscussionForumResponse;
import org.emat.dto.UpdateDiscussionForumRequest;

public interface DiscussionForumService {

    DiscussionForumResponse create(CreateDiscussionForumRequest request);

    DiscussionForumResponse getById(Long id);

    List<DiscussionForumResponse> getAll();

    DiscussionForumResponse update(Long id, UpdateDiscussionForumRequest request);

    void delete(Long id);
}