package com.agrillnovate.System.dto;

import java.util.Date;

public class ReportFeedbackDTO {

    private Long feedbackID;
    private Date dateSubmitted;
    private String content;

    public ReportFeedbackDTO(Long feedbackID, Date dateSubmitted, String content) {
        this.feedbackID = feedbackID;
        this.dateSubmitted = dateSubmitted;
        this.content = content;
    }

    // Getters and Setters
    public Long getFeedbackID() { return feedbackID; }
    public void setFeedbackID(Long feedbackID) { this.feedbackID = feedbackID; }

    public Date getDateSubmitted() { return dateSubmitted; }
    public void setDateSubmitted(Date dateSubmitted) { this.dateSubmitted = dateSubmitted; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
