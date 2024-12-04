package com.agrillnovate.System.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class PublicKnowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Timestamp datePublished;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    public PublicKnowledge(Long id, String title, String content, Timestamp datePublished, byte[] image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.datePublished = datePublished;
        this.image = image;
    }

    public PublicKnowledge() {}

    // Getters and Setters

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

    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
