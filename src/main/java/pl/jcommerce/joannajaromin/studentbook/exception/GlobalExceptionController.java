//package pl.jcommerce.joannajaromin.studentbook.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import java.util.Collections;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@ControllerAdvice
//@Component
//public class GlobalExceptionController {
//
//    private Map error(Object message) {
//        return Collections.singletonMap("error", message);
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ModelAndView handleException(MethodArgumentTypeMismatchException exc){
//        ModelAndView model = new ModelAndView("error/generic_error");
//        model.addObject("errMsg", "Id powinno być dodatnią liczbą całkowitą.");
//        return model;
//    }
//
//    @ExceptionHandler(NumberFormatException.class)
//    public ModelAndView handleException(NumberFormatException exc){
//        ModelAndView model = new ModelAndView("error/generic_error");
//        model.addObject("errMsg", "Id powinno być dodatnią liczbą całkowitą.");
//        return model;
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map handle(ConstraintViolationException exception) {
//        return error(exception.getConstraintViolations()
//                .stream()
//                .map(ConstraintViolation::getMessage)
//                .collect(Collectors.toList()));
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map handle(MethodArgumentNotValidException exception) {
//        return error(exception.getBindingResult().getFieldErrors()
//                .stream()
//                .map(FieldError::getDefaultMessage)
//                .collect(Collectors.toList()));
//    }
//}
