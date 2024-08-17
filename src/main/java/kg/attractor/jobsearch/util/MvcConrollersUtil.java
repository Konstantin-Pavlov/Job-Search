package kg.attractor.jobsearch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
public class MvcConrollersUtil {
    private MvcConrollersUtil() {
    }

    public static void authCheckAndAddAttributes(
            Model model,
            Authentication authentication,
            List<?> collection,
            String placeHolder) {
        model.addAttribute(placeHolder, collection);
        authCheck(model, authentication);
    }

    public static <T> void authCheckAndAddAttributes(
            Model model,
            Authentication authentication,
            T dto,
            String placeHolder
    ) {
        model.addAttribute(placeHolder, dto);
        authCheck(model, authentication);
    }

    public static void authCheck(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
        }
    }

}
