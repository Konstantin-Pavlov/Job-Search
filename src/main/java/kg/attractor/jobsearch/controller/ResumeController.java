package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping
    public ResponseEntity<List<ResumeDto>> getResumes() {
        return ResponseEntity.ok(resumeService.getResumes());
    }

    //    http://localhost:8089/resumes/id/4
    @GetMapping("id/{id}")
    public ResponseEntity<?> getDirectorById(@PathVariable long id) {
        ResumeDto director = resumeService.getResumeById(id);
        if (director == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("resume with id %d not found", id));
        }
        return ResponseEntity.ok(director);
    }

    //    http://localhost:8089/resumes/category/2
    @GetMapping("category/{category_id}")
    public ResponseEntity<?> getResumeByCategoryId(@PathVariable Integer category_id) {
        ResumeDto resume = resumeService.getResumeByCategoryId(category_id);
        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("resume with category_id %d not found", category_id));
        }
        return ResponseEntity.ok(resume);
    }

    @PostMapping("add")
    public HttpStatus add(@RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Integer id) {
        if (resumeService.deleteResume(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

