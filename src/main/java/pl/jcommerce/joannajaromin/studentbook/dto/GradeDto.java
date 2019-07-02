package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.Data;

public @Data class GradeDto {

    private Integer id;

    private Integer studentId;

    private Integer subjectId;

    private Integer grade;
}
