package com.agrillnovate.System.dto;

import java.util.Date;
import java.util.List;

public class ResearchDTO {
    private Long researchID;
    private String title;
    private String author;
    private Date datePublished;
    private String content;
    private String status;
    private Double latitude;
    private Double longitude;
    private String category; // Add this line

    private List<ResearchImageDTO> images;
    private List<FeedbackDTO> feedbacks;

    public ResearchDTO(Long researchID, String title, String author, Date datePublished, String content, String status, Double latitude, Double longitude, String category, List<ResearchImageDTO> images, List<FeedbackDTO> feedbacks) {
        this.researchID = researchID;
        this.title = title;
        this.author = author;
        this.datePublished = datePublished;
        this.content = content;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.images = images;
        this.feedbacks = feedbacks;
    }

    // Constructors, Getters, and Setters
    public ResearchDTO(Long researchID, String title, String author, Date datePublished, String content, String status, Double latitude, Double longitude, List<ResearchImageDTO> images, List<FeedbackDTO> feedbacks) {
        this.researchID = researchID;
        this.title = title;
        this.author = author;
        this.datePublished = datePublished;
        this.content = content;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.feedbacks = feedbacks;
    }

    // Getters and Setters for all fields...

    public Long getResearchID() {
        return researchID;
    }

    public void setResearchID(Long researchID) {
        this.researchID = researchID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<ResearchImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ResearchImageDTO> images) {
        this.images = images;
    }

    public List<FeedbackDTO> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackDTO> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
