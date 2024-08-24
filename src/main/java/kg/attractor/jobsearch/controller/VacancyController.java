package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.CategoriesService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Slf4j
@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final UserService userService;
    private final CategoriesService categoriesService;

    @GetMapping()
    public String getVacancies(Model model, Authentication authentication) {
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancies(),
                "vacancies");
        return "vacancies/vacancies";
    }

    @GetMapping("{vacancyId}")
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

    @GetMapping("create")
    public String showCreateResumeForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            List<CategoryDto> categories = categoriesService.getCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("vacancyDto", new VacancyDto());
            return "vacancies/create_vacancy";
        }
        return "redirect:/login"; // Redirect to login if not authenticated
    }

    @PostMapping("create")
    public String createResume(
            @Valid
            @ModelAttribute("vacancyDto")
            VacancyDto vacancyDto,
            Authentication authentication,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            log.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("vacancyDto", vacancyDto); // Keep the form fields with the entered values
            return "vacancies/create_vacancy";
        }
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            vacancyDto.setAuthorId(userDto.getId());
            vacancyDto.setCreatedDate(LocalDateTime.now());
            vacancyDto.setUpdateTime(LocalDateTime.now());
            vacancyService.createVacancy(vacancyDto);
            model.addAttribute("successMessage", "vacancy added successfully");
            return "redirect:/auth/profile"; // Redirect to the profile
        }

        return "redirect:/login"; // Redirect to login if not authenticated
    }

    @GetMapping("edit/{vacancyId}")
    public String showEditVacancyForm(
            @PathVariable Integer vacancyId,
            Model model,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            VacancyDto vacancyDto = vacancyService.getVacancyById(vacancyId);
            UserDto userDto = userService.getUserById(vacancyDto.getAuthorId());
            List<CategoryDto> categories = categoriesService.getCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("vacancyDto", vacancyDto);
            model.addAttribute("userDto", userDto);
            return "vacancies/edit_vacancy";
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }

    @PostMapping("edit/{vacancyId}")
    public String editVacancy(
            @PathVariable Integer vacancyId,
            @Valid @ModelAttribute("vacancyDto") VacancyDto vacancyDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("vacancyDto", vacancyDto);
            return "vacancies/edit_vacancy";
        }
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            vacancyDto.setAuthorId(userDto.getId());
            vacancyDto.setId(vacancyId);
            vacancyService.editVacancy(vacancyDto);
            model.addAttribute("successMessage", "Profile updated successfully");
            return "redirect:/auth/profile"; // Redirect to the profile
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }

    @PostMapping("update/{vacancyId}")
    public String updateVacancy(
            @PathVariable Integer vacancyId,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (authentication != null && authentication.isAuthenticated()) {
            VacancyDto vacancyDto = vacancyService.getVacancyById(vacancyId);
            vacancyService.updateVacancy(vacancyId);

            redirectAttributes.addFlashAttribute("ifEntityUpdated", true);
            redirectAttributes.addFlashAttribute("entityTitle", "vacancy");
            redirectAttributes.addFlashAttribute("entityName", vacancyDto.getName());
            return "redirect:/auth/profile";
        }
        return "redirect:/auth/login";
    }

}
