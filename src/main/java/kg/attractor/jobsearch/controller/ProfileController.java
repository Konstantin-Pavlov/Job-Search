package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("auth/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/edit-profile")
    public String editProfile(@RequestParam("userId") Integer userId, Model model) throws UserNotFoundException {
        UserDto userDto = userService.getUserById(userId);
        model.addAttribute("userDto", userDto);
        return "auth/edit_profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(UserDto userDto) {
        userService.updateUser(userDto);
        return "redirect:auth/profile";
    }
}
