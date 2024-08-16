package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("apiVacancyController")
@RequestMapping("api/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping()
    public ResponseEntity<List<VacancyDto>> getVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getVacancyById(@PathVariable long id) {
        VacancyDto vacancy = vacancyService.getVacancyById(id);
        if (vacancy == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Vacancy with id %d not found", id));
        }
        return ResponseEntity.ok(vacancy);
    }

    // http://localhost:8089/vacancies/get-user-vacancies/2
    @GetMapping("get-user-vacancies/{user_id}")
    public ResponseEntity<?> getVacanciesUserResponded(@PathVariable Integer user_id) {
        List<VacancyDto> vacancies = vacancyService.getVacanciesUserResponded(user_id);
        if (vacancies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("vacancies with user_id %d not found", user_id));
        }
        return ResponseEntity.ok(vacancies);
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

    //    http://localhost:8089/vacancies/add
    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return ResponseEntity.ok("vacancy is valid");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Integer id) {
        if (vacancyService.deleteVacancy(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
