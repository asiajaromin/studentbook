import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import pl.jcommerce.joannajaromin.studentbook.entity.Student
import pl.jcommerce.joannajaromin.studentbook.service.MailService
import pl.jcommerce.joannajaromin.studentbook.service.MailServiceImpl
import spock.lang.Specification

class MailServiceTest extends Specification {

    def FIRST_NAME = "Henryk"
    def LAST_NAME = "Nowak"
    def EMAIL_ADDRESS = "henryk.nowak@mail.pl"
    def SUBJECT_NAME = "Geografia"
    def EXPECTED_EMAIL_SUBJECT = "Wystawiono nową ocenę z przedmiotu: " + SUBJECT_NAME
    def GRADE = 4
    def EXPECTED_EMAIL_TEXT = prepareEmailText()
    private Student student
    private MailService mailService
    private JavaMailSender mailSender

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
                .toString()
    }

    def setup(){
        student = new Student()
        student.setFirstName(FIRST_NAME)
        student.setLastName(LAST_NAME)
        student.setEmail(EMAIL_ADDRESS)
        mailSender = Mock()
        mailService = new MailServiceImpl(mailSender)
    }

    def "can send email"(){
        given:

        when:
        mailService.sendEmailToStudent(GRADE,SUBJECT_NAME,student)

        then:
        1 * mailSender.send(_)
    }

    def "email contains expected information" (){
        given:
        SimpleMailMessage emailMessage = new SimpleMailMessage()
        emailMessage.setTo(EMAIL_ADDRESS)
        emailMessage.setSubject(EXPECTED_EMAIL_SUBJECT)
        emailMessage.setText(EXPECTED_EMAIL_TEXT)

        when:
        mailService.sendEmailToStudent(GRADE,SUBJECT_NAME,student)

        then:
        1 * mailSender.send(emailMessage)
    }

}
