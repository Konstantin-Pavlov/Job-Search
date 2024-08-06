package kg.attractor.jobsearch.service.impl;


import kg.attractor.jobsearch.exception.ErrorResponseBody;
import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorServiceImpl implements ErrorService {


    @Override
    public ErrorResponseBody makeResponse(Exception exception) {
        return ErrorResponseBody.builder()
                .error(exception.getMessage())
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(BindingResult bindingResult) {
        Map<String, List<String>> errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                ));

        return ErrorResponseBody.builder()
                .error("Validation failed")
                .reasons(errors)
                .build();
    }

}