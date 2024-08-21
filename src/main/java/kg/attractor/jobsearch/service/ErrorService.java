package kg.attractor.jobsearch.service;


import kg.attractor.jobsearch.exception.ErrorResponseBody;
import org.springframework.validation.BindingResult;

public interface ErrorService {
    ErrorResponseBody makeResponse(Exception exception);

    ErrorResponseBody makeResponse(BindingResult exception);
}
