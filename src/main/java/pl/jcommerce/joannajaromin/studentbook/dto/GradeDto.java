package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.Data;

@Data
public class GradeDto {

    private Integer id;

    private Integer studentId;

    private Integer subjectId;

    private Integer grade;
}
