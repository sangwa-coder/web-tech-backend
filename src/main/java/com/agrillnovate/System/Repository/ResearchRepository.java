package com.agrillnovate.System.Repository;

import com.agrillnovate.System.model.Research;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Long> {
    List<Research> findByUserUserID(Long userId);

    @Query("SELECT COUNT(r) FROM Research r WHERE r.author = :author")
    int countByAuthor(String author);

    @Query("SELECT COUNT(r) FROM Research r WHERE r.author = :author")
    int countPublishedByAuthor(String author);
    List<Research> findByAuthor(String author);




}
