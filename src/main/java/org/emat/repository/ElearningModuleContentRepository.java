package org.emat.repository;

import java.util.List;
import org.emat.entity.ElearningModuleContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElearningModuleContentRepository
        extends JpaRepository<ElearningModuleContent, Long> {

    List<ElearningModuleContent> findAllByIsActiveTrue();
}