package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Locale;
import java.util.ResourceBundle;

@Controller
@RequiredArgsConstructor
public class NavigationController {
    private static final Logger log = LoggerFactory.getLogger(NavigationController.class);

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {
//        model.addAttribute("_csrf", csrfToken);

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
    }

    @GetMapping("navigation")
    public String getNavigation(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        log.info("isAuthenticated: {}", isAuthenticated);
        return "navigation/navigation";
    }
}

