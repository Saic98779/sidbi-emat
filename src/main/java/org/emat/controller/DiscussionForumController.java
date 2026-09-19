package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDiscussionForumRequest;
import org.emat.dto.DiscussionForumResponse;
import org.emat.dto.UpdateDiscussionForumRequest;
import org.emat.service.DiscussionForumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discussion-forum")
@RequiredArgsConstructor
@Slf4j
public class DiscussionForumController {

    private final DiscussionForumService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('discussionForumCreate'))")
    public ResponseEntity<ApiResponse<DiscussionForumResponse>> create(
            @RequestBody CreateDiscussionForumRequest request) {
        log.info("Received request to create Discussion Forum");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Discussion Forum created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('discussionForumRead'))")
    public ResponseEntity<ApiResponse<DiscussionForumResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch Discussion Forum with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Discussion Forum fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('discussionForumRead'))")
    public ResponseEntity<ApiResponse<List<DiscussionForumResponse>>> getAll() {
        log.info("Received request to fetch all Discussion Forums");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Discussion Forums fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('discussionForumUpdate'))")
    public ResponseEntity<ApiResponse<DiscussionForumResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateDiscussionForumRequest request) {
        log.info("Received request to update Discussion Forum with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Discussion Forum updated successfully",
                        service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('discussionForumDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Discussion Forum with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Discussion Forum deleted successfully", null));
    }
}