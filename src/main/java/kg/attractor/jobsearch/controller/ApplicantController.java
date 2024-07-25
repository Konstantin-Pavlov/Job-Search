package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApplicantController {
    //    @Autowired - no need if field is final and @RequiredArgsConstructor annotation is used
    UserService applicantService;
    VacancyService vacancyService;
    ResumeService resumeService;

    @GetMapping("/vacancies")
    public ResponseEntity<List<VacancyDto>> getAllActiveVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
    }

    @GetMapping("/vacancies/category/{category}")
    public ResponseEntity<List<?>> getVacanciesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(category));
    }

    // http://localhost:8089/applicant/get-vacancies-by-category/1
    @GetMapping("get-vacancies-by-category/{category_id}")
    public ResponseEntity<?> getVacanciesByCategoryId(@PathVariable Integer category_id) {
        List<VacancyDto> vacancies = vacancyService.getVacanciesByCategoryId(category_id);
        if (vacancies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("vacancies with category id %d not found", category_id));
        }
        return ResponseEntity.ok(vacancies);
    }

    @PostMapping("/resume")
    public ResponseEntity<String> createResume(@Valid @RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Resume created successfully");
    }

    @PutMapping("/resume/{id}")
    public ResponseEntity<String> editResume(@PathVariable Integer id, @RequestBody ResumeDto resumeDto) {
        resumeService.editResume(id, resumeDto);
        return ResponseEntity.ok("Resume edited successfully");
    }

    @DeleteMapping("/resume/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable Integer id) {
        if (resumeService.deleteResume(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok("Resume deleted successfully");
    }

    @PostMapping("/vacancy/{vacancyId}/apply")
    public ResponseEntity<String> applyForVacancy(@PathVariable Long vacancyId) {
        applicantService.applyForVacancy(vacancyId);
        return ResponseEntity.ok("Applied to vacancy successfully");
    }
}
