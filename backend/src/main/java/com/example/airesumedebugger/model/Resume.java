package com.example.airesumedebugger.model;

import java.util.List;

public class Resume {
    private String id;
    private String candidateName;
    private String email;
    private String phoneNumber;
    private String education;
    private List<String> skills;
    private List<String> experience;
    
    // Constructors
    public Resume() {}
    
    public Resume(String id, String candidateName, String email, String phoneNumber, 
                  String education, List<String> skills, List<String> experience) {
        this.id = id;
        this.candidateName = candidateName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.education = education;
        this.skills = skills;
        this.experience = experience;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCandidateName() {
        return candidateName;
    }
    
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEducation() {
        return education;
    }
    
    public void setEducation(String education) {
        this.education = education;
    }
    
    public List<String> getSkills() {
        return skills;
    }
    
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
    
    public List<String> getExperience() {
        return experience;
    }
    
    public void setExperience(List<String> experience) {
        this.experience = experience;
    }
} 