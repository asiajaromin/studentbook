package pl.jcommerce.joannajaromin.studentbook.service;

import pl.jcommerce.joannajaromin.studentbook.dto.EmailData;

public interface MailService {

    void sendEmailToStudentAboutNewGrade(EmailData emailData);
}
