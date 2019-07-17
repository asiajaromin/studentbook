package pl.jcommerce.joannajaromin.studentbook.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = GradeValidator.class)
@Target( {ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GradeConstraint {
    String message() default "Ocena powinna być liczbą całkowitą od 1 do 6.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
