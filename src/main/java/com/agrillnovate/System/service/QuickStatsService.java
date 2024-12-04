package com.agrillnovate.System.service;

import com.agrillnovate.System.dto.QuickStats;
import com.agrillnovate.System.Repository.FeedbackRepository;
import com.agrillnovate.System.Repository.ResearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuickStatsService {

    @Autowired
    private ResearchRepository researchRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public QuickStats getQuickStats(String email) {
        int ongoing = researchRepository.countByAuthor(email);
        int published = researchRepository.countPublishedByAuthor(email);

        return new QuickStats(ongoing, published);
    }
}
