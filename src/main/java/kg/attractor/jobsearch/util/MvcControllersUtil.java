package kg.attractor.jobsearch.util;

import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.repository.RespondedApplicantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

@Slf4j
public class MvcControllersUtil {

    private MvcControllersUtil() {
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

    public static Map<VacancyDto, List<ResumeDto>> getRespondedToVacanciesResumesMap(
            List<VacancyDto> vacancies,
            List<ResumeDto> resumeDtos,
            List<RespondedApplicantDto> respondedApplicantDtos
    ) {
        Map<VacancyDto, List<ResumeDto>> map = new HashMap<>();
        respondedApplicantDtos
                .forEach(record -> {
                    Optional<VacancyDto> vacancyDto = getById(vacancies, record.getVacancyId());
                    Optional<ResumeDto> resumeDto = getById(resumeDtos, record.getResumeId());
                    if (vacancyDto.isPresent() && resumeDto.isPresent()) {
                        map.computeIfAbsent(vacancyDto.get(), k -> new ArrayList<>()).add(resumeDto.get());
                    }
                });
        return map;
    }

    // todo - add to context?
    public static ResourceBundle getResourceBundleSetLocaleSetProperties(Model model, Locale locale) {
        // If locale is null, set it to English
//        MvcControllersUtil.setLocale(locale);

        if (locale == null) {
            locale = Locale.ENGLISH; // Default to English if no locale is set
        }

        // Load the resource bundle based on the current locale
//        ResourceBundle bundle = MvcControllersUtil.getResourceBundle(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("resource", locale);
        // Retrieve and add translations to the model for the layout.ftlh
        MvcControllersUtil.setPropertiesForLayout(model, bundle);
        return bundle;
    }

    // Retrieve and add translations to the model for the layout.ftlh
    public static void setPropertiesForLayout(Model model, ResourceBundle bundle) {
        model.addAttribute("home", bundle.getString("layout.home"));
        model.addAttribute("create", bundle.getString("layout.create"));
        model.addAttribute("profile", bundle.getString("layout.profile"));
        model.addAttribute("logout", bundle.getString("layout.logout"));
        model.addAttribute("login", bundle.getString("layout.login"));
        model.addAttribute("register", bundle.getString("layout.register"));
        model.addAttribute("loggedInMessage", bundle.getString("layout.logged.in"));
        model.addAttribute("roleMessage", bundle.getString("layout.roles"));
        model.addAttribute("notLoggedInMessage", bundle.getString("layout.not.logged.in"));
        model.addAttribute("availableActions", bundle.getString("layout.actions.available"));
        model.addAttribute("title", bundle.getString("layout.title"));
    }

    private static <T> Optional<T> getById(List<T> tList, Integer id) {
        if (tList == null || id == null) {
            return Optional.empty();
        }

        for (T item : tList) {
            try {
                Method getIdMethod = item.getClass().getMethod("getId");
                Integer itemId = (Integer) getIdMethod.invoke(item);
                if (id.equals(itemId)) {
                    return Optional.of(item);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return Optional.empty();
    }


}
