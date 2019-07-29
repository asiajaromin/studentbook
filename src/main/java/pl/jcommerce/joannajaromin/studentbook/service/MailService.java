package pl.jcommerce.joannajaromin.studentbook.service;

import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;

import javax.mail.MessagingException;

public interface MailService {

    void sendEmailToStudent(GradeDto gradeDto) throws MessagingException;
}
