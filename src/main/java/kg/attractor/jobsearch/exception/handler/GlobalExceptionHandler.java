package kg.attractor.jobsearch.exception.handler;

import kg.attractor.jobsearch.exception.CanNotFindImageException;
import kg.attractor.jobsearch.exception.CustomErrorResponse;
import kg.attractor.jobsearch.exception.EntityNotFoundException;
import kg.attractor.jobsearch.exception.ErrorResponseBody;
import kg.attractor.jobsearch.exception.ResourceNotFoundException;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorService errorService;

//    @ExceptionHandler(NoSuchElementException.class)
//    public ErrorResponse noSuchElement(NoSuchElementException exception) {
//        return ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage()).build();
//    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ErrorResponse validationHandler(MethodArgumentNotValidException exception){
//        return ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage()).build();
//    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<CustomErrorResponse> handleIOException(IOException exception) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .type("about:blank")
                .title("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getMessage())
                .detailMessageArguments(new Object[]{"Additional", "Details", "Here"}) // Example details
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseBody> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> validationHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(errorService.makeResponse(e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    // Обработка IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseBody> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.BAD_REQUEST);
    }

    // Обработка ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.NOT_FOUND);
    }

    // Обработка EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.NOT_FOUND);
    }

    // Обработка CanNotFindImageException
    @ExceptionHandler(CanNotFindImageException.class)
    public ResponseEntity<ErrorResponseBody> handleCanNotFindImageException(CanNotFindImageException e) {
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.NOT_FOUND);
    }

    // Обработка DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<CustomErrorResponse> handleBadSqlGrammarException(BadSqlGrammarException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .type("about:blank")
                .title("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(e.getMessage())
                .detailMessageArguments(new Object[]{e.getSQLException().getMessage()}) // Example details
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}