package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SaveGradeDto {

    private Integer studentId;

    private Integer subjectId;

    @Min(1)
    @Max(6)
    private Integer grade;
}
