package com.agrillnovate.System.service;

import com.agrillnovate.System.dto.FeedbackDTO;
import com.agrillnovate.System.dto.FeedbackStat;
import com.agrillnovate.System.model.Feedback;
import com.agrillnovate.System.model.Research;
import com.agrillnovate.System.Repository.FeedbackRepository;
import com.agrillnovate.System.Repository.ResearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ResearchRepository researchRepository;

    public Feedback createFeedback(Long researchID, FeedbackDTO feedbackDTO) {
        Research research = researchRepository.findById(researchID).orElseThrow(() -> new RuntimeException("Research not found"));
        Feedback feedback = new Feedback();
        feedback.setResearch(research);
        feedback.setContent(feedbackDTO.getContent());
        feedback.setDateSubmitted(feedbackDTO.getDateSubmitted());
        return feedbackRepository.save(feedback);
    }


    public List<Feedback> getFeedbackByResearchId(Long researchId) {
        return feedbackRepository.findByResearchResearchID(researchId);
    }

    public List<FeedbackStat> getFeedbackStats() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream()
                .collect(Collectors.groupingBy(feedback -> feedback.getResearch().getTitle()))
                .entrySet().stream()
                .map(entry -> new FeedbackStat(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toList());
    }
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
    public long countFeedback() {
        return feedbackRepository.count();
    }


}
