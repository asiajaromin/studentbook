package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailData;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine htmlTemplateEngine;

    @Override
    @Async
    public void sendEmailToStudentAboutNewGrade(EmailData emailData) {
        try {
            MimeMessage email = prepareMessage(emailData);
            javaMailSender.send(email);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    private MimeMessage prepareMessage(EmailData emailData) throws MessagingException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        messageHelper.setSubject("Wystawiono nową ocenę z przedmiotu: " + emailData.getSubjectName());
        messageHelper.setTo(emailData.getStudentEmail());
        Context context = createContext(emailData);
        String htmlContent = this.htmlTemplateEngine.process("email-template",context);
        messageHelper.setText(htmlContent,true);
        return mimeMessage;
    }

    private Context createContext(EmailData emailData) {
        Context context = new Context();
        context.setVariable("emailData",emailData);;
        return context;
    }

}
