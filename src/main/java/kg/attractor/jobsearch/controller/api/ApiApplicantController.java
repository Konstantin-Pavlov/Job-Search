package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
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

@RestController
@RequestMapping("api/applicant")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiApplicantController {
    //    @Autowired - no need if field is final and @RequiredArgsConstructor annotation is used
    UserService applicantService;
    VacancyService vacancyService;
    ResumeService resumeService;

    @GetMapping("show-all-users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(applicantService.getUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(applicantService.getUserById(id));
    }

    //http://localhost:8089/name?name=John
    @GetMapping("name")
    private ResponseEntity<?> getUserByName(
            @RequestParam(name = "name")
            String name) {
        try {
            List<UserDto> users = applicantService.getUsersByName(name);
            return ResponseEntity.ok(users);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //   http://localhost:8089/phone?phone=1234567890
    @GetMapping("phone")
    private ResponseEntity<?> getUserByPhone(
            @RequestParam("phone")
            String phone) {
        try {
            UserDto user = applicantService.getUserByPhone(phone);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //http://localhost:8089/applicant/email?email=jane@example.com
    @GetMapping("email")
    private ResponseEntity<?> getUserByEmail(
            @RequestParam("email") String email) {
        try {
            UserDto user = applicantService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //    http://localhost:8089/resumes/category/2
    @GetMapping("category/{category_id}")
    public ResponseEntity<?> getResumeByCategoryId(@PathVariable Integer category_id) {
        List<ResumeDto> resumes = resumeService.getResumesByCategoryId(category_id);
        if (resumes == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("s with category_id %d not found", category_id));
        }
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/resumes/category/{category}")
    public ResponseEntity<List<?>> getResumesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(resumeService.getResumeByCategory(category));
    }

    @GetMapping("/vacancies/category/{category}")
    public ResponseEntity<List<?>> getVacanciesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(category));
    }

    //    http://localhost:8089/resumes/get-user-resumes/2
    @GetMapping("get-user-resumes/{user_id}")
    public ResponseEntity<?> getResumesByUserId(@PathVariable Integer user_id) {
        List<ResumeDto> resumes = resumeService.getResumeByUserId(user_id);
        if (resumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("resumes with user_id %d not found", user_id));
        }
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/vacancies")
    public ResponseEntity<List<VacancyDto>> getAllActiveVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
    }

    // http://localhost:8089/vacancies/get-vacancies-by-category/1
    @GetMapping("get-vacancies-by-category/{category_id}")
    public ResponseEntity<?> getVacanciesByCategoryId(@PathVariable Integer category_id) {
        List<VacancyDto> vacancies = vacancyService.getVacanciesByCategoryId(category_id);
        if (vacancies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("vacancies with category id %d not found", category_id));
        }
        return ResponseEntity.ok(vacancies);
    }

    //  http://localhost:8089/api/applicant/get-user-vacancies/1
    @GetMapping("get-vacancies-user-responded/{user_id}")
    public ResponseEntity<?> getVacanciesUserResponded(@PathVariable Integer user_id) {
        List<VacancyDto> vacancies = vacancyService.getVacanciesUserResponded(user_id);
        if (vacancies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("vacancies with user_id %d not found", user_id));
        }
        return ResponseEntity.ok(vacancies);
    }

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<?> getAvatar(@PathVariable Integer userId) throws UserNotFoundException {
        return applicantService.getAvatar(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto) {
        userDto.setAccountType("applicant");
        applicantService.addUser(userDto);
        return ResponseEntity.ok("user is valid");
    }

    @PostMapping("/add-with-avatar")
    public ResponseEntity<?> add(
            @RequestParam("name") String name,
            @RequestParam("age") Integer age,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws UserNotFoundException, IOException {

        UserWithAvatarFileDto userWithAvatarFileDto = UserWithAvatarFileDto.builder()
                .name(name)
                .age(age)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .avatar(avatar)
                .accountType("applicant")
                .enabled(true)
                .build();

        applicantService.addUserWithAvatar(userWithAvatarFileDto);
        return ResponseEntity.ok("User is valid");
    }

    @PostMapping("/{userId}/upload-avatar")
    public ResponseEntity<String> saveAvatar(@PathVariable Integer userId, @RequestParam("file") MultipartFile image) throws UserNotFoundException, IOException {
        applicantService.saveAvatar(userId, image);
        return new ResponseEntity<>("Avatar uploaded successfully", HttpStatus.OK);
    }

//    @PostMapping("/{userId}/upload-avatar")
//    public ResponseEntity<String> uploadAvatar(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) {
//        try {
//            applicantService.uploadAvatar(userId, file);
//            return ResponseEntity.ok("Avatar uploaded successfully");
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Failed to upload avatar");
//        }
//    }


    @PostMapping("/resume")
    public ResponseEntity<String> createResume(@Valid @RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Resume created successfully");
    }

    @PostMapping("/vacancy/{vacancyId}/apply")
    public ResponseEntity<String> applyForVacancy(@PathVariable Integer vacancyId, @RequestBody ResumeDto resumeDto) {
        applicantService.applyForVacancy(vacancyId, resumeDto);
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
