package pl.jcommerce.joannajaromin.studentbook.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = IdValidator.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdConstraint {
    String message() default "Nieprawidłowy format Id. Id powinno być dodatnią liczbą całkowitą.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
