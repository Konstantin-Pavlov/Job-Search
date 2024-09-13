package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final UserService userService;

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {
        model.addAttribute("_csrf", csrfToken);

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
    }

    @GetMapping()
    public String profile(
            Model model,
            Principal principal,
            Authentication authentication) throws IOException {

        // Add the flag to the model if it exists
        Boolean ifEntityUpdated = (Boolean) model.asMap().get("ifEntityUpdated");
        log.info("ifEntityUpdated: {}", ifEntityUpdated);
        if (ifEntityUpdated != null) {
            String entityTitle = (String) model.asMap().get("entityTitle");
            String entityName = (String) model.asMap().get("entityName");

            model.addAttribute("entityUpdated", ifEntityUpdated);
            model.addAttribute("entityTitle", entityTitle);
            model.addAttribute("entityName", entityName);
        }

        if (principal == null) {
            log.error("Principal is null. User is not authenticated.");
            return "redirect:/auth/login";
        }
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
        }
        log.info("isAuthenticated: {}", isAuthenticated);
        log.info("Fetching profile for user: {}", principal.getName());

        UserDto userDto = userService.getUserByEmail(principal.getName());


        List<ResumeDto> resumes = resumeService.getResumeByUserId(userDto.getId());
        List<ResumeDto> resumesRespondedToEmployerVacancies = resumeService.getResumesRespondedToEmployerVacancies(userDto.getId());

        List<VacancyDto> vacanciesUserResponded = vacancyService.getVacanciesUserResponded(userDto.getId());
        List<VacancyDto> vacancies = vacancyService.getVacancyByAuthorId(userDto.getId());

        model.addAttribute("userDto", userDto);
        model.addAttribute("resumesRespondedToEmployerVacancies", resumesRespondedToEmployerVacancies);
        model.addAttribute("vacanciesUserResponded", vacanciesUserResponded);
        model.addAttribute("userResumes", resumes);
        model.addAttribute("userVacancies", vacancies);
        return "auth/profile";
    }

    @GetMapping("edit")
    public String showEditProfileForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            model.addAttribute("user", userDto);
            return "auth/profile-edit";
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }

    @PostMapping("edit")
    public String editProfile(
            @Valid @ModelAttribute("user") UserWithAvatarFileDto userWithAvatarFileDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) throws IOException {
            model.addAttribute("userDto", userWithAvatarFileDto);
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "auth/profile-edit";
        }
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            userWithAvatarFileDto.setEnabled(userDto.isEnabled());
            userWithAvatarFileDto.setId(userDto.getId());
            userService.updateUser(userWithAvatarFileDto);
            model.addAttribute("successMessage", "Profile updated successfully");
            model.addAttribute("hasUpdated", true);
            model.addAttribute("entityName", userDto.getName());
//            return "redirect:/profile"; // Redirect to the profile
            return "auth/profile-edit";
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }
}
