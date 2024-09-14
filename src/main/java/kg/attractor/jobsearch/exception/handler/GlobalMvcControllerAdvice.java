package kg.attractor.jobsearch.exception.handler;

import freemarker.template.TemplateModelException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exception.CategoryNotFoundException;
import kg.attractor.jobsearch.exception.ErrorResponseBody;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.exception.VacancyNotFoundException;
import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalMvcControllerAdvice {

    private final ErrorService errorService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(HttpServletRequest request, Model model, Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        request.setAttribute("javax.servlet.error.status_code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        request.setAttribute("javax.servlet.error.message", ex.getMessage());
        request.setAttribute("javax.servlet.error.exception", ex);

        ErrorResponseBody errorResponse = errorService.makeResponse(ex);

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", errorResponse.getError());
        model.addAttribute("message", errorResponse.getError());
        model.addAttribute("details", request);
        model.addAttribute("exceptionMessage", ex.getMessage());
        model.addAttribute("stackTrace", getStackTrace(ex));

        return "errors/error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String notFound(HttpServletRequest request, Model model, NoSuchElementException ex) {
        log.error("NoSuchElementException: {}", request.getRequestURI());

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);

        return "errors/error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleVacancyNotFound(HttpServletRequest request, Model model, UserNotFoundException ex) {
        log.error("UserNotFoundException: {}", ex.getMessage());

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "User Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);

        return "errors/error";
    }

    @ExceptionHandler(VacancyNotFoundException.class)
    public String handleVacancyNotFound(HttpServletRequest request, Model model, VacancyNotFoundException ex) {
        log.error("VacancyNotFoundException: {}", ex.getMessage());

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "Vacancy Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);

        return "errors/error";
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public String handleVacancyNotFound(HttpServletRequest request, Model model, CategoryNotFoundException ex) {
        log.error("CategoryNotFoundException: {}", ex.getMessage());

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "Category Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);

        return "errors/error";
    }

    // Handle FreeMarker template errors
    @ExceptionHandler(TemplateModelException.class)
    public String handleFreeMarkerError(HttpServletRequest request, Model model, TemplateModelException ex) {
        log.error("FreeMarker error: {}", ex.getMessage());

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", "Template Error");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);

        return "errors/error";
    }


    // generic exception handler for other exceptions
//    @ExceptionHandler(Exception.class)
//    public String handleGenericException(HttpServletRequest request, Model model, Exception ex) {
//        log.error("Exception: {}", ex.getMessage());
//
//        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        model.addAttribute("reason", "Internal Server Error");
//        model.addAttribute("message", ex.getMessage());
//        model.addAttribute("details", request);
//
//        return "errors/error";
//    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
