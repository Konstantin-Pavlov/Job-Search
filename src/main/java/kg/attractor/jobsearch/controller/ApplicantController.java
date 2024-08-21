package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
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

    @GetMapping("/employer/{id}")
    public ResponseEntity<?> getEmployerById(@PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(applicantService.getUserById(id));
    }

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable Integer userId) {
        try {
            Resource resource = applicantService.getAvatarFileResource(userId);
            String contentType = applicantService.getContentType(resource);
            log.info("get avatar : {}", contentType);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) {
        try {
            applicantService.uploadAvatar(userId, file);
            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload avatar");
        }
    }

    @PostMapping("/resume")
    public ResponseEntity<String> createResume(@Valid @RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Resume created successfully");
    }

    @PostMapping("/vacancy/{vacancyId}/apply")
    public ResponseEntity<String> applyForVacancy(@PathVariable Long vacancyId) {
        applicantService.applyForVacancy(vacancyId);
        return ResponseEntity.ok("Applied to vacancy successfully");
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

}
