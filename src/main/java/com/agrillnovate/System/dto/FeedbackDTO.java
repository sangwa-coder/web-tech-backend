package com.agrillnovate.System.dto;

import java.util.Date;

public class FeedbackDTO {

    private Long feedbackID;
    private String content;
    private Date dateSubmitted;
    private Long researchID; // Add the researchID field

    // Constructor
    public FeedbackDTO(Long feedbackID, String content, Date dateSubmitted, Long researchID) {
        this.feedbackID = feedbackID;
        this.content = content;
        this.dateSubmitted = dateSubmitted;
        this.researchID = researchID;
    }

    // Getters and Setters
    public Long getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(Long feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Long getResearchID() {
        return researchID;
    }

    public void setResearchID(Long researchID) {
        this.researchID = researchID;
    }
}
