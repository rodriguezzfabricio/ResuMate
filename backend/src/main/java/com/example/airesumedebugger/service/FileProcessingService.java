package com.example.airesumedebugger.service;

import com.example.airesumedebugger.model.FileUploadResponse;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(FileProcessingService.class);
    private final Tika tika = new Tika();

    /**
     * Process the uploaded file and extract text content
     * 
     * @param file The uploaded file
     * @return FileUploadResponse with extracted text and file information
     */
    public FileUploadResponse processFile(MultipartFile file) {
        FileUploadResponse response = new FileUploadResponse();
        
        try {
            // Set basic file information
            response.setOriginalFilename(file.getOriginalFilename());
            response.setFileSize(file.getSize());
            response.setFileType(determineFileType(file));
            
            // Extract text from the file
            String extractedText = extractTextFromFile(file);
            response.setExtractedText(extractedText);
            response.setSuccess(true);
            
        } catch (Exception e) {
            logger.error("Error processing file: {}", e.getMessage(), e);
            response.setSuccess(false);
            response.setErrorMessage("Failed to process file: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Determine file type from the uploaded file
     */
    private String determineFileType(MultipartFile file) throws IOException {
        return tika.detect(file.getInputStream());
    }
    
    /**
     * Extract text content from the uploaded file using Apache Tika
     */
    private String extractTextFromFile(MultipartFile file) throws IOException, TikaException, SAXException {
        // Create a content handler for storing the parsed text
        BodyContentHandler handler = new BodyContentHandler(-1); // -1 means no limit on text size
        
        // Metadata object to store document metadata
        Metadata metadata = new Metadata();
        metadata.set("resourceName", file.getOriginalFilename());
        
        // Auto-detect parser for handling different file types
        AutoDetectParser parser = new AutoDetectParser();
        
        // Create parse context
        ParseContext parseContext = new ParseContext();
        
        // Parse the document
        try (InputStream stream = file.getInputStream()) {
            parser.parse(stream, handler, metadata, parseContext);
            return handler.toString().trim();
        }
    }
    
    /**
     * Validate if file is allowed (PDF or DOCX)
     */
    public boolean isFileAllowed(MultipartFile file) throws IOException {
        String mimeType = tika.detect(file.getInputStream());
        return mimeType.equals("application/pdf") || 
               mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }
}