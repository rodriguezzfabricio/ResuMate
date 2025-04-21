package com.example.airesumedebugger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ResumeAnalysisController {

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeResume(@RequestBody String resumeText) {
        // For now, return dummy feedback
        String feedback = "Good job using active verbs! Your resume shows strong action-oriented language. " +
                         "Consider adding more quantifiable achievements to make your experience more impactful.";
        
        return ResponseEntity.ok(feedback);
    }
} 