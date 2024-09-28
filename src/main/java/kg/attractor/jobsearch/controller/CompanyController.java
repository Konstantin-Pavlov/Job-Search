package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@AllArgsConstructor
@Controller
public class CompanyController {
    private final UserService userService;
    private final VacancyService vacancyService;

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {
//        model.addAttribute("_csrf", csrfToken);

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
    }

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