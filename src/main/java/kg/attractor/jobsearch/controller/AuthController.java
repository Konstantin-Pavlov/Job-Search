package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserDtoWithAvatarUploadingDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @ModelAttribute
    public void addAttributes(Model model, CsrfToken csrfToken) {
        model.addAttribute("_csrf", csrfToken);
    }

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDtoWithAvatarUploadingDto());
        return "auth/register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDtoWithAvatarUploadingDto userDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            log.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("userDto", new UserDtoWithAvatarUploadingDto()); // Keep the form fields with the entered values
            return "auth/register";
        }

        try {
            userDto.setEnabled(true);
            userService.addUserWithAvatar(userDto);
        } catch (IOException | UserNotFoundException e) {
            model.addAttribute("errorMessage", "Error uploading avatar. Please try again.");
            log.error(e.getMessage());
            return "auth/register";
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());

            // сообщение появляется под полем
            bindingResult.rejectValue("email", "error.userDto", String.format("User with email %s already exists.", userDto.getEmail()));
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("userDto", userDto);

            // сообщение появляется над формой
//            model.addAttribute("errorMessage", String.format("User with email %s already exists.", userDto.getEmail()));
            return "auth/register";
        }

        model.addAttribute("successMessage", "Registration successful! Redirecting to the main page...");
        return "auth/register";
    }
}