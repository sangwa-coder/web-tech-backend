package com.agrillnovate.System.Repository;

import com.agrillnovate.System.model.PublicKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicKnowledgeRepository extends JpaRepository<PublicKnowledge, Long> {
}