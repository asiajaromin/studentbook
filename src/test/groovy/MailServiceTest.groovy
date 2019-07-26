import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import pl.jcommerce.joannajaromin.studentbook.entity.Student
import pl.jcommerce.joannajaromin.studentbook.service.MailService
import pl.jcommerce.joannajaromin.studentbook.service.MailServiceImpl
import spock.lang.Specification

class MailServiceTest extends Specification {

//    there is some problem with final fields: https://github.com/spockframework/spock/issues/963
    private String FIRST_NAME = "Henryk"
    private String LAST_NAME = "Nowak"
    private String EMAIL = "henryk.nowak@mail.pl"
    private String SUBJECT_NAME = "Geografia"
    private String EXPECTED_EMAIL_SUBJECT = "Wystawiono nową ocenę z przedmiotu: geografia"
    private String EXPECTED_EMAIL_TEXT = prepareEmailText()
    private int GRADE = 4
    private Student student
    private MailService mailService;
    private JavaMailSender mailSender
    private SimpleMailMessage email

    private String prepareEmailText(){
        return new StringBuilder()
                .append("Witaj ")
                .append()
                .append(" ")
                .append(student.getLastName())
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

    def setup(){
        student = new Student();
        student.setFirstName(FIRST_NAME)
        student.setLastName(LAST_NAME)
        student.setEmail(EMAIL)
        mailSender = Mock()
        mailService = new MailServiceImpl(mailSender)
        email = new SimpleMailMessage()
        email.setTo(EMAIL)
        email.setSubject(EXPECTED_EMAIL_SUBJECT)
        email.setText(EXPECTED_EMAIL_TEXT)
    }

    def "can send email"(){
        given:

        when:
        mailService.sendEmailToStudent(GRADE,SUBJECT_NAME,student)

        then:
        1 * mailSender.send(email)
    }

}
