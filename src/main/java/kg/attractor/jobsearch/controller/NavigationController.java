package kg.attractor.jobsearch.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NavigationController {
    private static final Logger log = LoggerFactory.getLogger(NavigationController.class);

    @GetMapping("navigation")
    public String getNavigation(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        log.info("isAuthenticated: {}", isAuthenticated);
        return "navigation/navigation";
    }
}

