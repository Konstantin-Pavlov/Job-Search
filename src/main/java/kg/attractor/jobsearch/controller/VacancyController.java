package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.CategoriesService;
import kg.attractor.jobsearch.service.UserService;
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
    private final UserService userService;
    private final CategoriesService categoriesService;

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
        VacancyDto vacancy = vacancyService.getVacancyById(vacancyId);
        UserDto userDto = userService.getUserById(vacancy.getAuthorId());
        CategoryDto categoryDto = categoriesService.getCategoryById(vacancy.getCategoryId());

        model.addAttribute("user", userDto);
        model.addAttribute("category", categoryDto);
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancy,
                "vacancy"
        );
        return "vacancies/vacancy_info";
    }
}
