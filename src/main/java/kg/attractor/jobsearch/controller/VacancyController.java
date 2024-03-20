package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    @GetMapping("vacancies")
    public ResponseEntity<List<VacancyDto>> getVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
    }

    @GetMapping("vacancies/{id}")
    public ResponseEntity<?> getVacancyById(@PathVariable long id) {
        VacancyDto vacancy = vacancyService.getVacancyById(id);
        if (vacancy == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Vacancy with id %d not found", id));
        }
        return ResponseEntity.ok(vacancy);
    }
}
