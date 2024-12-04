package com.agrillnovate.System.dto;

import java.util.Date;

public class UserDTO {
    private Long userID;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String role;
    private Date createdAt;
    private String educationBackground; // New field
    private byte[] cv; // New field
    private boolean approved; // New field



    public UserDTO(Long userID, String name, String email, String phone, String address, String role, Date createdAt, String educationBackground, byte[] cv,boolean approved) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
        this.educationBackground = educationBackground;
        this.cv = cv;
        this.approved = approved;
    }

    // Getters and Setters

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public String getEducationBackground() { return educationBackground; }
    public void setEducationBackground(String educationBackground) { this.educationBackground = educationBackground; }

    public byte[] getCv() { return cv; }
    public void setCv(byte[] cv) { this.cv = cv; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
