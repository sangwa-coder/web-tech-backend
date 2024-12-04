package com.agrillnovate.System.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "research")
public class Research {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long researchID;

    private String title;
    private String author;
    private Date datePublished;

    @Column(columnDefinition = "LONGTEXT") // Change this line
    private String content;

    private String category; // Add this line

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    private String status;

    @OneToMany(mappedBy = "research", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResearchImage> images;


    @OneToMany(mappedBy = "research", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "research", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Feedback> feedbacks;

    private Double latitude;
    private Double longitude;

    public Research() {}

    public Research(Long researchID, String title, String author, Date datePublished, String content, String category, User user, Date created_at, String status, List<ResearchImage> images, List<Comment> comments, List<Feedback> feedbacks, Double latitude, Double longitude) {
        this.researchID = researchID;
        this.title = title;
        this.author = author;
        this.datePublished = datePublished;
        this.content = content;
        this.category = category;
        this.user = user;
        this.created_at = created_at;
        this.status = status;
        this.images = images;
        this.comments = comments;
        this.feedbacks = feedbacks;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Research(Long researchID, String title, String author, Date datePublished, String content, User user, Date created_at, String status, List<ResearchImage> images, List<Feedback> feedbacks, Double latitude, Double longitude) {
        this.researchID = researchID;
        this.title = title;
        this.author = author;
        this.datePublished = datePublished;
        this.content = content;
        this.user = user;
        this.created_at = created_at;
        this.status = status;
        this.images = images;
        this.feedbacks = feedbacks;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getResearchID() {
        return researchID;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResearchImage> getImages() {
        return images;
    }

    public void setImages(List<ResearchImage> images) {
        this.images = images;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Double getLatitude() {
        return latitude;
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
}
