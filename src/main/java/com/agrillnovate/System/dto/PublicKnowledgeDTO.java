package com.agrillnovate.System.dto;

import java.sql.Timestamp;

public class PublicKnowledgeDTO {
    private Long id;
    private String title;
    private String content;
    private byte[] image;
    private Timestamp datePublished;

    // Constructors, getters, and setters
    public PublicKnowledgeDTO() {}

    public PublicKnowledgeDTO(Long id, String title, String content, byte[] image, Timestamp datePublished) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.datePublished = datePublished;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }
}
