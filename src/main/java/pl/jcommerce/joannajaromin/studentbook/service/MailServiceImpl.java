package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TemplateEngine htmlTemplateEngine;

    @Override
    @Async
    public void sendEmailToStudent(GradeDto gradeDto) throws MailException, MessagingException {
        int subjectId = gradeDto.getSubjectId();
        int studentId = gradeDto.getStudentId();
        Subject subject = subjectRepository.myFindById(subjectId);
        String subjectName = subject.getName();
        Student student = studentRepository.myFindById(studentId);
        int gradeInt = gradeDto.getGrade();
//        SimpleMailMessage email = prepareEmail(gradeInt, subjectName, student);
        MimeMessage email = prepareMessage(gradeInt, subjectName, student);
        javaMailSender.send(email);
    }

    private MimeMessage prepareMessage(int gradeInt, String subjectName, Student student) throws MessagingException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        messageHelper.setSubject("Wystawiono nową ocenę z przedmiotu: " + subjectName);
        messageHelper.setTo(student.getEmail());
        Context context = createContext(gradeInt,subjectName,student);
        String htmlContent = this.htmlTemplateEngine.process("html/email-template.html",context);
        messageHelper.setText(htmlContent,true);
        return mimeMessage;
    }

    private Context createContext(int gradeInt, String subjectName, Student student) {
        Context context = new Context();
        context.setVariable("firstName",student.getFirstName());
        context.setVariable("lastName", student.getLastName());
        context.setVariable("subject", subjectName);
        context.setVariable("grade", gradeInt);
        return context;
    }


//    private SimpleMailMessage prepareEmail(int grade, String subjectName, Student student) {
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(student.getEmail());
//        email.setSubject("Wystawiono nową ocenę z przedmiotu: " + subjectName);
//        String emailText = prepareEmailText(grade, subjectName, student);
//        email.setText(emailText);
//        return email;
//    }
//
//    private String prepareEmailText(int grade, String subjectName, Student student) {
//        return new StringBuilder()
//                    .append("Witaj ")
//                    .append(student.getFirstName())
//                    .append(" ")
//                    .append(student.getLastName())
//                    .append(System.lineSeparator())
//                    .append(System.lineSeparator())
//                    .append("Otrzymałeś ocenę!")
//                    .append(System.lineSeparator())
//                    .append("Nazwa przedmiotu: ")
//                    .append(subjectName)
//                    .append(System.lineSeparator())
//                    .append("Ocena: ")
//                    .append(grade)
//                    .toString();
//    }
}
