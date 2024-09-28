package kg.attractor.jobsearch.controller;


import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exception.ErrorResponseBody;
import kg.attractor.jobsearch.service.ErrorService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {
    private final ErrorService errorService;

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {
//        model.addAttribute("_csrf", csrfToken);

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
    }

    @RequestMapping("/error")
    // todo - debug
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = (throwable != null) ? throwable.getMessage() : (String) request.getAttribute("javax.servlet.error.message");

        if (statusCode == null) {
            statusCode = 500; // Default to 500 if status code is not available
        }

        if (errorMessage == null) {
            errorMessage = "An unexpected error occurred";
        }

        log.error("Error with status code {} and message {}", statusCode, errorMessage);

        ErrorResponseBody errorResponse = errorService.makeResponse(new Exception(errorMessage));

        model.addAttribute("status", statusCode);
        model.addAttribute("reason", errorResponse.getError());
        model.addAttribute("message", errorResponse.getError());
        model.addAttribute("details", request);
        model.addAttribute("reasons", errorResponse.getReasons());

        // Add exception message and stack trace if available
        String exceptionMessage = (String) request.getAttribute("javax.servlet.error.exception_message");
        String stackTrace = (String) request.getAttribute("javax.servlet.error.stack_trace");

        if (exceptionMessage != null) {
            model.addAttribute("exceptionMessage", exceptionMessage);
        }
        if (stackTrace != null) {
            model.addAttribute("stackTrace", stackTrace);
        }


        return "errors/error";
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }

}