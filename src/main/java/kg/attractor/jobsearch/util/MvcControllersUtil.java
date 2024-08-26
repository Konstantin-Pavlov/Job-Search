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
import java.util.Map;
import java.util.Optional;

@Slf4j
public class MvcControllersUtil {
    RespondedApplicantRepository respondedApplicantRepository;

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
