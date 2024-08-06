package kg.attractor.jobsearch.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employer")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployerController {
    //    @Autowired - no need if field is final and @RequiredArgsConstructor annotation is used
    UserService employerService;
    VacancyService vacancyService;
    ResumeService resumeService;

    @GetMapping("/applicant/{id}")
    public ResponseEntity<?> getApplicantById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(employerService.getUserById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

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

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto) {
        userDto.setAccountType("employer");
        employerService.addUser(userDto);
        return ResponseEntity.ok("user is valid");
    }

    @PostMapping("/vacancy")
    public ResponseEntity<String> createVacancy(@Valid @RequestBody VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vacancy created successfully");
        //  return ResponseEntity.ok("vacancy is valid");
    }

    @PutMapping("/vacancy/{id}")
    public ResponseEntity<String> editVacancy(@PathVariable Long id, @RequestBody VacancyDto vacancyDto) {
        vacancyService.editVacancy(id, vacancyDto);
        return ResponseEntity.ok("Vacancy edited successfully");
    }

    @DeleteMapping("/vacancy/{id}")
    public ResponseEntity<String> deleteVacancy(@PathVariable Integer id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.ok("Vacancy deleted successfully");
    }

}

