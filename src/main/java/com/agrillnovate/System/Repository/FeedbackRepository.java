package com.agrillnovate.System.Repository;

import com.agrillnovate.System.dto.FeedbackDTO;
import com.agrillnovate.System.dto.FeedbackStat;
import com.agrillnovate.System.dto.ReportFeedbackDTO;
import com.agrillnovate.System.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByResearchResearchID(Long researchID);


    @Query("SELECT new com.agrillnovate.System.dto.ReportFeedbackDTO(f.id, f.dateSubmitted, f.content) " +
            "FROM Feedback f WHERE f.dateSubmitted BETWEEN :startDate AND :endDate")
    List<ReportFeedbackDTO> findFeedbacksByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);



    List<Feedback> findByResearch_ResearchID(Long researchID);

}
