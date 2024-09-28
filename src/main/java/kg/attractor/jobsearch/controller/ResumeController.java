package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.CategoriesService;
import kg.attractor.jobsearch.service.RespondedApplicantService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoriesService categoriesService;
    private final VacancyService vacancyService;
    private final RespondedApplicantService respondedApplicantService;

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {
//        model.addAttribute("_csrf", csrfToken);

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);

        // Add resume-related properties to the model
        model.addAttribute("resumeCreateTitle", bundle.getString("resume.createTitle"));
        model.addAttribute("resumeName", bundle.getString("resume.name"));
        model.addAttribute("resumeCategory", bundle.getString("resume.category"));
        model.addAttribute("resumeSalary", bundle.getString("resume.salary"));
        model.addAttribute("resumeIsActive", bundle.getString("resume.isActive"));
        model.addAttribute("resumeActiveYes", bundle.getString("resume.activeYes"));
        model.addAttribute("resumeActiveNo", bundle.getString("resume.activeNo"));
        model.addAttribute("resumeCreateButton", bundle.getString("resume.createButton"));
        model.addAttribute("resumeEditTitle", bundle.getString("resume.editTitle"));
        model.addAttribute("resumeApplicantId", bundle.getString("resume.applicantId"));
        model.addAttribute("resumeSaveButton", bundle.getString("resume.saveButton"));
        model.addAttribute("resumeAuthor", bundle.getString("resume.author"));
        model.addAttribute("resumeCreatedDate", bundle.getString("resume.createdDate"));
        model.addAttribute("resumeUpdatedDate", bundle.getString("resume.updatedDate"));
        model.addAttribute("resumeTitle", bundle.getString("resume.title"));
        model.addAttribute("resumeCount", bundle.getString("resume.count"));
        model.addAttribute("resumeLoggedInAs", bundle.getString("resume.loggedInAs"));
        model.addAttribute("resumeNotLoggedIn", bundle.getString("resume.notLoggedIn"));
        model.addAttribute("resumeDetails", bundle.getString("resume.details"));

        model.addAttribute("resumeButtonBack", bundle.getString("resume.button.back"));
        model.addAttribute("resumeButtonBackToProfile", bundle.getString("resume.button.backToProfile"));
        model.addAttribute("resumeButtonHome", bundle.getString("resume.button.home"));
        model.addAttribute("resumeButtonEdit", bundle.getString("resume.button.edit"));

        model.addAttribute("resumesRespondedNoResponses", bundle.getString("resumes.responded.noResponses"));
        model.addAttribute("resumesRespondedTitlePart1", bundle.getString("resumes.responded.title.part1"));
        model.addAttribute("resumesRespondedTitlePart2", bundle.getString("resumes.responded.title.part2"));
        model.addAttribute("resumesRespondedCardSalary", bundle.getString("resumes.responded.card.salary"));
        model.addAttribute("resumesRespondedCardMoreInfo", bundle.getString("resumes.responded.card.moreInfo"));
        model.addAttribute("resumesRespondedButtonBackToProfile", bundle.getString("resumes.responded.button.backToProfile"));
        model.addAttribute("resumesRespondedButtonHome", bundle.getString("resumes.responded.button.home"));

        model.addAttribute("resumeEditPopupTitle", bundle.getString("resume.edit.popup.title"));
        model.addAttribute("resumeEditPopupMessage1", bundle.getString("resume.edit.popup.message1"));
        model.addAttribute("resumeEditPopupMessage2", bundle.getString("resume.edit.popup.message2"));
    }

    @GetMapping()
    public String getResumes(Model model, Authentication authentication) {
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeService.getResumes(),
                "resumes");
        return "resumes/resumes";
    }

    @GetMapping("resumes-responded-to-employer-vacancies")
    public String getResumesRespondedToEmployerVacancies(Model model, Authentication authentication) {
        UserDto userDto = userService.getUserByEmail(authentication.getName());
        List<ResumeDto> resumes = resumeService.getResumes();
        List<VacancyDto> vacancies = vacancyService.getVacancies();
        List<VacancyDto> vacanciesByAuthorId = vacancyService.getVacancyByAuthorId(userDto.getId());
        List<ResumeDto> resumesRespondedToEmployerVacancies = resumeService.getResumesRespondedToEmployerVacancies(userDto.getId());
        List<RespondedApplicantDto> respondedApplicants = respondedApplicantService.getRespondedApplicants();
        Map<VacancyDto, List<ResumeDto>> respondedToVacanciesResumesMap = MvcControllersUtil.getRespondedToVacanciesResumesMap(
                vacanciesByAuthorId,
                resumesRespondedToEmployerVacancies,
                respondedApplicants
        );
        model.addAttribute("resumesRespondedToEmployerVacancies", resumesRespondedToEmployerVacancies);
        return "resumes/resumes_responded_to_employer_vacancies";
    }

    @GetMapping("{resumeId}")
    public String getInfo(@PathVariable Integer resumeId, Model model, Authentication authentication) {
        ResumeDto resumeDto = resumeService.getResumeById(resumeId);
        UserDto userDto = userService.getUserById(resumeDto.getApplicantId());
        CategoryDto categoryDto = categoriesService.getCategoryById(resumeDto.getCategoryId());
        model.addAttribute("userDto", userDto);
        model.addAttribute("categoryDto", categoryDto);
        model.addAttribute("resume", resumeDto);

        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeDto,
                "resumes"
        );
        return "resumes/resume_info";
    }

    @GetMapping("create")
    public String showCreateResumeForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            List<CategoryDto> categories = categoriesService.getCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("resumeDto", new ResumeDto());
            return "resumes/create_resume";
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }

    @PostMapping("create")
    public String createResume(
            @Valid
            @ModelAttribute("resumeDto")
            ResumeDto resumeDto,
            Authentication authentication,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            log.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("resumeDto", resumeDto); // Keep the form fields with the entered values
            return "resumes/create_resume";
        }
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            resumeDto.setApplicantId(userDto.getId());
            resumeDto.setCreatedDate(LocalDateTime.now());
            resumeDto.setUpdateTime(LocalDateTime.now());
            resumeService.addResume(resumeDto);
            model.addAttribute("successMessage", "Resume added successfully");
            return "redirect:/profile"; // Redirect to the profile
        }

        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }

    @GetMapping("edit/{resumeId}")
    public String showEditResumeForm(
            @PathVariable Integer resumeId,
            Model model,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            ResumeDto resumeDto = resumeService.getResumeById(resumeId);
            UserDto userDto = userService.getUserById(resumeDto.getApplicantId());
            List<CategoryDto> categories = categoriesService.getCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("resumeDto", resumeDto);
            model.addAttribute("userDto", userDto);
            return "resumes/edit_resume";
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }

    @PostMapping("edit/{resumeId}")
    public String editResume(
            @PathVariable Integer resumeId,
            @Valid @ModelAttribute("resumeDto") ResumeDto resumeDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) {
        List<CategoryDto> categories = categoriesService.getCategories();
        model.addAttribute("categories", categories);
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("resumeDto", resumeDto);
            return "resumes/edit_resume";
        }
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            resumeDto.setApplicantId(userDto.getId());
            resumeDto.setId(resumeId);
            resumeService.editResume(resumeDto);
            model.addAttribute("entityUpdated", true);
//            return "redirect:/profile"; // Redirect to the profile
            return "resumes/edit_resume";
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }

    @PostMapping("update/{resumeId}")
    public String updateResume(
            @PathVariable Integer resumeId,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (authentication != null && authentication.isAuthenticated()) {
            ResumeDto resumeDto = resumeService.getResumeById(resumeId);
            resumeService.updateResume(resumeId);

            redirectAttributes.addFlashAttribute("ifEntityUpdated", true);
            redirectAttributes.addFlashAttribute("entityTitle", "resume");
            redirectAttributes.addFlashAttribute("entityName", resumeDto.getName());
            return "redirect:/profile";
        }
        return "redirect:/auth/login";
    }

}
