package pl.jcommerce.joannajaromin.studentbook.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GradeNotificationServiceTest {

    private static final String EMAIL_ADDRESS = "henryk.nowak@mail.pl";
    private static final String SUBJECT_NAME = "Geografia";
    private static final String EXPECTED_EMAIL_SUBJECT = "Wystawiono nową ocenę z przedmiotu: " + SUBJECT_NAME;
    private static final int GRADE_ID = 2;
    private static final int STUDENT_ID = 1;
    private static final int SUBJECT_ID = 3;
    private static final int GRADE = 4;
    private static final String TEMPLATE_NAME = "grade-notification-template";
    private Student student;
    private Subject subject;
    private Grade grade;
    private GradeNotificationService gradeNotificationService;
    private MailService mailService;
    private GradeRepository gradeRepo;
    private StudentRepository studentRepo;
    private SubjectRepository subjectRepo;

    @Before
    public void before() {
        student = new Student();
        student.setId(STUDENT_ID);
        student.setEmail(EMAIL_ADDRESS);
        subject = new Subject();
        subject.setId(SUBJECT_ID);
        subject.setName(SUBJECT_NAME);
        grade = new Grade(GRADE_ID,student,subject,GRADE);
        mailService = mock(MailService.class);
        gradeRepo = mock(GradeRepository.class);
        studentRepo = mock(StudentRepository.class);
        subjectRepo = mock(SubjectRepository.class);
        gradeNotificationService = new GradeNotificationServiceImpl(mailService, gradeRepo);
    }

    @Test
    public void canSendEmail(){
        when(gradeRepo.findByIdCustom(GRADE_ID)).thenReturn(grade);
        gradeNotificationService.notifyAboutNewGrade(GRADE_ID);
        var argument = ArgumentCaptor.forClass(EmailDto.class);
        verify(mailService).sendEmail(argument.capture());
        assertEquals(student.getEmail(),argument.getValue().getRecipient());
        assertEquals(EXPECTED_EMAIL_SUBJECT,argument.getValue().getSubject());
        assertEquals(TEMPLATE_NAME,argument.getValue().getTemplateName());
    }
}
