package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

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
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("userDto", userWithAvatarFileDto);
            return "auth/profile-edit";
        }
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            userWithAvatarFileDto.setEnabled(userDto.isEnabled());
            userWithAvatarFileDto.setPassword(userDto.getPassword());
            userWithAvatarFileDto.setId(userDto.getId());
            userService.updateUser(userWithAvatarFileDto);
            model.addAttribute("successMessage", "Profile updated successfully");
            return "redirect:/auth/profile"; // Redirect to the profile
        }
        return "redirect:/auth/login"; // Redirect to login if not authenticated
    }
}
