package smarthire_backend.service;

import org.springframework.stereotype.Service;

@Service
public class AiResumeService {

    public String analyzeResume(String resumeText) {

        String text = resumeText.toLowerCase();

        String role = "General Software Developer";
        int score = 70;

        if (text.contains("java") || text.contains("spring boot")) {
            role = "Java Backend / Full Stack Developer";
            score = 88;
        } else if (text.contains("react") || text.contains("javascript")) {
            role = "Frontend React Developer";
            score = 82;
        } else if (text.contains("mysql") || text.contains("database")) {
            role = "Backend Developer";
            score = 78;
        }

        return
                "1. Resume Summary:\n" +
                "Candidate profile has been analyzed based on provided skills and experience.\n\n" +

                "2. Extracted Skills:\n" +
                resumeText + "\n\n" +

                "3. Suitable Job Role:\n" +
                role + "\n\n" +

                "4. Job Match Score:\n" +
                score + "/100\n\n" +

                "5. Shortlist Recommendation:\n" +
                "Candidate can be considered for the next screening round.\n\n" +

                "6. Improvement Suggestions:\n" +
                "Add measurable project impact, GitHub links, deployment links, and stronger technical keywords.";
    }
}