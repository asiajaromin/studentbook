package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine htmlTemplateEngine;

    @Async
    @Override
    public void sendEmail(EmailDto emailDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            messageHelper.setSubject(emailDto.getSubject());
            messageHelper.setTo(emailDto.getRecipient());
            String htmlContent = htmlTemplateEngine.process(emailDto.getTemplateName(), emailDto.getContext());
            messageHelper.setText(htmlContent,true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
