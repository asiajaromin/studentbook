package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.Data;

@Data
public class GradeDto {

    private final Integer id;

    private final Integer studentId;

    private final Integer subjectId;

    private final Integer grade;
}
