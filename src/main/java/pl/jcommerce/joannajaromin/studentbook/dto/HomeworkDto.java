package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HomeworkDto {

    private Integer id;
    private Integer groupId;
    private Integer teacherId;
    private Integer subjectId;
    private String fileName;
    private String fileDescription;
    private byte[] fileData;
}
