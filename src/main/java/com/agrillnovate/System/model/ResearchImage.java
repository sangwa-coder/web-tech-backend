package com.agrillnovate.System.model;

import jakarta.persistence.*;

@Entity
@Table(name = "research_images")
public class ResearchImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "research_id")
    private Research research;

    // Default constructor
    public ResearchImage() {}

    // Parameterized constructor
    public ResearchImage(byte[] image, Research research) {
        this.image = image;
        this.research = research;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }
}
