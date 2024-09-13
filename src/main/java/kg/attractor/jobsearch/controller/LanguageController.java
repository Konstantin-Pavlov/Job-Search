package kg.attractor.jobsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Use @SessionAttributes:
 * This allows you to manage session attributes without directly using HttpSession.
 * <br></br>
 * Access Locale from Model:
 * Use @SessionAttribute to access the currentLocale in your controller methods.
 */

@Controller
@SessionAttributes("currentLocale")
public class LanguageController {

    @PostMapping("/setLanguage")
    public String setLanguage(@RequestParam("lang") String lang, Model model) {
        Locale locale;

        switch (lang) {
            case "ru":
                locale = Locale.forLanguageTag("ru"); // Use forLanguageTag
                break;
            case "kg":
                locale = Locale.forLanguageTag("kg"); // Use forLanguageTag
                break;
            default:
                locale = Locale.ENGLISH; // Default to English
        }

        model.addAttribute("currentLocale", locale);
        ResourceBundle bundle = ResourceBundle.getBundle("resource", locale);
        String title = bundle.getString("title");
        model.addAttribute("title", title); // Set the title in the model

        return "redirect:/"; // Redirect to the home page or wherever you want
    }
}