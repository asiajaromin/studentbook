package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailToStudent(GradeDto gradeDto, Subject subject, Student student) throws MailException {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(student.getEmail());
        email.setSubject("Wystawiono nową ocenę z przedmiotu: " + subject.getName());
        email.setText("Witaj " + student.getFirstName() + " " + student.getLastName() +
                System.lineSeparator() + System.lineSeparator() +
                "Otrzymałeś ocenę!" + System.lineSeparator() +
                "Nazwa przedmiotu: " + subject.getName() + System.lineSeparator()+
                "Ocena: " + gradeDto.getGrade());
        javaMailSender.send(email);
    }
}
