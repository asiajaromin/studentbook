package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jcommerce.joannajaromin.studentbook.validator.GradeConstraint;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SaveGradeDto {

    private Integer studentId;
    private Integer subjectId;

    @GradeConstraint
    private Integer grade;
}
