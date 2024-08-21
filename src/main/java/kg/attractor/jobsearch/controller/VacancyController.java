package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping("vacancies")
    public String getVacancies(Model model, Authentication authentication) {
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancies(),
                "vacancies");
        return "vacancies/vacancies";
    }

    @GetMapping("vacancies/{vacancyId}")
    public String getInfo(@PathVariable Integer vacancyId, Model model, Authentication authentication) {
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancyById(vacancyId),
                "vacancy"
        );
        return "vacancies/vacancy_info";
    }
}
