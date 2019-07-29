package pl.jcommerce.joannajaromin.studentbook.service;

import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;

public interface MailService {

    void sendEmailToStudent(GradeDto gradeDto);
}
