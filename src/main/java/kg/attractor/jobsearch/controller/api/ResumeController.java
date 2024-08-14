package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resumes")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResumeController {

    ResumeService resumeService;

    @GetMapping
    public ResponseEntity<List<ResumeDto>> getResumes() {
        return ResponseEntity.ok(resumeService.getResumes());
    }

    //    http://localhost:8089/resumes/id/4
    @GetMapping("id/{id}")
    public ResponseEntity<?> getResumeById(@PathVariable long id) {
        ResumeDto resume = resumeService.getResumeById(id);
        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("resume with id %d not found", id));
        }
        return ResponseEntity.ok(resume);
    }

    //    http://localhost:8089/resumes/category/2
    @GetMapping("category/{category_id}")
    public ResponseEntity<?> getResumeByCategoryId(@PathVariable Integer category_id) {
        ResumeDto resume = resumeService.getResumeByCategoryId(category_id);
        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("resume with category_id %d not found", category_id));
        }
        return ResponseEntity.ok(resume);
    }

    //    http://localhost:8089/resumes/search-by-user/2
    @GetMapping("search-by-user/{user_id}")
    public ResponseEntity<?> getResumeByUserId(@PathVariable Integer user_id) {
        List<ResumeDto> resumes = resumeService.getResumeByUserId(user_id);
        if (resumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("resumes with user_id %d not found", user_id));
        }
        return ResponseEntity.ok(resumes);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return ResponseEntity.ok("resume is valid");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Integer id) {
        if (resumeService.deleteResume(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

