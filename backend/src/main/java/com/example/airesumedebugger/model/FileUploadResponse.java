package com.example.airesumedebugger.model;

import java.time.LocalDateTime;

public class FileUploadResponse {
    private String originalFilename;
    private long fileSize;
    private String fileType;
    private LocalDateTime uploadTime;
    private String extractedText;
    private boolean success;
    private String errorMessage;

    // Constructors
    public FileUploadResponse() {
        this.uploadTime = LocalDateTime.now();
    }

    public FileUploadResponse(String originalFilename, long fileSize, String fileType, 
                            String extractedText, boolean success) {
        this.originalFilename = originalFilename;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.extractedText = extractedText;
        this.success = success;
        this.uploadTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}