package com.agrillnovate.System.Repository;

import com.agrillnovate.System.dto.CommentDTO;
import com.agrillnovate.System.dto.CommentStat;
import com.agrillnovate.System.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByResearchResearchID(Long researchID);

    @Query("SELECT new com.agrillnovate.System.dto.CommentStat(c.research.title, COUNT(c)) FROM Comment c GROUP BY c.research.title")
    List<CommentStat> getCommentsStats();

    @Query("SELECT new com.agrillnovate.System.dto.CommentStat(c.research.title, COUNT(c)) FROM Comment c WHERE c.research.researchID = :researchID GROUP BY c.research.title")
    List<CommentStat> getCommentsStatsByResearchID(@Param("researchID") Long researchID);
    List<Comment> findByResearch_ResearchID(Long researchID);

    @Query("SELECT new com.agrillnovate.System.dto.CommentDTO(c.research.id, c.name, c.content, c.email, c.phone, c.dateSubmitted) " +
            "FROM Comment c WHERE c.dateSubmitted BETWEEN :startDate AND :endDate")
    List<CommentDTO> findCommentsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
