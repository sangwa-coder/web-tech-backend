package com.agrillnovate.System.dto;

import java.util.Date;

public class CommentDTO {

    private Long researchID;
    private String name;
    private String content;
    private String email;
    private String phone;
    private Date dateSubmitted;

    // Constructor
    public CommentDTO(Long researchID, String name, String content, String email, String phone, Date dateSubmitted) {
        this.researchID = researchID;
        this.name = name;
        this.content = content;
        this.email = email;
        this.phone = phone;
        this.dateSubmitted = dateSubmitted;
    }

    // Getters and Setters
    public Long getResearchID() {
        return researchID;
    }

    public void setResearchID(Long researchID) {
        this.researchID = researchID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
}
