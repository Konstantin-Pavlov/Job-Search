package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@AllArgsConstructor
@Controller
public class CompanyController {
    private final UserService userService;
    private final VacancyService vacancyService;

    @GetMapping("/company/{email}")
    public String getCompanyInfo(@PathVariable String email, Model model, Authentication authentication) throws UserNotFoundException {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(email);
            List<VacancyDto> companyVacancies = vacancyService.getVacancyByAuthorId(userDto.getId());

            model.addAttribute("userDto", userDto);
            model.addAttribute("companyVacancies", companyVacancies);

            return "/vacancies/company-info";
        }

        return "redirect:/auth/login";
    }
}