package com.agrillnovate.System.dto;

public class CommentStat {
    private String researchTitle;
    private long commentCount;

    public CommentStat(String researchTitle, long commentCount) {
        this.researchTitle = researchTitle;
        this.commentCount = commentCount;
    }

    // Getters and setters
    public String getResearchTitle() {
        return researchTitle;
    }

    public void setResearchTitle(String researchTitle) {
        this.researchTitle = researchTitle;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
