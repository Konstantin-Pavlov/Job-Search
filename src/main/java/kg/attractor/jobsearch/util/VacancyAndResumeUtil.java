package kg.attractor.jobsearch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
public class VacancyAndResumeUtil {
    private VacancyAndResumeUtil() {
    }

    public static void authCheckAndAddAttributes(
            Model model,
            Authentication authentication,
            List<?> collection,
            String placeHolder) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute(placeHolder, collection);
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            log.info("Authenticated with email {}", username);
            model.addAttribute("username", username);
        }
    }

    public static <T> void authCheckAndAddAttributes(
            Model model,
            Authentication authentication,
            T dto,
            String placeHolder
    ) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute(placeHolder, dto);
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
        }
    }
}
