package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.impl.CustomUserDetailsServiceImpl;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsServiceImpl customUserDetails;

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {
        model.addAttribute("_csrf", csrfToken);

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);

        // Add login properties to the model
        model.addAttribute("loginSignIn", bundle.getString("login.signIn"));
        model.addAttribute("loginEmail", bundle.getString("login.email"));
        model.addAttribute("loginPassword", bundle.getString("login.password"));
    }

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserWithAvatarFileDto());
        return "auth/register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserWithAvatarFileDto userDto,
                               BindingResult bindingResult,
                               Model model,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            log.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("userDto", userDto); // Keep the form fields with the entered values
            return "auth/register";
        }

        try {
            // Debugging output
            MultipartFile avatar = userDto.getAvatar();
            if (avatar != null) {
                log.info("Received avatar: name={}, size={}, originalFilename={}",
                        avatar.getName(), avatar.getSize(), avatar.getOriginalFilename());
                if (avatar.getOriginalFilename() == null || avatar.getOriginalFilename().isEmpty()) {
                    log.info("Received empty avatar, default avatar will be used");
                }
            } else {
                log.warn("Avatar file is null!");
            }

            userDto.setEnabled(true);
            userService.addUserWithAvatar(userDto);

            // Authenticate the user programmatically
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
//            Authentication authentication = authenticationManager.authenticate(authToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Automatically log in the user after registration
            UserDetails userDetails = customUserDetails.loadUserByUsername(userDto.getEmail());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, userDto.getPassword(), userDetails.getAuthorities());

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Create a new session and set the authentication context
            request.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());


            log.info("User authenticated successfully: {}", userDto.getEmail());
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
        } catch (Exception e) {
            log.error(e.getMessage());
            // сообщение появляется над формой
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }

        model.addAttribute("successMessage", "Registration successful! Redirecting to the main page...");
        model.addAttribute("okay", true);
        model.addAttribute("entityName", userDto.getName());
        // Redirect to the profile page after successful registration
//        return "redirect:/auth/profile";

        // Return the registration page with the success message
        return "auth/register";
    }

}