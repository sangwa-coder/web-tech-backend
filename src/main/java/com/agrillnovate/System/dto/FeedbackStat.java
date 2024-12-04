package com.agrillnovate.System.dto;

public class FeedbackStat {
    private String researchTitle;
    private int feedbackCount;

    public FeedbackStat(String researchTitle, int feedbackCount) {
        this.researchTitle = researchTitle;
        this.feedbackCount = feedbackCount;
    }

    // Getters and setters
    public String getResearchTitle() {
        return researchTitle;
    }

    public void setResearchTitle(String researchTitle) {
        this.researchTitle = researchTitle;
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(int feedbackCount) {
        this.feedbackCount = feedbackCount;
    }
}
