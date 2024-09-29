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
import kg.attractor.jobsearch.util.ConsoleColors;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
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

        // Add vacancy-related properties to the model
        model.addAttribute("vacanciesTitle", bundle.getString("vacancies.title"));
        model.addAttribute("vacancyDescription", bundle.getString("vacancy.description"));
        model.addAttribute("vacancyCategory", bundle.getString("vacancy.category"));
        model.addAttribute("vacancySalary", bundle.getString("vacancy.salary"));
        model.addAttribute("vacancyExperience", bundle.getString("vacancy.experience"));
        model.addAttribute("vacancyExperience", bundle.getString("vacancy.experience"));
        model.addAttribute("vacancyExperienceFrom", bundle.getString("vacancy.experience.from"));
        model.addAttribute("vacancyExperienceTo", bundle.getString("vacancy.experience.to"));
        model.addAttribute("vacancyExperienceYears", bundle.getString("vacancy.experience.years"));
        model.addAttribute("vacancyIsActive", bundle.getString("vacancy.isActive"));
        model.addAttribute("vacancyActiveYes", bundle.getString("vacancy.activeYes"));
        model.addAttribute("vacancyActiveNo", bundle.getString("vacancy.activeNo"));
        model.addAttribute("vacancyCreatedDate", bundle.getString("vacancy.createdDate"));
        model.addAttribute("vacancyUpdatedDate", bundle.getString("vacancy.updatedDate"));
        model.addAttribute("vacancyDetails", bundle.getString("vacancy.details"));
        model.addAttribute("vacancyAlreadyRespondedToVacancy", bundle.getString("vacancy.alreadyRespondedToVacancy"));
        model.addAttribute("vacancySelectResume", bundle.getString("vacancy.selectResume"));
        model.addAttribute("vacancyCompany", bundle.getString("vacancy.company"));
        model.addAttribute("vacancyBackToProfile", bundle.getString("vacancy.button.backToProfile"));
        model.addAttribute("vacancyRespondedNoVacancies", bundle.getString("vacancies.user.responded.noVacancies"));
        model.addAttribute("vacancyUserRespondedTo", bundle.getString("vacancies.user.responded.youHaveResponded"));
        model.addAttribute("vacanciesVacancies", bundle.getString("vacancies.user.responded.vacancies"));

        model.addAttribute("vacancyCreateTitle", bundle.getString("vacancy.create.title"));
        model.addAttribute("vacancyCreateErrorMessage", bundle.getString("vacancy.create.errorMessage"));
        model.addAttribute("vacancyCreateUpdatedMessage", bundle.getString("vacancy.create.updatedMessage"));
        model.addAttribute("vacancyCreateUpdatedYes", bundle.getString("vacancy.create.updatedYes"));
        model.addAttribute("vacancyCreateUpdatedNo", bundle.getString("vacancy.create.updatedNo"));
        model.addAttribute("vacancyCreateLabelName", bundle.getString("vacancy.create.label.name"));
        model.addAttribute("vacancyCreateLabelDescription", bundle.getString("vacancy.create.label.description"));
        model.addAttribute("vacancyCreateLabelCategory", bundle.getString("vacancy.create.label.category"));
        model.addAttribute("vacancyCreateLabelSalary", bundle.getString("vacancy.create.label.salary"));
        model.addAttribute("vacancyCreateLabelExpFrom", bundle.getString("vacancy.create.label.expFrom"));
        model.addAttribute("vacancyCreateLabelExpTo", bundle.getString("vacancy.create.label.expTo"));
        model.addAttribute("vacancyCreateLabelIsActive", bundle.getString("vacancy.create.label.isActive"));
        model.addAttribute("vacancyCreateErrorName", bundle.getString("vacancy.create.error.name"));
        model.addAttribute("vacancyCreateErrorDescription", bundle.getString("vacancy.create.error.description"));
        model.addAttribute("vacancyCreateErrorCategory", bundle.getString("vacancy.create.error.category"));
        model.addAttribute("vacancyCreateErrorSalary", bundle.getString("vacancy.create.error.salary"));
        model.addAttribute("vacancyCreateErrorExpFrom", bundle.getString("vacancy.create.error.expFrom"));
        model.addAttribute("vacancyCreateErrorExpTo", bundle.getString("vacancy.create.error.expTo"));
        model.addAttribute("vacancyCreateErrorIsActive", bundle.getString("vacancy.create.error.isActive"));
        model.addAttribute("vacancyCreatePopupSuccessMessage", bundle.getString("vacancy.create.popup.successTitle"));
        model.addAttribute("vacancyCreatePopupVacancy", bundle.getString("vacancy.create.popup.vacancy"));
        model.addAttribute("vacancyCreatePopupSuccessfullyCreated", bundle.getString("vacancy.create.popup.successfullyCreated"));


        model.addAttribute("vacancyEditTitle", bundle.getString("vacancy.edit.title"));
        model.addAttribute("vacancyEditName", bundle.getString("vacancy.edit.name"));
        model.addAttribute("vacancyEditDescription", bundle.getString("vacancy.edit.description"));
        model.addAttribute("vacancyEditCategoryId", bundle.getString("vacancy.edit.categoryId"));
        model.addAttribute("vacancyEditCategory", bundle.getString("vacancy.edit.category"));
        model.addAttribute("vacancyEditSalary", bundle.getString("vacancy.edit.salary"));
        model.addAttribute("vacancyEditExpFrom", bundle.getString("vacancy.edit.expFrom"));
        model.addAttribute("vacancyEditExpTo", bundle.getString("vacancy.edit.expTo"));
        model.addAttribute("vacancyEditIsActive", bundle.getString("vacancy.edit.isActive"));
        model.addAttribute("vacancyEditActiveYes", bundle.getString("vacancy.edit.activeYes"));
        model.addAttribute("vacancyEditActiveNo", bundle.getString("vacancy.edit.activeNo"));
        model.addAttribute("vacancyEditAuthorId", bundle.getString("vacancy.edit.authorId"));
        model.addAttribute("vacancyEditSubmit", bundle.getString("vacancy.edit.submit"));
        model.addAttribute("vacancyEditPopUpSuccessTitle", bundle.getString("vacancy.edit.popup.successTitle"));
        model.addAttribute("vacancyEditPopUpVacancy", bundle.getString("vacancy.edit.popup.vacancy"));
        model.addAttribute("vacancyEditPopUpSuccessfullyEdited", bundle.getString("vacancy.edit.popup.successfullyEdited"));

        model.addAttribute("vacancyButtonApply", bundle.getString("vacancy.button.apply"));
        model.addAttribute("vacancyButtonBack", bundle.getString("vacancy.button.back"));
        model.addAttribute("vacancyButtonInfo", bundle.getString("vacancy.button.info"));
        model.addAttribute("vacancyButtonClose", bundle.getString("vacancy.button.close"));
        model.addAttribute("vacancyButtonConfirmChanges", bundle.getString("vacancy.button.confirmChanges"));
        model.addAttribute("vacancyButtonCreate", bundle.getString("vacancy.button.createVacancy"));

    }

    @GetMapping()
    public String getVacancies(Model model,
                               @PageableDefault(size = 6) Pageable pageable,
                               Authentication authentication) {
//        MvcControllersUtil.authCheckAndAddAttributes(
//                model,
//                authentication,
//                vacancyService.getVacancies(),
//                "vacancies");
        Page<VacancyDto> vacancies = vacancyService.getVacancies(pageable);
        int currentPage = pageable.getPageNumber();
        int totalPages = vacancies.getTotalPages();
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageable.getPageSize());
        return "vacancies/vacancies";
    }

    @GetMapping("vacancies-user-responded")
    public String getVacanciesUserResponded(Model model, Authentication authentication) {
        UserDto userDto = userService.getUserByEmail(authentication.getName());
        List<VacancyDto> vacanciesUserResponded = vacancyService.getVacanciesUserResponded(userDto.getId());
        model.addAttribute("vacanciesUserResponded", vacanciesUserResponded);
        return "vacancies/vacancies_user_responded";
    }

    @GetMapping("vacancies-with-responses")
    public String getVacanciesWithResponses(Model model) {
        List<ResumeDto> resumes = resumeService.getResumes();
        List<VacancyDto> vacancies = vacancyService.getVacancies();
        List<RespondedApplicantDto> respondedApplicants = respondedApplicantService.getRespondedApplicants();

        Map<VacancyDto, List<ResumeDto>> respondedToVacanciesResumesMap = MvcControllersUtil.getRespondedToVacanciesResumesMap(
                vacancies,
                resumes,
                respondedApplicants
        );

        // Debugging output
        respondedToVacanciesResumesMap.forEach((vacancy, resumeList) -> {
            log.info(ConsoleColors.BLUE_UNDERLINED + "Vacancy: {}" + ConsoleColors.RESET, vacancy);
            resumeList.forEach(resume -> log.info("\t\t" + ConsoleColors.ANSI_PURPLE + "Resume: {}" + ConsoleColors.RESET, resume));
        });

        model.addAttribute("vacanciesWithResumesEntries", respondedToVacanciesResumesMap.entrySet());
        return "vacancies/vacancies_with_responses";
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
        List<CategoryDto> categories = categoriesService.getCategories();
        model.addAttribute("categories", categories);
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
//            model.addAttribute("successMessage", "vacancy added successfully");
            model.addAttribute("entityUpdated", true);
//            return "redirect:/profile"; // Redirect to the profile
            return "vacancies/create_vacancy";
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
        List<CategoryDto> categories = categoriesService.getCategories();
        model.addAttribute("categories", categories);
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
            model.addAttribute("entityUpdated", true);
//            return "redirect:/profile"; // Redirect to the profile
            return "vacancies/edit_vacancy";
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
            return "redirect:/profile";
        }
        return "redirect:/auth/login";
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

        if (authentication != null && authentication.isAuthenticated()) {
            UserDto currentUser = userService.getUserByEmail(authentication.getName());
            List<ResumeDto> userResumes = resumeService.getResumeByUserId(currentUser.getId());
            boolean ifUserRespondedToVacancy =
                    respondedApplicantService.checkIfUserRespondedToVacancy(userResumes, vacancyId);
            model.addAttribute("userRespondedToVacancy", ifUserRespondedToVacancy);
            model.addAttribute("userResumes", userResumes);
            model.addAttribute("user", currentUser);
        }

        return "vacancies/vacancy_info";
    }

    @PostMapping("apply/{vacancyId}")
    public String applyForVacancy(
            @PathVariable Integer vacancyId,
            @RequestParam Integer resumeId,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (authentication != null && authentication.isAuthenticated()) {
            ResumeDto resumeDto = resumeService.getResumeById(resumeId);
            userService.applyForVacancy(vacancyId, resumeDto);

            redirectAttributes.addFlashAttribute("successMessage", "Successfully applied for the vacancy.");
            return "redirect:/profile";
        }
        return "redirect:/auth/login";
    }

}
