package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;

@Service
@RequiredArgsConstructor
public class GradeNotificationServiceImpl implements GradeNotificationService {

    private static final String MAIL_SUBJECT_STRING = "Wystawiono nową ocenę z przedmiotu: ";
    private static final String TEMPLATE_NAME = "grade-notification-template";

    private final MailService mailService;
    private final GradeRepository gradeRepository;

    @Override
    @Async
    public void notifyAboutNewGrade(int gradeId) {
        Grade grade = gradeRepository.findByIdCustom(gradeId);
        var subjectName = grade.getSubject().getName();
        var gradeValue = grade.getGrade();
        var mailSubject = MAIL_SUBJECT_STRING + subjectName;
        var student = grade.getStudent();
        Context context = createContext(gradeValue, student, subjectName);
        var emailDto = new EmailDto(student.getEmail(),mailSubject,TEMPLATE_NAME,context);
        mailService.sendEmail(emailDto);
    }

    private Context createContext(int grade, Student student, String subjectName) {
        var context = new Context();
        context.setVariable("studentName", student.getFirstName() + " " + student.getLastName());
        context.setVariable("subjectName", subjectName);
        context.setVariable("grade", grade);
        return context;
    }
}
