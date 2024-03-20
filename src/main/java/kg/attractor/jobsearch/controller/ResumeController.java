package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("resumes")
    public ResponseEntity<List<ResumeDto>> getResumes() {
        return ResponseEntity.ok(resumeService.getResumes());
    }

    @GetMapping("resumes/{id}")
    public ResponseEntity<?> getDirectorById(@PathVariable long id) {
        ResumeDto director = resumeService.getResumeById(id);
        if (director == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("resume with id %d not found", id));
        }
        return ResponseEntity.ok(director);
    }
}
