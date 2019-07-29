package pl.jcommerce.joannajaromin.studentbook.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MailServiceTest {

    private static final String FIRST_NAME = "Henryk";
    private static final String LAST_NAME = "Nowak";
    private static final String EMAIL_ADDRESS = "henryk.nowak@mail.pl";
    private static final String SUBJECT_NAME = "Geografia";
    private static final String EXPECTED_EMAIL_SUBJECT = "Wystawiono nową ocenę z przedmiotu: " + SUBJECT_NAME;
    private final String EXPECTED_EMAIL_TEXT = prepareEmailText();
    private static final int GRADE_ID = 2;
    private static final int STUDENT_ID = 1;
    private static final int SUBJECT_ID = 3;
    private static final int GRADE = 4;
    private Student student;
    private MailService mailService;
    private JavaMailSender mailSender;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private GradeDto gradeDto;

    private String prepareEmailText(){
        return new StringBuilder()
                .append("Witaj ")
                .append(FIRST_NAME)
                .append(" ")
                .append(LAST_NAME)
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Otrzymałeś ocenę!")
                .append(System.lineSeparator())
                .append("Nazwa przedmiotu: ")
                .append(SUBJECT_NAME)
                .append(System.lineSeparator())
                .append("Ocena: ")
                .append(GRADE)
                .toString();
    }

    @Before
    public void before (){
        student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL_ADDRESS);
        mailSender = mock(JavaMailSender.class);
        subjectRepository = mock(SubjectRepository.class);
        studentRepository = mock(StudentRepository.class);
        mailService = new MailServiceImpl(mailSender,studentRepository,subjectRepository);
        gradeDto = new GradeDto(GRADE_ID, STUDENT_ID, SUBJECT_ID, GRADE);
    }

    @Test
    public void canSendEmail(){
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(EMAIL_ADDRESS);
        emailMessage.setSubject(EXPECTED_EMAIL_SUBJECT);
        emailMessage.setText(EXPECTED_EMAIL_TEXT);
        mailService.sendEmailToStudent(gradeDto);
        verify(mailSender).send(emailMessage);
    }

}
