package com.agrillnovate.System.dto;

import com.agrillnovate.System.model.*;
import com.agrillnovate.System.model.Thread;

import java.util.List;

public class SystemReportDTO {
    private long totalUsers;
    private long totalResearch;
    private long totalComments;
    private long totalThreads;
    private long totalPosts;
    private long totalFeedback;

    private List<User> users;
    private List<ResearchDTO> research;
    private List<Comment> comments;
    private List<Thread> threads;
    private List<Post> posts;
    private List<Feedback> feedback;

    // Getters and setters for all fields
    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalResearch() {
        return totalResearch;
    }

    public void setTotalResearch(long totalResearch) {
        this.totalResearch = totalResearch;
    }

    public long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(long totalComments) {
        this.totalComments = totalComments;
    }

    public long getTotalThreads() {
        return totalThreads;
    }

    public void setTotalThreads(long totalThreads) {
        this.totalThreads = totalThreads;
    }

    public long getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(long totalPosts) {
        this.totalPosts = totalPosts;
    }

    public long getTotalFeedback() {
        return totalFeedback;
    }

    public void setTotalFeedback(long totalFeedback) {
        this.totalFeedback = totalFeedback;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<ResearchDTO> getResearch() {
        return research;
    }

    public void setResearch(List<ResearchDTO> research) {
        this.research = research;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }
}
