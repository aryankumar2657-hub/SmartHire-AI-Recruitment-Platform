package smarthire_backend.controller;

import smarthire_backend.service.AiResumeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin("*")
public class AiResumeController {

    private final AiResumeService aiResumeService;

    public AiResumeController(AiResumeService aiResumeService) {
        this.aiResumeService = aiResumeService;
    }

    @PostMapping("/resume-analysis")
    public String analyzeResume(@RequestBody Map<String, String> body) {
        try {
            String resumeText = body.get("resumeText");

            if (resumeText == null || resumeText.isBlank()) {
                return "Please provide resume text.";
            }

            return aiResumeService.analyzeResume(resumeText);

        } catch (Exception e) {
            return "AI Controller Error: " + e.getMessage();
        }
    }
}