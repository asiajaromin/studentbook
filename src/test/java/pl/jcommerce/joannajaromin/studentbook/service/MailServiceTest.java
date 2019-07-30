package pl.jcommerce.joannajaromin.studentbook.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailData;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MailServiceTest {

    private static final String FIRST_NAME = "Henryk";
    private static final String LAST_NAME = "Nowak";
    private static final String EMAIL_ADDRESS = "henryk.nowak@mail.pl";
    private static final String SUBJECT_NAME = "Geografia";
    private static final String EXPECTED_EMAIL_SUBJECT = "Wystawiono nową ocenę z przedmiotu: " + SUBJECT_NAME;
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
    private TemplateEngine templateEngine;
    private EmailData emailData;
    private TemplateEngine htmlTemplateEngine;

    @Before
    public void before (){
        student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL_ADDRESS);
        templateEngine = mock(TemplateEngine.class);
        mailSender = mock(JavaMailSender.class);
        subjectRepository = mock(SubjectRepository.class);
        studentRepository = mock(StudentRepository.class);
        htmlTemplateEngine = mock(TemplateEngine.class);
        mailService = new MailServiceImpl(mailSender, templateEngine);
        gradeDto = new GradeDto(GRADE_ID, STUDENT_ID, SUBJECT_ID, GRADE);
        emailData = new EmailData(SUBJECT_NAME,GRADE,student);
    }

    @Ignore
    @Test
    public void canSendEmail() throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage((Session)null);
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        messageHelper.setSubject(EXPECTED_EMAIL_SUBJECT);
        messageHelper.setTo(emailData.getStudentEmail());
        Context context = new Context();
        context.setVariable("emailData", emailData);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
//        problem in the following line. Null pointer exception
//        http://unittesting1.blogspot.com/2016/04/mockito-mocking-templateengine-throws.html
        when(htmlTemplateEngine.process("email-template",context)).thenReturn(" ");
        mailService.sendEmailToStudentAboutNewGrade(emailData);
        verify(mailSender).send(mimeMessage);
    }

}
