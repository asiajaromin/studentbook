package pl.jcommerce.joannajaromin.studentbook.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INCORRECT_ID_MESSAGE = "Nieprawidłowa wartość id.";
    private static final String ID_NOT_EXIST_MESSAGE = "Brak wpisu o takim id.";
    private static final String FILE_NOT_ATTACHED_MESSAGE = "Brak załączonego pliku.";

    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleIncorrectId(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = INCORRECT_ID_MESSAGE;
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleIdNotExists(RuntimeException ex, WebRequest request){
        String bodyOfResponse = ID_NOT_EXIST_MESSAGE;
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

// This exc is already defined in super class so it has to be handled differently that other exc
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String bodyOfResponse = FILE_NOT_ATTACHED_MESSAGE;
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
