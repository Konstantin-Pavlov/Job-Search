package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.DateTimeUtil;
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
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("vacancies", vacancyService.getVacancies());
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
        }
        return "vacancies/index";
    }

    @GetMapping("vacancies/{vacancyId}")
    public String getInfo(@PathVariable long vacancyId, Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        VacancyDto vacancyDto = vacancyService.getVacancyById(vacancyId);
        model.addAttribute("vacancy", vacancyDto);
        model.addAttribute("formattedCreateDate", DateTimeUtil.getFormattedDate(vacancyDto.getCreatedDate()));
        model.addAttribute("formattedUpdateDate", DateTimeUtil.getFormattedDate(vacancyDto.getUpdateTime()));
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
        }
        return "vacancies/vacancy_info";
    }
}
