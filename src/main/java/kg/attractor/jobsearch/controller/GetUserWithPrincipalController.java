package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.util.MvcControllersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.security.Principal;
import java.util.Locale;
import java.util.ResourceBundle;

@RestController
public class GetUserWithPrincipalController {

    private static final Logger log = LoggerFactory.getLogger(GetUserWithPrincipalController.class);

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {
//        model.addAttribute("_csrf", csrfToken);

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
    }

    @GetMapping(value = "/username")
    public String currentUserName(Principal principal) {
        if (principal == null) {
            log.error("Principal is null");
            return "Principal is null";
        }
        log.info("Principal name: {}", principal.getName());
        return principal.getName();
    }

    @GetMapping(value = "/current-user")
    public String currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            log.info("Current user: {}", username);
            return username;
        } else {
            log.error("No authenticated user found");
            return "No authenticated user found";
        }
    }

}
