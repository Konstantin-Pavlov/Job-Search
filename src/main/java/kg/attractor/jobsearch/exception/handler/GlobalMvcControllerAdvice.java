package kg.attractor.jobsearch.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exception.CategoryNotFoundException;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.exception.VacancyNotFoundException;
import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalMvcControllerAdvice {

    private final ErrorService errorService;

    @ExceptionHandler(NoSuchElementException.class)
    public String notFound(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleVacancyNotFound(HttpServletRequest request, Model model, UserNotFoundException ex) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "User Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(VacancyNotFoundException.class)
    public String handleVacancyNotFound(HttpServletRequest request, Model model, VacancyNotFoundException ex) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "Vacancy Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public String handleVacancyNotFound(HttpServletRequest request, Model model, CategoryNotFoundException ex) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "Category Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }
}
