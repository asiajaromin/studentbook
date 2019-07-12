package pl.jcommerce.joannajaromin.studentbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HomeworkFileNotAttached extends MissingServletRequestPartException {

    public HomeworkFileNotAttached(String exception) {
        super(exception);
    }
}
