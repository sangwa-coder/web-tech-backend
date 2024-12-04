package com.agrillnovate.System.dto;

import com.agrillnovate.System.model.Comment;
import com.agrillnovate.System.model.Feedback;
import com.agrillnovate.System.model.Research;

import java.util.List;

public class ResearchDetailsDTO {
    private Research research;
    private List<Comment> comments;
    private List<Feedback> feedbacks;

    // Getters and Setters

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
