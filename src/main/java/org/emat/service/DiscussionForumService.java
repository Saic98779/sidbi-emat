package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiscussionForumRequest;
import org.emat.dto.DiscussionForumResponse;
import org.emat.dto.UpdateDiscussionForumRequest;
import org.emat.dto.UpdateDiscussionForumStatusRequest;

public interface DiscussionForumService {

    DiscussionForumResponse create(CreateDiscussionForumRequest request);

    DiscussionForumResponse getById(Long id);

    List<DiscussionForumResponse> getAll();

    DiscussionForumResponse update(Long id, UpdateDiscussionForumRequest request);

    DiscussionForumResponse updateStatus(Long id, UpdateDiscussionForumStatusRequest request);

    void delete(Long id);
}