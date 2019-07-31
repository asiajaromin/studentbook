package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailData;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;

@Service
@RequiredArgsConstructor
public class GradeNotificationServiceImpl implements GradeNotificationService {

    private static final String MAIL_SUBJECT_STRING = "Wystawiono nową ocenę z przedmiotu: ";
    private static final String TEMPLATE_NAME = "grade-notification-template";

    private final MailService mailService;
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public void notifyAboutNewGrade(int gradeId) {
        var grade = gradeRepository.findByIdCustom(gradeId);
        var subjectName = subjectRepository.findByIdCustom(grade.getSubject().getId()).getName();
        var mailSubject = MAIL_SUBJECT_STRING + subjectName;
        var student = studentRepository.findByIdCustom(grade.getStudent().getId());
        Context context = createContext(grade.getGrade(), student, subjectName);
        var emailDto = new EmailDto(student.getEmail(),mailSubject,TEMPLATE_NAME,context);
        mailService.sendEmail(emailDto);
    }

    private Context createContext(int grade, Student student, String subjectName) {
        var context = new Context();
        var emailData = new EmailData(subjectName,grade,student);
        context.setVariable("emailData", emailData);
        return context;
    }
}
