package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailToStudent(int grade, String subjectName, Student student) throws MailException {
        SimpleMailMessage email = prepareEmail(grade, subjectName, student);
        javaMailSender.send(email);
    }

    private SimpleMailMessage prepareEmail(int grade, String subjectName, Student student) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(student.getEmail());
        email.setSubject("Wystawiono nową ocenę z przedmiotu: " + subjectName);
        String emailText = prepareEmailText(grade, subjectName, student);
        email.setText(emailText);
        return email;
    }

    private String prepareEmailText(int grade, String subjectName, Student student) {
        return new StringBuilder()
                    .append("Witaj ")
                    .append(student.getFirstName())
                    .append(" ")
                    .append(student.getLastName())
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append("Otrzymałeś ocenę!")
                    .append(System.lineSeparator())
                    .append("Nazwa przedmiotu: ")
                    .append(subjectName)
                    .append(System.lineSeparator())
                    .append("Ocena: ")
                    .append(grade)
                    .toString();
    }
}
