package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;

@Getter
@EqualsAndHashCode
public class EmailData {

    private String subjectName;
    private int grade;
    private String studentEmail;
    private String firstName;
    private String lastName;

    public EmailData(String subjectName, int grade, Student student) {
        this.subjectName = subjectName;
        this.grade = grade;
        this.studentEmail = student.getEmail();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
    }

}
