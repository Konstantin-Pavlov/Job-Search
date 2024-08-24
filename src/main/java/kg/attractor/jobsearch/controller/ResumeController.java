package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.CategoriesService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
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
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoriesService categoriesService;

    @GetMapping()
    public String getResumes(Model model, Authentication authentication) {
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeService.getResumes(),
                "resumes");
        return "resumes/resumes";
    }

    @GetMapping("{resumeId}")
    public String getInfo(@PathVariable Integer resumeId, Model model, Authentication authentication) {
        ResumeDto resumeDto = resumeService.getResumeById(resumeId);
        UserDto userDto = userService.getUserById(resumeDto.getApplicantId());
        CategoryDto categoryDto = categoriesService.getCategoryById(resumeDto.getCategoryId());
        model.addAttribute("userDto", userDto);
        model.addAttribute("categoryDto", categoryDto);
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeDto,
                "resume"
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
            return "redirect:/auth/profile"; // Redirect to the profile
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
            model.addAttribute("successMessage", "Profile updated successfully");
            return "redirect:/auth/profile"; // Redirect to the profile
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }

//    @GetMapping("update")
//    public String showUpdateResumeForm(Model model, Authentication authentication) {
//        return "resumes/update_resume";
//    }

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
            return "redirect:/auth/profile";
        }
        return "redirect:/auth/login";
    }

}
