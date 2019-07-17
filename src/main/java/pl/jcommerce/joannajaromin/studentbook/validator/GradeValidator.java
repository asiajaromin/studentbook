package pl.jcommerce.joannajaromin.studentbook.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GradeValidator implements ConstraintValidator<GradeConstraint,Integer> {
    @Override
    public void initialize(GradeConstraint constraint) {

    }

    @Override
    public boolean isValid(Integer grade, ConstraintValidatorContext constraintValidatorContext) {
        return grade != null && grade instanceof Integer && grade >= 1 && grade <= 6;
    }
}
