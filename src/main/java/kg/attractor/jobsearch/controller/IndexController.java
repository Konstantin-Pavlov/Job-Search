package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Locale;
import java.util.ResourceBundle;


@Slf4j
@Controller
public class IndexController {
    @GetMapping("/")
    public String welcome(Model model,
                          Authentication authentication,
                          @SessionAttribute(name = "currentLocale", required = false)
                              Locale locale) {
        MvcControllersUtil.authCheck(
                model,
                authentication
        );

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
//        ResourceBundle bundle = getResourceBundleSetLocaleSetProperties(model, locale);

        // Retrieve and add translations to the model for the index.ftlh
        model.addAttribute("greeting", bundle.getString("index.greeting"));
        model.addAttribute("vacancies", bundle.getString("index.vacancies"));
        model.addAttribute("resumes", bundle.getString("index.resumes"));
        model.addAttribute("vacanciesAndResponses", bundle.getString("index.vacancies.and.responses"));


        // Log greetings for debugging
        log.info("Locale: {}", locale);
        log.info("Greeting: {}", bundle.getString("greeting"));

        return "index";
    }

    private static ResourceBundle getResourceBundleSetLocaleSetProperties(Model model, Locale locale) {
        // If locale is null, set it to English
        if (locale == null) {
            locale = Locale.ENGLISH; // Default to English if no locale is set
        }

        // Load the resource bundle based on the current locale
        ResourceBundle bundle = ResourceBundle.getBundle("resource", locale);

        // Retrieve and add translations to the model for the layout.ftlh
        MvcControllersUtil.setPropertiesForLayout(model, bundle);
        return bundle;
    }


}
