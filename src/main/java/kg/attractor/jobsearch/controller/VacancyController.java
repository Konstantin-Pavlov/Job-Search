package kg.attractor.jobsearch.controller;

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
