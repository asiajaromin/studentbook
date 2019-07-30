package pl.jcommerce.joannajaromin.studentbook.service;

import org.junit.Before;
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
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        mailService = new MailServiceImpl(mailSender, templateEngine);
        gradeDto = new GradeDto(GRADE_ID, STUDENT_ID, SUBJECT_ID, GRADE);
        emailData = new EmailData(SUBJECT_NAME,GRADE,student);
        htmlTemplateEngine = mock(TemplateEngine.class);
    }

    @Test
    public void canSendEmail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        messageHelper.setSubject("Wystawiono nową ocenę z przedmiotu: " + emailData.getSubjectName());
        messageHelper.setTo(emailData.getStudentEmail());
        Context context = new Context();
        context.setVariable("emailData", emailData);
        String htmlContent = this.htmlTemplateEngine.process("email-template",context);
        messageHelper.setText(htmlContent,true);
        mailService.sendEmailToStudentAboutNewGrade(emailData);
        verify(mailSender).send(mimeMessage);
    }

}
