package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jcommerce.joannajaromin.studentbook.validator.GradeConstraint;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradeDto {

    private Integer id;
    private Integer studentId;
    private Integer subjectId;

    @GradeConstraint
    private Integer grade;
}
