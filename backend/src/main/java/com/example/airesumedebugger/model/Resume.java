package com.example.airesumedebugger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class Resume {
    private ResumeSection contactInfo;
    private ResumeSection summary;
    private ResumeSection experience;
    private ResumeSection education;
    private ResumeSection projects;
    private ResumeSection skills;
    private List<ResumeSection> additionalSections = new ArrayList<>();
    private String rawText;
    private List<FeedbackItem> feedbackItems = new ArrayList<>();

    // Getters and setters
    public ResumeSection getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ResumeSection contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ResumeSection getSummary() {
        return summary;
    }

    public void setSummary(ResumeSection summary) {
        this.summary = summary;
    }

    public ResumeSection getExperience() {
        return experience;
    }

    public void setExperience(ResumeSection experience) {
        this.experience = experience;
    }

    public ResumeSection getEducation() {
        return education;
    }

    public void setEducation(ResumeSection education) {
        this.education = education;
    }

    public ResumeSection getProjects() {
        return projects;
    }

    public void setProjects(ResumeSection projects) {
        this.projects = projects;
    }

    public ResumeSection getSkills() {
        return skills;
    }

    public void setSkills(ResumeSection skills) {
        this.skills = skills;
    }

    public List<ResumeSection> getAdditionalSections() {
        return additionalSections;
    }

    public void setAdditionalSections(List<ResumeSection> additionalSections) {
        this.additionalSections = additionalSections;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
    
    public List<FeedbackItem> getFeedbackItems() {
        return feedbackItems;
    }
    
    public void setFeedbackItems(List<FeedbackItem> feedbackItems) {
        this.feedbackItems = feedbackItems;
    }

    // Utility method to get all sections
    public Map<String, ResumeSection> getAllSections() {
        Map<String, ResumeSection> sections = new LinkedHashMap<>();
        if (contactInfo != null) sections.put("Contact", contactInfo);
        if (summary != null) sections.put("Summary", summary);
        if (experience != null) sections.put("Experience", experience);
        if (education != null) sections.put("Education", education);
        if (projects != null) sections.put("Projects", projects);
        if (skills != null) sections.put("Skills", skills);
        
        for (ResumeSection section : additionalSections) {
            sections.put(section.getTitle(), section);
        }
        
        return sections;
    }
}
