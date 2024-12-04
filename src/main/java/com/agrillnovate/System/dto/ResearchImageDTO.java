package com.agrillnovate.System.dto;

public class ResearchImageDTO {
    private Long id;
    private byte[] image;

    public ResearchImageDTO(Long id, byte[] image) {
        this.id = id;
        this.image = image;
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
}
