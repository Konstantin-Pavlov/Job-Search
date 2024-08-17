package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.util.VacancyAndResumeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping("resumes")
    public String getResumes(Model model, Authentication authentication) {
        VacancyAndResumeUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeService.getResumes(),
                "resumes");
        return "resumes/resumes";
    }

    @GetMapping("resumes/{resumeId}")
    public String getInfo(@PathVariable long resumeId, Model model, Authentication authentication) {
        VacancyAndResumeUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeService.getResumeById(resumeId),
                "resume"
        );
        return "resumes/resume_info";
    }

}
