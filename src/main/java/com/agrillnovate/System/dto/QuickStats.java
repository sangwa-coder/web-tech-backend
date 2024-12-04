package com.agrillnovate.System.dto;


public class QuickStats {
    private int ongoing;
    private int published;
    private int feedback;

    public QuickStats(int ongoing, int published) {
        this.ongoing = ongoing;
        this.published = published;
        this.feedback = feedback;
    }

    // Getters and setters

    public int getOngoing() {
        return ongoing;
    }

    public void setOngoing(int ongoing) {
        this.ongoing = ongoing;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }
}
