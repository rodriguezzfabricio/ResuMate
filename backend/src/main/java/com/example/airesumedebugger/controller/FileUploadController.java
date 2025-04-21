package com.example.airesumedebugger.controller;

import com.example.airesumedebugger.model.FileUploadResponse;
import com.example.airesumedebugger.service.FileProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final FileProcessingService fileProcessingService;

    @Autowired
    public FileUploadController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }

        try {
            // Check if file is allowed (PDF or DOCX)
            if (!fileProcessingService.isFileAllowed(file)) {
                return ResponseEntity.badRequest().body("Only PDF and DOCX files are allowed");
            }

            // Process the file
            FileUploadResponse response = fileProcessingService.processFile(file);
            return ResponseEntity.ok(response);
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process file: " + e.getMessage());
        }
    }
} 