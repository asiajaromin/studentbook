package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Async("asyncExecutor")
    public void sendEmailToStudent(GradeDto gradeDto) throws MailException {
        int subjectId = gradeDto.getSubjectId();
        int studentId = gradeDto.getStudentId();
        Subject subject = subjectRepository.myFindById(subjectId);
        String subjectName = subject.getName();
        Student student = studentRepository.myFindById(studentId);
        int gradeInt = gradeDto.getGrade();
        SimpleMailMessage email = prepareEmail(gradeInt, subjectName, student);
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
