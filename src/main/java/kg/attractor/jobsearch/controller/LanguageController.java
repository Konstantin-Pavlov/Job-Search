package kg.attractor.jobsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Locale;

/**
 * The {@code LanguageController} class is responsible for handling language selection
 * in the application. It allows users to change the language of the interface and
 * stores the selected language in the session for subsequent requests.
 * <p>
 * This controller uses Spring's {@code @SessionAttributes} to manage the current
 * locale across multiple requests without needing to pass it explicitly each time.
 * </p>
 */

@Controller
@SessionAttributes("currentLocale")
public class LanguageController {
    /**
     * Sets the current language based on the user's selection and redirects to the
     * current page.
     *
     * @param lang       the language code selected by the user (e.g., "en", "ru", "kg").
     * @param currentUrl the URL of the current page, used for redirection after
     *                   setting the language.
     * @param model      the model object used to pass attributes to the view.
     * @return a redirect string to the current URL after setting the language.
     */
    @PostMapping("/setLanguage")
    public String setLanguage(@RequestParam("lang") String lang,
                              @RequestParam("currentUrl") String currentUrl,
                              Model model) {
        Locale locale = switch (lang) {
            case "ru" -> Locale.forLanguageTag("ru");
            case "kg" -> Locale.forLanguageTag("kg");
            default -> Locale.ENGLISH; // Default to English
        };

        model.addAttribute("currentLocale", locale);
//        ResourceBundle bundle = ResourceBundle.getBundle("resource", locale);
//        String title = bundle.getString("title");
//        model.addAttribute("title", title); // Set the title in the model

        // Redirect to the current URL after setting the language
        return "redirect:" + currentUrl;
    }
}