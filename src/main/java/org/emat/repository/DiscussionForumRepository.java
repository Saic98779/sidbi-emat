package org.emat.repository;

import java.util.List;
import org.emat.entity.DiscussionForum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionForumRepository extends JpaRepository<DiscussionForum, Long> {

    List<DiscussionForum> findAllByIsActiveTrue();
}