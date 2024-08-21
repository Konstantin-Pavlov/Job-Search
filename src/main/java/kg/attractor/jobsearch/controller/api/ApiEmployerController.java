package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/employer")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiEmployerController {
    //    @Autowired - no need if field is final and @RequiredArgsConstructor annotation is used
    UserService employerService;
    VacancyService vacancyService;
    ResumeService resumeService;

    @GetMapping("/resumes")
    public ResponseEntity<List<ResumeDto>> getAllResumes() {
        return ResponseEntity.ok(resumeService.getResumes());
    }

    @GetMapping("/resumes/category/{category}")
    public ResponseEntity<?> getResumesByCategory(@PathVariable String category) {
        List<ResumeDto> resumes = resumeService.getResumeByCategory(category);
        if (resumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("resumes with category %s not found", category));
        }
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/vacancy/{vacancyId}/applicants")
    public ResponseEntity<?> getApplicantsRespondedToVacancy(@PathVariable Integer vacancyId) {
        List<UserDto> users = employerService.getUsersRespondedToVacancy(vacancyId);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("users with vacancy id %d not found", vacancyId));
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/applicant/{id}")
    public ResponseEntity<?> getApplicantById(@PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(employerService.getUserById(id));
    }

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable Integer userId) {
        try {
            return (ResponseEntity<Resource>) employerService.getAvatar(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{userId}/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) {
        try {
            employerService.saveAvatar(userId, file);
            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload avatar");
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/vacancy")
    public ResponseEntity<String> createVacancy(@Valid @RequestBody VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vacancy created successfully");
        //  return ResponseEntity.ok("vacancy is valid");
    }

    @PutMapping("/vacancy/{id}")
    public ResponseEntity<String> editVacancy(@PathVariable Integer id, @RequestBody VacancyDto vacancyDto) {
        vacancyService.editVacancy(id, vacancyDto);
        return ResponseEntity.ok("Vacancy edited successfully");
    }

    @DeleteMapping("/vacancy/{id}")
    public ResponseEntity<String> deleteVacancy(@PathVariable Integer id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.ok("Vacancy deleted successfully");
    }

}

