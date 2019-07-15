package pl.jcommerce.joannajaromin.studentbook.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdValidator implements ConstraintValidator<IdConstraint, Integer> {

   @Override
   public void initialize(IdConstraint constraint) {
   }

   @Override
   public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
      return id>0 && id<500;
   }
}
