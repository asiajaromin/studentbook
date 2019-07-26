package pl.jcommerce.joannajaromin.studentbook.service;

import pl.jcommerce.joannajaromin.studentbook.entity.Student;

public interface MailService {
    void sendEmailToStudent(int grade, String subjectName, Student student);
}
