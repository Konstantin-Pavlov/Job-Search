package kg.attractor.jobsearch.service.impl;


import kg.attractor.jobsearch.exception.ErrorResponseBody;
import kg.attractor.jobsearch.exception.ResourceNotFoundException;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorResponseBody makeResponse(Exception exception) {
        log.error("Exception: {}", exception.getMessage(), exception);

//        exception.printStackTrace();
        return switch (exception) {
            case ResourceNotFoundException resourceNotFoundException -> ErrorResponseBody.builder()
                    .error("resource not found")
                    .reasons(Map.of(
                            "message", List.of(exception.getMessage()),
                            "stackTrace", getFilteredStackTrace(exception)
                    ))
                    .build();
            case UserNotFoundException userNotFoundException -> ErrorResponseBody.builder()
                    .error("user not found")
                    .reasons(Map.of(
                            "message", List.of(exception.getMessage()),
                            "stackTrace", getFilteredStackTrace(exception)
                    ))
                    .build();
            case SQLSyntaxErrorException throwables -> ErrorResponseBody.builder()
                    .error("Database Syntax Error")
                    .reasons(Map.of(
                            "message", List.of(exception.getMessage()),
                            "stackTrace", getFilteredStackTrace(exception)
                    ))
                    .build();
            case DuplicateKeyException duplicateKeyException -> ErrorResponseBody.builder()
                    .error("Duplicate Entry Error")
                    .reasons(Map.of("error", List.of("A record with the same key already exists in the database. Please check your input and try again.")))
                    .build();
            default ->
                // Handle other exceptions as needed
                    ErrorResponseBody.builder()
                            .error(exception.getMessage())
                            .reasons(Map.of(
                                    "message", List.of(exception.getMessage()),
                                    "stackTrace", getFilteredStackTrace(exception)
                            ))
                            .build();
        };
    }

    @Override
    public ErrorResponseBody makeResponse(BindingResult exception) {
        Map<String, List<String>> reasons = new HashMap<>();
        log.error("not valid data - Validation error:");
        exception.getFieldErrors().stream()
                .filter(e -> e.getDefaultMessage() != null)
                .forEach(e -> {
                    log.error("{}: {}", e.getField(), e.getDefaultMessage());
                    List<String> errors = new ArrayList<>();
                    errors.add(e.getDefaultMessage());
                    if (!reasons.containsKey(e.getField())) {
                        reasons.put(e.getField(), errors);
                    } else {
                        reasons.get(e.getField()).addAll(errors);
                    }
                });
        return ErrorResponseBody.builder()
                .error("Validation error")
                .reasons(reasons)
                .build();
    }

    private List<String> getFilteredStackTrace(Throwable throwable) {
        return Stream.of(throwable.getStackTrace())
                .map(StackTraceElement::toString)
                .limit(5) // Limit to the first 5 lines for brevity
                .toList();
    }

}
