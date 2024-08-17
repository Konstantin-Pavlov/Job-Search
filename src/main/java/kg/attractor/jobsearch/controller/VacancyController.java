package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.DateTimeUtil;
import kg.attractor.jobsearch.util.VacancyAndResumeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping()
    public String getVacancies(Model model, Authentication authentication) {
        VacancyAndResumeUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancies(),
                "vacancies");
        return "vacancies/index";
    }

    @GetMapping("vacancies/{vacancyId}")
    public String getInfo(@PathVariable long vacancyId, Model model, Authentication authentication) {
        VacancyAndResumeUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancyById(vacancyId),
                "vacancy"
        );
        return "vacancies/vacancy_info";
    }
}
