package pl.jcommerce.joannajaromin.studentbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HomeworkNotFoundException extends RuntimeException {

    public HomeworkNotFoundException(String exception) {
        super(exception);
    }
}
