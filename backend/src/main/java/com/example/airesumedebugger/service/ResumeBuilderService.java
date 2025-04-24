package com.example.airesumedebugger.service;

import com.example.airesumedebugger.model.Resume;
import com.example.airesumedebugger.model.ResumeSection;
import com.example.airesumedebugger.model.ResumeBulletPoint;
import com.example.airesumedebugger.model.FeedbackItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This service takes in the FileUploadResponse output 'response' and builds a
 * resume object with key sections
 * i.e. Header/Contact Info, Summary, Experience, Education, Projects, Skills
 */

@Service
public class ResumeBuilderService {

    // Common section titles with different variations
    private static final Map<String, List<String>> SECTION_PATTERNS = Map.of(
            "Contact", List.of("contact info(rmation)?", "personal info(rmation)?", "header"),
            "Summary", List.of("summary", "professional summary", "profile", "objective", "about( me)?"),
            "Experience", List.of("experience", "work experience", "employment( history)?", "professional experience"),
            "Education", List.of("education(al background)?", "academic background", "qualifications"),
            "Projects", List.of("projects", "personal projects", "key projects", "relevant projects"),
            "Skills", List.of("skills", "technical skills", "core( competencies)?", "areas of expertise"));

    // logic to identify bullet points
    private static final Pattern BULLET_PATTERN = Pattern.compile("^\\s*[•●○◦\\-\\*\\+]\\s+(.+)$");
    private static final Pattern NUMBERED_BULLET_PATTERN = Pattern.compile("^\\s*\\d+\\.\\s+(.+)$");

    // List of weak action verbs to check against
    // Will probably add more
    private static final Set<String> WEAK_VERBS = new HashSet<>(Arrays.asList(
            "helped", "worked", "participated", "contributed", "assited", "was part", "responsible for",
            "handled", "managed", "tasked with", "supported", "involved in", "took part", "engaged in"));

    /**
     * Builds a structured Resume object from extracted text
     * 
     * @param extractedText Raw text extracted from resume file
     * @return Structured Resume object with identified sections and bullet points
     */
    public Resume buildResume(String extractedText) {
        if (extractedText == null || extractedText.trim().isEmpty()) {
            throw new IllegalArgumentException("Extracted text cannot be empty");
        }

        Resume resume = new Resume();
        resume.setRawText(extractedText);

        // Parse and identify key sections
        // key is a string like education or skill
        // returns ResumeSection object
        Map<String, ResumeSection> sections = identifySections(extractedText);

        // Set sections in the resume object
        populateResumeSections(resume, sections);

        // generate feedback
        List<FeedbackItem> feedback = generateWeakVerbFeedback(resume);
        resume.setFeedbackItems(feedback);

        return resume;
    }

    // Identify sections in the resume text
    private Map<String, ResumeSection> identifySections(String extractedText) {
        // I use a hashmap but a linked hashmap keeps track of insertion of sections
        Map<String, ResumeSection> sections = new LinkedHashMap<>();

        // Split the resume into individual lines
        /*
         * lines = [
         * "John Doe",
         * "Email: john@example.com",
         * "Phone: 123-456-7890",
         * "Education",
         * "UMBC, B.S. Computer Science, 2024",
         * "Experience",
         * "Intern at Google",
         * "Skills",
         * "Java, Python, SQL"
         * ];
         */
        String[] lines = extractedText.split("\\r?\\n");

        // Find section headers and their line numbers
        // Will match a regex to SECTION_PATTERNS
        /*
         * sectionHeaders = {
         * 3: "Education",
         * 5: "Experience",
         * 7: "Skills"
         * }
         */
        Map<Integer, String> sectionHeaders = findSectionHeaders(lines);

        // Create resume object and extract section content aka what's in experience,
        // projects, etc.
        // In top to bottom order
        List<Integer> headerLines = new ArrayList<>(sectionHeaders.keySet());
        Collections.sort(headerLines);

        /*
         * Lines 4 to 4 → education content
         * Lines 6 to 6 → experience content
         * Lines 8 to end → skills content
         */

        // Special case: extract contact info at the beginning if not explicitly marked
        if (headerLines.isEmpty() || headerLines.get(0) > 3) {
            // If there are no headers at all, assume the whole resume is one section
            int endLine = headerLines.isEmpty() ? lines.length : headerLines.get(0);

            // I'll loop through the whole block of code and just make it one big text chunk
            StringBuilder contactContent = new StringBuilder();
            for (int i = 0; i < endLine; i++) {
                contactContent.append(lines[i]).append("\n");
            }

            ResumeSection contactSection = new ResumeSection();
            contactSection.setTitle("Contact");
            contactSection.setRawText(contactContent.toString().trim());
            contactSection.setStartLine(0);
            contactSection.setEndLine(endLine - 1);

            // Add bullet points (in this case, individual lines of contact info)
            identifyBulletPoints(contactSection, 0);

            sections.put("Contact", contactSection);
        }

        // Logic to identify each section
        // Loop through each identified section header (sorted)
        for (int i = 0; i < headerLines.size(); i++) {
            int startLine = headerLines.get(i);
            int endLine = (i < headerLines.size() - 1) ? headerLines.get(i + 1) : lines.length; // where next header is

            String title = sectionHeaders.get(startLine);

            StringBuilder content = new StringBuilder();
            // Skip the header line itself
            for (int j = startLine + 1; j < endLine; j++) {
                content.append(lines[j]).append("\n");
            }

            ResumeSection section = new ResumeSection();
            section.setTitle(title);
            section.setRawText(content.toString().trim());
            section.setStartLine(startLine + 1); // +1 to skip header
            section.setEndLine(endLine - 1);

            // Identify bullet points within this section
            identifyBulletPoints(section, section.getStartLine());

            sections.put(title, section);

            /*
             * Section map looks like:
             * {
             * "Education": ResumeSection(...),
             * "Skills": ResumeSection(...),
             * "Experience": ResumeSection(...)
             * }
             */
        }

        return sections;
    }

}
