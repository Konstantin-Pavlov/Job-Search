package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vacancies")
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
    public ResponseEntity<?> getVacanciesUserResponded(@PathVariable Integer user_id){
        List<VacancyDto> vacancies = vacancyService.getVacanciesUserResponded(user_id);
        if (vacancies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("vacancies with user_id %d not found", user_id));
        }
        return ResponseEntity.ok(vacancies);
    }

    //    http://localhost:8089/vacancies/add
    @PostMapping("add")
    public HttpStatus add(@RequestBody VacancyDto vacancyDto) {
        vacancyService.addVacancy(vacancyDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Integer id) {
        if (vacancyService.deleteVacancy(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
